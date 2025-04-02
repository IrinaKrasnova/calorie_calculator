package example.myexample.calculator;

import example.myexample.entity.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalorieCalculatorFactory {
    private final WeightLossCalorieCalculator weightLossCalorieCalculator;
    private final MaintenanceCalorieCalculator maintenanceCalorieCalculator;
    private final WeightGainCalorieCalculator weightGainCalorieCalculator;

    public CalorieCalculator getCalorieCalculator(Goal goal) {
        switch (goal) {
            case WEIGHT_LOSS:
                return weightLossCalorieCalculator;
            case MAINTENANCE:
                return maintenanceCalorieCalculator;
            case WEIGHT_GAIN:
                return weightGainCalorieCalculator;
            default:
                throw new IllegalArgumentException("Unknown goal: " + goal);
        }
    }
}
