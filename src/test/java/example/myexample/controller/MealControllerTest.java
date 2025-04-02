package example.myexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.myexample.dto.DailyMealReportDto;
import example.myexample.entity.Dish;
import example.myexample.entity.Meal;
import example.myexample.entity.User;
import example.myexample.exception.GlobalExceptionHandler;
import example.myexample.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MealService mealService;
    @InjectMocks
    private MealController mealController;
    private ObjectMapper objectMapper;

    private Long userId;
    private Long dishId1;
    private Long dishId2;
    private User user;
    private Dish dish1;
    private Dish dish2;
    private Meal meal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mealController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper = new ObjectMapper();
        userId = 1L;
        user = User.builder()
                .id(userId)
                .username("John Doe")
                .email("john@example.com")
                .build();

        dishId1 = 101L;
        dishId2 = 102L;

        dish1 = Dish.builder()
                .id(dishId1)
                .name("Dish1")
                .build();
        dish2 = Dish.builder()
                .id(dishId2)
                .name("Dish2")
                .build();

        List<Dish> dishes = Arrays.asList(dish1, dish2);
        meal = new Meal(user, dishes);
    }

    @Test
    public void testAddMeal() throws Exception {
        when(mealService.addMeal(userId, Arrays.asList(dishId1, dishId2))).thenReturn(meal);

        mockMvc.perform(post("/meals/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(dishId1, dishId2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishes[0].id").value(dishId1))
                .andExpect(jsonPath("$.dishes[1].id").value(dishId2));

        verify(mealService, times(1)).addMeal(userId, Arrays.asList(dishId1, dishId2));
    }


    @Test
    public void testGetMealHistory() throws Exception {
        LocalDate date = LocalDate.now();
        List<String> dishNames = Arrays.asList("Dish1", "Dish2");
        DailyMealReportDto reportDto = DailyMealReportDto.builder()
                .date(date)
                .totalCalories(500)
                .dishNames(dishNames)
                .build();

        when(mealService.getDailyMealReport(userId, date)).thenReturn(reportDto);

        mockMvc.perform(get("/meals/{userId}/history", userId)
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(date.toString()))
                .andExpect(jsonPath("$.totalCalories").value(500))
                .andExpect(jsonPath("$.dishNames").isArray())
                .andExpect(jsonPath("$.dishNames.length()").value(2))
                .andExpect(jsonPath("$.dishNames[0]").value("Dish1"))
                .andExpect(jsonPath("$.dishNames[1]").value("Dish2"));

        verify(mealService, times(1)).getDailyMealReport(userId, date);
    }

    @Test
    void testGetMealHistoryWithoutDate() throws Exception {
        DailyMealReportDto reportDto = DailyMealReportDto.builder()
                .totalCalories(500)
                .dishNames(Arrays.asList("Dish1", "Dish2"))
                .build();

        when(mealService.getDailyMealReport(userId, LocalDate.now())).thenReturn(reportDto);

        mockMvc.perform(get("/meals/{userId}/history", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCalories").value(500))
                .andExpect(jsonPath("$.dishNames").isArray())
                .andExpect(jsonPath("$.dishNames[0]").value("Dish1"))
                .andExpect(jsonPath("$.dishNames[1]").value("Dish2"));
    }
}