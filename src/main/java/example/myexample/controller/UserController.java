package example.myexample.controller;

import example.myexample.entity.Meal;
import example.myexample.service.MealUserHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import example.myexample.entity.User;
import example.myexample.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MealUserHelperService mealUserHelperService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/{userId}/calories/check")
    public ResponseEntity<Boolean> checkCalories(@PathVariable Long userId) {
        return ResponseEntity.ok(mealUserHelperService.checkCalories(userId));
    }
}
