package vttp.ssf.mini_project.model;

public class Meal {

    private String title;
    private String image;
    private int calories;
    private String protein;
    private String carbs;
    private String fats;

    public Meal() {
    }

    public Meal(String title, String image, int calories, String protein, String carbs, String fats) {
        this.title = title;
        this.image = image;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "title='" + title + '\'' +
                ", imageurl='" + image + '\'' +
                ", calories=" + calories +
                ", protein='" + protein + '\'' +
                ", carbs='" + carbs + '\'' +
                ", fats='" + fats + '\'' +
                '}';
    }
}


