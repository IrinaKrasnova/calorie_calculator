package example.myexample.service;

import example.myexample.dto.DailyMealReportDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import example.myexample.calculator.CalorieCalculatorMeal;
import example.myexample.entity.Dish;
import example.myexample.entity.Meal;
import example.myexample.entity.User;
import example.myexample.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserService userService;
    private final DishService dishService;
    private final CalorieCalculatorMeal calorieCalculatorMeal;

    public Meal addMeal(Long userId, List<Long> dishIds) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        List<Dish> dishes = dishService.findDishes(dishIds);
        Meal meal = new Meal(user, dishes);
        return mealRepository.save(meal);
    }

    public int getCaloriesForDay(Long userId, LocalDate date) {
        return getMealsForDay(userId, date)
                .stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();
    }

    public DailyMealReportDto getDailyMealReport(Long userId, LocalDate date) {
        List<Meal> meals = getMealsForDay(userId, date);

        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();

        List<String> dishNames = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .map(Dish::getName)
                .toList();

        return new DailyMealReportDto(date, totalCalories, dishNames);
    }

    private List<Meal> getMealsForDay(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return mealRepository.findByUserIdAndTimestampBetween(userId, start, end);
    }
}
