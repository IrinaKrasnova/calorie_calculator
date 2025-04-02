package example.myexample.service;

import example.myexample.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealUserHelperServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private MealService mealService;
    @InjectMocks
    private  MealUserHelperService mealUserHelperService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("John")
                .dailyCalorieLimit(2000)
                .build();
    }

    @Test
    void testCheckCaloriesWhenCaloriesAreWithinLimit() {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(mealService.getCaloriesForDay(1L, LocalDate.now())).thenReturn(1500);

        boolean result = mealUserHelperService.checkCalories(1L);

        assertTrue(result, "Calories should be within the limit");
    }

    @Test
    void testCheckCaloriesWhenCaloriesExceedLimit() {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(mealService.getCaloriesForDay(1L, LocalDate.now())).thenReturn(2500);

        boolean result = mealUserHelperService.checkCalories(1L);

        assertFalse(result, "Calories should exceed the limit");
    }

    @Test
    void testCheckCaloriesWhenUserNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                mealUserHelperService.checkCalories(1L)
        );

        assertEquals("User with id 1 not found", exception.getMessage(), "Exception message should match");
    }
}