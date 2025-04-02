package example.myexample.calculator;

import lombok.RequiredArgsConstructor;
import example.myexample.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeightGainCalorieCalculator implements CalorieCalculator {
    private final BMRCalculator bmrCalculator;
    @Override
    public double calculateDailyCalories(User user) {
        double bmr = bmrCalculator.calculateBMR(user);
        return bmr * 1.2 + 500;
    }
}
