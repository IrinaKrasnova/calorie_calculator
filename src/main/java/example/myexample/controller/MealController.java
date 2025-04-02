package example.myexample.controller;

import example.myexample.dto.DailyMealReportDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import example.myexample.entity.Meal;
import example.myexample.service.MealService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meals")
public class MealController {
    private final MealService mealService;

    @PostMapping("/{userId}")
    public ResponseEntity<Meal> addMeal(
            @PathVariable @Positive(message = "User id must be a positive number")
            @NotNull(message = "id field is required ") Long userId,
            @RequestBody @NotEmpty(message = "Dish IDs must not be empty")
            @Valid List<@Positive(message = "Dish id must be a positive number") Long> dishIds) {
        return ResponseEntity.ok(mealService.addMeal(userId, dishIds));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<DailyMealReportDto> getMealHistory(
            @PathVariable @Positive(message = "User id must be a positive number")
            @NotNull(message = "id field is required ") Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return ResponseEntity.ok(mealService.getDailyMealReport(userId, date));
    }
}
