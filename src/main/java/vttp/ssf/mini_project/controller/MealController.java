package vttp.ssf.mini_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.service.MealService;

import java.util.List;

@Controller
@RequestMapping
public class MealController {

    @Autowired
    private MealService mealSvc;

    @GetMapping("/search")
    public String getMeals(
            @RequestParam(required = false,defaultValue = "0") Integer maxCalories,
            @RequestParam(required = false) String minProtein,
            @RequestParam(required = false) String maxCarbs,
            @RequestParam(required = false) String maxFats,
            Model model
    ) {


        // Fetch list of meals from the service
        List<Meal> meals = mealSvc.getMeals(maxCalories, minProtein, maxCarbs, maxFats);
        model.addAttribute("meals", meals);

        return "home";
    }

    @GetMapping("/recipes/{id}/information")
    public String getRecipeInformation(@PathVariable String id,Model model) {
        // Fetch recipe url from service
        String recipeUrl = mealSvc.getRecipesUrl(id);
        // Redirect to recipe's url
        return "redirect:" + recipeUrl;
    }

}
