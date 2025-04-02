package example.myexample.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dish")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "calories")
    @Min(1)
    private int calories;

    @Column(name = "proteins")
    @Min(1)
    private double proteins;

    @Column(name = "fats")
    @Min(1)
    private double fats;

    @Column(name = "carbs")
    @Min(1)
    private double carbs;
}

