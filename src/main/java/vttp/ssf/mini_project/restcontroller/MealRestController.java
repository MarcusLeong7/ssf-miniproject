package vttp.ssf.mini_project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.model.MealPlan;
import vttp.ssf.mini_project.service.MealPlanService;
import vttp.ssf.mini_project.service.MealService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MealRestController {

    @Autowired
    private MealService mealSvc;

    @Autowired
    private MealPlanService mealPlanSvc;

    // Retrieve meals based on default values
    // Returns a list of meals
    @GetMapping(path = "/meals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> getMeals(
            @RequestParam(required = false, defaultValue = "1000") int maxCalories,
            @RequestParam(required = false, defaultValue = "0") String minProtein,
            @RequestParam(required = false, defaultValue = "100") String maxCarbs,
            @RequestParam(required = false, defaultValue = "100") String maxFats) {
        List<Meal> meals = mealSvc.getMeals(maxCalories, minProtein, maxCarbs, maxFats);
        return ResponseEntity.ok(meals);
    }


    @GetMapping(path = "/mealplans/{mealPlanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMealPlanById(@RequestParam String userEmail , @PathVariable String mealPlanId) {
        // Retrieve meal plan by ID
        MealPlan mealPlan = mealPlanSvc.findById(userEmail,mealPlanId);
        if (mealPlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MealPlan with ID " + mealPlanId + " is not found.");
        }

        // Retrieve associated meals
        List<Meal> meals = mealSvc.getSelectedMeals(mealPlan.getMealIds());

        // Build a map to be pass as response
        Map<String, Object> resp = new HashMap<>();
        resp.put("MealPlan", mealPlan);
        resp.put("Meals", meals);

        return ResponseEntity.ok(resp);
    }



}
