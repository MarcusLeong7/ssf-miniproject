package vttp.ssf.mini_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.mini_project.model.MealPlan;

import java.util.List;

@Repository
public class MealPlanRepo {

    @Autowired
    private RedisTemplate<String,Object> template;

    // Save a meal plan
    public void save(MealPlan mealPlan) {
        template.opsForHash().put(mealPlan.getUserEmail(), mealPlan.getId(), mealPlan);
    }

    // Retrieve all meal plans for a user
    public List<MealPlan> findByUserEmail(String userEmail) {
        return template.opsForHash().values(userEmail)
                .stream()
                .map(obj -> (MealPlan) obj)
                .toList();
    }

    public MealPlan findMealPlanById(String userEmail,String mealPlanId) {
        return (MealPlan) template.opsForHash().get(userEmail, mealPlanId);
    }

    // Delete a meal plan
    public void deleteByUserIdAndPlanId(String userEmail, String planId) {
        template.opsForHash().delete(userEmail, planId);
    }
}
