package example.myexample.service;

import example.myexample.calculator.CalorieCalculatorFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import example.myexample.entity.User;
import example.myexample.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CalorieCalculatorFactory calorieCalculatorFactory;

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    public User addUser(User user) {
        double dailyCalorieLimit = calorieCalculatorFactory.getCalorieCalculator(user.getGoal())
                .calculateDailyCalories(user);
        user.setDailyCalorieLimit(dailyCalorieLimit);
            return userRepository.save(user);
    }
}
