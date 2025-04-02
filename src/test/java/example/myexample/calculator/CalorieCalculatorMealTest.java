package example.myexample.calculator;

import example.myexample.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalorieCalculatorMealTest {
    @InjectMocks
    private CalorieCalculatorMeal calorieCalculatorMeal;
    @Mock
    private Dish dish1;
    @Mock
    private Dish dish2;
    @Mock
    private Dish dish3;

    @Test
    void testCalculateCaloriesMeal() {
        when(dish1.getCalories()).thenReturn(200);
        when(dish2.getCalories()).thenReturn(350);
        when(dish3.getCalories()).thenReturn(150);

        List<Dish> dishes = Arrays.asList(dish1, dish2, dish3);
        int totalCalories = calorieCalculatorMeal.calculateCaloriesMeal(dishes);
        assertEquals(700, totalCalories);
    }

    @Test
    void testCalculateCaloriesMealWithEmptyList() {
        List<Dish> emptyDishes = List.of();
        int totalCalories = calorieCalculatorMeal.calculateCaloriesMeal(emptyDishes);
        assertEquals(0, totalCalories);
    }
}

