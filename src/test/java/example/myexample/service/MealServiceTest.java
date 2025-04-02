package example.myexample.service;

import example.myexample.calculator.CalorieCalculator;
import example.myexample.calculator.CalorieCalculatorMeal;
import example.myexample.dto.DailyMealReportDto;
import example.myexample.entity.Dish;
import example.myexample.entity.Meal;
import example.myexample.entity.User;
import example.myexample.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {
    @Mock
    private MealRepository mealRepository;
    @Mock
    private UserService userService;
    @Mock
    private DishService dishService;
    @Mock
    private CalorieCalculatorMeal calorieCalculatorMeal;
    @InjectMocks
    private MealService mealService;

    private User user;
    private Dish dish1;
    private Dish dish2;
    private Meal meal;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("John")
                .build();

        dish1 = Dish.builder()
                .id(1L)
                .name("Dish 1")
                .calories(200)
                .build();


        dish2 = Dish.builder()
                .id(2L)
                .name("Dish 2")
                .calories(300)
                .build();

        meal = new Meal(user, Arrays.asList(dish1, dish2));
    }

    @Test
    void testAddMeal() {
        List<Long> dishIds = Arrays.asList(1L, 2L);
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(dishService.findDishes(dishIds)).thenReturn(Arrays.asList(dish1, dish2));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        Meal result = mealService.addMeal(1L, dishIds);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertTrue(result.getDishes().containsAll(Arrays.asList(dish1, dish2)));

        ArgumentCaptor<Meal> mealCaptor = ArgumentCaptor.forClass(Meal.class);
        verify(mealRepository, times(1)).save(mealCaptor.capture());

        Meal savedMeal = mealCaptor.getValue();
        assertEquals(user, savedMeal.getUser());
        assertTrue(savedMeal.getDishes().containsAll(Arrays.asList(dish1, dish2)));
    }

    @Test
    void testAddMealWhenUserNotFound() {
        List<Long> dishIds = Arrays.asList(1L, 2L);
        when(userService.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            mealService.addMeal(1L, dishIds);
        });

        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void testGetCaloriesForDay() {
        LocalDate date = LocalDate.now();
        List<Meal> meals = Arrays.asList(meal);
        when(mealRepository.findByUserIdAndTimestampBetween(1L, date.atStartOfDay(), date.atTime(LocalTime.MAX)))
                .thenReturn(meals);

        int totalCalories = mealService.getCaloriesForDay(1L, date);

        assertEquals(500, totalCalories);
    }

    @Test
    void testGetDailyMealReport() {
        LocalDate date = LocalDate.now();
        List<Meal> meals = Arrays.asList(meal);
        when(mealRepository.findByUserIdAndTimestampBetween(1L, date.atStartOfDay(), date.atTime(LocalTime.MAX)))
                .thenReturn(meals);

        DailyMealReportDto report = mealService.getDailyMealReport(1L, date);

        assertNotNull(report);
        assertEquals(date, report.getDate());
        assertEquals(500, report.getTotalCalories());
        assertTrue(report.getDishNames().containsAll(Arrays.asList("Dish 1", "Dish 2")));
    }
}

