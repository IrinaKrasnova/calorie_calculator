package example.myexample.entity;

import example.myexample.entity.Gender;
import example.myexample.entity.Goal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import example.myexample.calculator.CalorieCalculator;
import example.myexample.calculator.CalorieCalculatorFactory;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 64, nullable = false)
    @NotBlank(message = "Имя пользователя не может быть пустым.")
    private String username;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "age")
    @NotNull(message = "Возраст обязателен для заполнения.")
    @Min(value = 12, message = "Возраст должен быть не менее 12 лет")
    @Max(value = 100, message = "Возраст не может превышать 100 лет")
    private Integer age;

    @Column(name = "weight")
    @NotNull(message = "Вес обязателен для заполнения.")
    @Min(value = 30, message = "Вес должен быть не менее 30 кг")
    private Double weight;

    @Column(name = "height")
    @NotNull(message = "Рост обязателен для заполнения.")
    @Min(value = 120, message = "Рост должен быть не менее 120 см")
    private Double height;

    @Column(name = "goal", nullable = false)
    @NotNull(message = "Цель обязательно должна быть указана.")
    @Enumerated(EnumType.ORDINAL)
    private Goal goal;

    @Column(name = "gender", nullable = false)
    @NotNull(message = "Пол обязательно должен быть указан.")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "dailyCalorieLimit")
    private double dailyCalorieLimit;
}
