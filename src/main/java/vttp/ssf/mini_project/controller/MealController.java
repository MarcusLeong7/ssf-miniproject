package vttp.ssf.mini_project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.service.MealService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class MealController {

    @Autowired
    private MealService mealSvc;

    @GetMapping("/search")
    public String getMeals(
            @RequestParam(required = false, defaultValue = "1000") Integer maxCalories,
            @RequestParam(required = false, defaultValue = "0") String minProtein,
            @RequestParam(required = false, defaultValue = "100") String maxCarbs,
            @RequestParam(required = false, defaultValue = "100") String maxFats,
            Model model,
            HttpSession session
    ) {


        // Fetch list of meals from the service
        List<Meal> meals = mealSvc.getMeals(maxCalories, minProtein, maxCarbs, maxFats);

        // Store list of meals in the session
        // Set API data for caching
        session.setAttribute("meals", meals);
        // Add data to the model
        model.addAttribute("meals", meals);

        return "home";
    }

    @GetMapping("/recipes/{id}/information")
    public String getRecipeInformation(@PathVariable String id, Model model) {
        // Fetch recipe url from service
        String recipeUrl = mealSvc.getRecipesUrl(id);
        // Redirect to recipe's url
        return "redirect:" + recipeUrl;
    }

    @PostMapping("/selected-meals")
    public String selectedMeals(
            @RequestParam(value = "mealIds", required = false) List<String> mealIds,
            Model model, HttpSession session) {

        System.out.println(mealIds);

        // Retrieve meals from the session
        @SuppressWarnings("unchecked")
        List<Meal> meals = (List<Meal>) session.getAttribute("meals");

        if (mealIds == null || mealIds.isEmpty()) {
            model.addAttribute("message", "No meals have been selected!");
            model.addAttribute("meals", meals);
            return "home";
        }

        System.out.println(meals);

        // Filter and save selected meals
        meals.stream()
                .filter(meal -> mealIds.contains(meal.getId())) // Filter selected meals
                .forEach(meal -> mealSvc.save(meal)); // Save each selected meal

        //Filter and stream selected meals
        List<Meal> selectedMeals = meals.stream()
                .filter(meal -> mealIds.contains(meal.getId()))
                .toList();

        System.out.println("Selected Meals:" + selectedMeals);

       Map<String,Integer> totals = mealSvc.calculateNutritionTotals(selectedMeals);

        model.addAttribute("meals", selectedMeals);
        model.addAttribute("totalCalories", totals.get("totalCalories"));
        model.addAttribute("totalProtein", totals.get("totalProtein"));
        model.addAttribute("totalFats", totals.get("totalFats"));
        model.addAttribute("totalCarbs", totals.get("totalCarbs"));

        return "selectedmeals";

    }

}
