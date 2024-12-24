package vttp.ssf.mini_project.model;

import java.util.List;

public class MealPlan {

    private String id;
    private String name;
    private String userEmail;
    private List<String> mealIds;

    public MealPlan() {
    }

    public MealPlan(String id, String name, String userEmail, List<String> mealIds) {
        this.id = id;
        this.name = name;
        this.userEmail = userEmail;
        this.mealIds = mealIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<String> getMealIds() {
        return mealIds;
    }

    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }

    @Override
    public String toString() {
        return "MealPlan{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", mealIds=" + mealIds +
                '}';
    }
}
