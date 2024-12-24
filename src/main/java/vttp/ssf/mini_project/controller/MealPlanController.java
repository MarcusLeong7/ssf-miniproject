package vttp.ssf.mini_project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.ssf.mini_project.model.Meal;
import vttp.ssf.mini_project.model.MealPlan;
import vttp.ssf.mini_project.service.MealPlanService;

import java.util.List;

@Controller
@RequestMapping("/mealplans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanSvc;


    // Method to display all meal plan ids for individual user
    @GetMapping
    public String displayMealPlansForUser(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("error", "User not logged in. Please log in to view meal plans.");
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
        System.out.println("test1");
        mealPlanSvc.insert(mealplan);
        System.out.println("test3");

        model.addAttribute("message", "Meal plan successfully saved.");

        return "redirect:/mealplans";
    }



}