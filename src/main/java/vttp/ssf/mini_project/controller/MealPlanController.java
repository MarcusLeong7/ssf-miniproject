package vttp.ssf.mini_project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vttp.ssf.mini_project.model.LoginUser;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.model.MealPlan;
import vttp.ssf.mini_project.service.MealPlanService;
import vttp.ssf.mini_project.service.MealService;

import java.util.List;

@Controller
@RequestMapping("/mealplans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanSvc;

    @Autowired
    private MealService mealSvc;


    // Method to display all meal plan ids for individual user
    @GetMapping
    public String displayMealPlansForUser(HttpSession session, Model model) {
        // Check if session exists
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("error", "User not logged in. Please log in to view meal plans.");
            model.addAttribute("user", new LoginUser());
            return "login";
        }
        List<MealPlan> mealPlans = mealPlanSvc.findAllPlansForUser(userEmail);
        model.addAttribute("mealPlans", mealPlans);
        return "mealplans"; // View to display all meal plans
    }

    @PostMapping("/save-meal-plan")
    public String saveMealPlan(
            @RequestParam String planName,
            @RequestParam(value = "mealIds", required = false) List<String> mealIds,
            Model model,
            HttpSession session
    ) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("error", "User not logged in. Please log in to save meal plans.");
            return "login";
        }

        @SuppressWarnings("unchecked")
        List<Meal> meals = (List<Meal>) session.getAttribute("meals");
        if (meals == null || mealIds == null || mealIds.isEmpty()) {
            model.addAttribute("message", "No meals selected. Please select meals to save.");
            return "selectedmeals";
        }

        // Filter selected meals from the session's meals attribute
        List<String> selectedMealIds = meals.stream()
                .filter(meal -> mealIds.contains(meal.getId()))
                .map(Meal::getId)
                .toList();

        MealPlan mealplan = new MealPlan();
        mealplan.setUserEmail(userEmail);
        mealplan.setName(planName);
        mealplan.setMealIds(selectedMealIds);
        mealPlanSvc.insert(mealplan);


        model.addAttribute("message", "Meal plan successfully saved.");

        return "redirect:/mealplans";
    }


    // Get mapping for individual meal plan
    @GetMapping("/{mealPlanId}")
    public String displayMealPlan(
            @PathVariable String mealPlanId,
             Model model, HttpSession session) {

        // Get user session
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("error", "User not logged in. Please log in to view meal plans.");
            model.addAttribute("user", new LoginUser());
            return "login";
        }

        // Retrieve mealplan id
        MealPlan mealplan = mealPlanSvc.findById(userEmail,mealPlanId);
        // Retrieve all the meals within mealplan
        List<String> mealIds = mealplan.getMealIds();
        List<Meal> meals = mealSvc.getSelectedMeals(mealIds);

        model.addAttribute("mealplan",mealplan);
        model.addAttribute("meals", meals);

        return "meal-information";

    }

    // PostMapping for deleting individual meal plan
    @PostMapping("/{mealPlanId}/delete")
    public String deleteMealPlan(
            @PathVariable String mealPlanId,
            Model model, HttpSession session) {

        // Get user session
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("error", "User not logged in. Please log in to view meal plans.");
            return "login";
        }

        mealPlanSvc.deleteById(userEmail,mealPlanId);
        model.addAttribute("message", "Meal plan successfully deleted.");
        return "redirect:/mealplans";
    }

}