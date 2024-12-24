package vttp.ssf.mini_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.ssf.mini_project.model.MealPlan;
import vttp.ssf.mini_project.repository.MealPlanRepo;

import java.util.List;
import java.util.UUID;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepo mealPlanRepo;

    public void insert(MealPlan mealPlan) {

        if (mealPlan.getUserEmail() == null || mealPlan.getUserEmail().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        mealPlan.setId(id);
        mealPlanRepo.save(mealPlan);
    }

    public List<MealPlan> findAllPlansForUser(String userEmail) {
        return mealPlanRepo.findByUserEmail(userEmail);
    }

    public MealPlan findById(String userEmail,String mealPlanId) {
        return mealPlanRepo.findMealPlanById(userEmail,mealPlanId);
    }

    public void deleteById(String userEmail,String mealPlanId) {
        mealPlanRepo.deleteByUserIdAndPlanId(userEmail,mealPlanId);
    }
}
