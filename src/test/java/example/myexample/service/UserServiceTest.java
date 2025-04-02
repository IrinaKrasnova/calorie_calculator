package example.myexample.service;

import example.myexample.calculator.CalorieCalculator;
import example.myexample.calculator.CalorieCalculatorFactory;
import example.myexample.entity.Goal;
import example.myexample.entity.User;
import example.myexample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CalorieCalculatorFactory calorieCalculatorFactory;
    @Mock
    private CalorieCalculator calorieCalculator;
    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("John")
                .goal(Goal.WEIGHT_LOSS)
                .build();
    }

    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent(), "User should be found");
        assertEquals(user, result.get(), "Returned user should match the mock user");
    }

    @Test
    void testFindByIdWhenUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent(), "User should be found");
        assertEquals(user, result.get(), "Returned user should match the mock user");
    }

    @Test
    void testAddUserWithDailyCalorieLimit() {
        double dailyCalorieLimit = 2000.0;
        when(calorieCalculatorFactory.getCalorieCalculator(user.getGoal())).thenReturn(calorieCalculator);
        when(calorieCalculator.calculateDailyCalories(user)).thenReturn(dailyCalorieLimit);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result, "User should not be null after saving");
        assertEquals(dailyCalorieLimit, result.getDailyCalorieLimit(), "Daily calorie limit should be set correctly");
        verify(userRepository, times(1)).save(user);
    }
}