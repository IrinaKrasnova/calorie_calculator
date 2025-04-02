package example.myexample.calculator;

import example.myexample.entity.Gender;
import example.myexample.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BMRCalculatorTest {
    @InjectMocks
    private BMRCalculator bmrCalculator;
    private User userMale;
    private User userFemale;

    @BeforeEach
    void setUp() {
        userMale = User.builder()
                .gender(Gender.MALE)
                .weight(70.0)
                .height(175.0)
                .age(25)
                .build();

        userFemale = User.builder()
                .gender(Gender.FEMALE)
                .weight(60.0)
                .height(165.0)
                .age(30)
                .build();
    }

    @Test
    void testCalculateBMRForMale() {
        double bmr = BMRCalculator.calculateBMR(userMale);
        assertEquals(1673.75, bmr, 0.01);
    }

    @Test
    void testCalculateBMRForFemale() {
        double bmr = BMRCalculator.calculateBMR(userFemale);
        assertEquals(1320.25, bmr, 0.01);
    }
}