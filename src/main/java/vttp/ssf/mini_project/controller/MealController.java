package vttp.ssf.mini_project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.service.MealService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class MealController {

    @Autowired
    private MealService mealSvc;

    @GetMapping("/search")
    public String getMeals(
            @RequestParam(required = false, defaultValue = "0") Integer maxCalories,
            @RequestParam(required = false) String minProtein,
            @RequestParam(required = false) String maxCarbs,
            @RequestParam(required = false) String maxFats,
            Model model,
            HttpSession session
    ) {


        // Fetch list of meals from the service
        List<Meal> meals = mealSvc.getMeals(maxCalories, minProtein, maxCarbs, maxFats);

        // Store list of meals in the session
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
        //Filter and stream selected meals
        List<Meal> selectedMeals = meals.stream()
                .filter(meal -> mealIds.contains(meal.getId()))
                .toList();

        System.out.println("Selected Meals:" + selectedMeals);

        // Calculate total calories and macronutrients
        int totalCalories = selectedMeals.stream()
                .mapToInt(meal -> Integer.parseInt(meal.getCalories()))
                .sum();
        int totalProtein = selectedMeals.stream()
                .mapToInt(meal -> Integer.parseInt(meal.getProtein().replace("g", "")))
                .sum();
        int totalFats = selectedMeals.stream()
                .mapToInt(meal -> Integer.parseInt(meal.getFats().replace("g", "")))
                .sum();
        int totalCarbs = selectedMeals.stream()
                .mapToInt(meal -> Integer.parseInt(meal.getCarbs().replace("g", "")))
                .sum();

        // Success message
        model.addAttribute("message", "These meals have been selected!");
        model.addAttribute("meals", selectedMeals);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalFats", totalFats);
        model.addAttribute("totalCarbs", totalCarbs);

        return "selectedmeals";

    }

}
