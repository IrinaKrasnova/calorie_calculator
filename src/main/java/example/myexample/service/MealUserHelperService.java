package example.myexample.service;

import example.myexample.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MealUserHelperService {
    private final UserService userService;
    private final MealService mealService;

    public boolean checkCalories(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        int consumedCalories = mealService.getCaloriesForDay(userId, LocalDate.now());

        return consumedCalories <= user.getDailyCalorieLimit();
    }
}
