package example.myexample.calculator;

import example.myexample.entity.Gender;
import example.myexample.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BMRCalculator {
    public static double calculateBMR(User user) {
        if (user.getGender() == Gender.MALE) {
            return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }
    }
}
