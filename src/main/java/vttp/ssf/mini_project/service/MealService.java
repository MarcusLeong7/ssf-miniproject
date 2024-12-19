package vttp.ssf.mini_project.service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vttp.ssf.mini_project.model.Meal;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    @Value("${my.api.key}")
    private String API_KEY;

    public static final String API_URL ="https://api.spoonacular.com/recipes/findByNutrients";

    // https://api.spoonacular.com/recipes/findByNutrients
    // ?apiKey=apikey&minCarbs=10&maxCarbs=50&number=2
    public List<Meal> getMeals(int maxCalories,String minProtein,String maxCarbs ,String maxFats) {
        //Construct the URL
        String uri = UriComponentsBuilder
                .fromUriString(API_URL)
                .queryParam("apiKey",API_KEY)
                .queryParam("maxCalories",maxCalories)
                .queryParam("minProtein", minProtein)
                .queryParam("maxCarbs",maxCarbs)
                .queryParam("maxFat",maxFats)
                .toUriString();

        // Create the GET request
        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // Use RestTemplate to send the request
        RestTemplate restTemplate = new RestTemplate();
        List<Meal> meals = new ArrayList<>();

        try{
            // Fetch response
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            String payload = response.getBody();
            System.out.println(payload);
            // Parse JSON response from a JSON String to Json Object
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonArray jsonArray = reader.readArray();
            System.out.println(">>> Processing JsonArray");

            // Extract array data
            for (JsonObject jsonObj : jsonArray.getValuesAs(JsonObject.class)){
                // Access properties of each object
                String title = jsonObj.getString("title");
                String image = jsonObj.getString("image");
                int calories = jsonObj.getInt("calories");
                String protein = jsonObj.getString("protein");
                String fat = jsonObj.getString("fat");
                String carbs = jsonObj.getString("carbs");

                // Set each meal with relevant data
                Meal meal = new Meal();
                meal.setTitle(title);
                meal.setImage(image);
                meal.setCalories(calories);
                meal.setProtein(protein);
                meal.setFats(fat);
                meal.setCarbs(carbs);
                // Add to list
                meals.add(meal);
            }
        }
        catch (Exception ex){
            System.err.printf("Error fetching data: %s\n", ex.getMessage());
        }

        return meals;
    }


}
