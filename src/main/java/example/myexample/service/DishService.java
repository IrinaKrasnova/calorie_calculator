package example.myexample.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import example.myexample.entity.Dish;
import example.myexample.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public List<Dish> findDishes (List<Long> dishIds) {
       List<Dish> dishes = dishRepository.findAllById(dishIds);
        if (dishes.size() != dishIds.size()) {
            List<Long> foundIds = dishes.stream()
                    .map(Dish::getId)
                    .toList();
            List<Long> missingIds = dishIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new EntityNotFoundException("Dishes not found for IDs: " + missingIds);
        }
        return dishes;
    }

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }
}
