package example.myexample.service;

import example.myexample.entity.Dish;
import example.myexample.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish dish1;
    private Dish dish2;

    @BeforeEach
    void setUp() {
        dish1 = Dish.builder()
                .id(1L)
                .name("Dish1")
                .build();

        dish2 = Dish.builder()
                .id(2L)
                .name("Dish2")
                .build();
    }

    @Test
    void testFindDishesWhenAllIdsExist() {
        List<Long> dishIds = Arrays.asList(1L, 2L);
        List<Dish> dishes = Arrays.asList(dish1, dish2);

        when(dishRepository.findAllById(dishIds)).thenReturn(dishes);

        List<Dish> result = dishService.findDishes(dishIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dish1));
        assertTrue(result.contains(dish2));
    }

    @Test
    void testFindDishesWhenSomeIdsDoNotExist() {
        List<Long> dishIds = Arrays.asList(1L, 3L);
        List<Dish> dishes = Arrays.asList(dish1);

        when(dishRepository.findAllById(dishIds)).thenReturn(dishes);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            dishService.findDishes(dishIds);
        });

        assertEquals("Dishes not found for IDs: [3]", exception.getMessage());
    }

    @Test
    void testAddDish() {
        when(dishRepository.save(any(Dish.class))).thenReturn(dish1);

        Dish savedDish = dishService.addDish(dish1);

        assertNotNull(savedDish);
        assertEquals("Dish1", savedDish.getName());
        verify(dishRepository, times(1)).save(dish1);
    }
}