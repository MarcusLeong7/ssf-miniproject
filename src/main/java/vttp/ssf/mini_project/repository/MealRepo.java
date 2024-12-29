package vttp.ssf.mini_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.mini_project.model.Meal;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MealRepo {

    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate<String,Object> template;

    // METHODS FOR MEALS without session
    // Save a selected meal
    public void saveMeal(Meal meal) {
        template.opsForHash().put("MEALS",meal.getId(),meal);
    }
    // Find a meal by ID
    public Meal findMealById(String mealId) {
        return (Meal) template.opsForHash().get("MEALS", mealId);
    }

    // Find all meals by IDs
    public List<Meal> findMealsByIds (List<String> mealIds) {
        List<Meal> meals = new ArrayList<>();
        for (String mealId : mealIds) {
            Meal meal = findMealById(mealId);
            meals.add(meal);
        }
        return meals;
    }


}
