package example.myexample.calculator;

import lombok.AllArgsConstructor;
import example.myexample.entity.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaintenanceCalorieCalculator implements CalorieCalculator {
    private final BMRCalculator bmrCalculator;
    @Override
    public double calculateDailyCalories(User user) {
        double bmr = bmrCalculator.calculateBMR(user);
        return bmr * 1.2;
    }
}
