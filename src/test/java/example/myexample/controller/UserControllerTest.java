package example.myexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.myexample.entity.Gender;
import example.myexample.entity.Goal;
import example.myexample.entity.User;
import example.myexample.exception.GlobalExceptionHandler;
import example.myexample.service.MealUserHelperService;
import example.myexample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private MealUserHelperService mealUserHelperService;

    @InjectMocks
    private UserController userController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddUser() throws Exception {
        User user = User.builder()
                .username("John Doe")
                .email("example@com")
                .age(25)
                .weight(75.0)
                .height(180.0)
                .goal(Goal.MAINTENANCE)
                .gender(Gender.FEMALE)
                .build();
        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.weight").value(75.0))
                .andExpect(jsonPath("$.height").value(180.0));

        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    void testAddUserWithEmptyUsername() throws Exception {
        User user = User.builder()
                .username("")
                .email("example@com")
                .age(25)
                .weight(75.0)
                .height(180.0)
                .goal(Goal.MAINTENANCE)
                .gender(Gender.FEMALE)
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Имя пользователя не может быть пустым."));

        verify(userService, times(0)).addUser(any(User.class));
    }

    @Test
    void testAddUserWithAgeTooLow() throws Exception {
        User user = User.builder()
                .username("John Doe")
                .email("example@com")
                .age(10)
                .weight(75.0)
                .height(180.0)
                .goal(Goal.MAINTENANCE)
                .gender(Gender.FEMALE)
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").value("Возраст должен быть не менее 12 лет"));

        verify(userService, times(0)).addUser(any(User.class));
    }

    @Test
    void testAddUserWithWeightTooLow() throws Exception {
        User user = User.builder()
                .username("John Doe")
                .email("example@com")
                .age(25)
                .weight(20.0)
                .height(180.0)
                .goal(Goal.MAINTENANCE)
                .gender(Gender.FEMALE)
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.weight").value("Вес должен быть не менее 30 кг"));

        verify(userService, times(0)).addUser(any(User.class));  // Сервис не должен быть вызван
    }

    @Test
    public void testCheckCalories() throws Exception {
        Long userId = 1L;
        when(mealUserHelperService.checkCalories(userId)).thenReturn(true);

        mockMvc.perform(get("/users/{userId}/calories/check", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(mealUserHelperService, times(1)).checkCalories(userId);
    }
}