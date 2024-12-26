package vttp.ssf.mini_project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.service.MealService;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealRestController {

    @Autowired
    private MealService mealSvc;

    // Retrieve meals based on default values
    @GetMapping
    public ResponseEntity<List<Meal>> getMeals(
            @RequestParam(required = false, defaultValue = "1000") int maxCalories,
            @RequestParam(required = false, defaultValue = "0") String minProtein,
            @RequestParam(required = false, defaultValue = "100") String maxCarbs,
            @RequestParam(required = false, defaultValue = "100") String maxFats) {
        List<Meal> meals = mealSvc.getMeals(maxCalories, minProtein, maxCarbs, maxFats);
        return ResponseEntity.ok(meals);
    }

    // Get a single meal by ID
    @GetMapping("/{mealPlanId}")
    public ResponseEntity<Meal> getMealById(@PathVariable String mealPlanId) {
        Meal meal = mealSvc.getMeal(mealPlanId);
        if (meal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meal);
    }
}
