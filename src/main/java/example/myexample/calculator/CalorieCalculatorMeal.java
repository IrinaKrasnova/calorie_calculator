package example.myexample.calculator;

import example.myexample.entity.Dish;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalorieCalculatorMeal {
    public int calculateCaloriesMeal (List<Dish> dishes) {
        return dishes.stream().mapToInt(Dish::getCalories).sum();
    }
}
