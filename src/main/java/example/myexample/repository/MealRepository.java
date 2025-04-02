package example.myexample.repository;

import example.myexample.entity.Meal;
import example.myexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
