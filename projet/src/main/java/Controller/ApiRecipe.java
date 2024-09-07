package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRecipe {

    private String recipe_name,cuisine_type, includeIngredients;
    private String preparation_time_in_mn, number_of_expected_results,meal_type,diet_type;
    private String max_calories_per_serving, intolerances, apiKey;

    public ApiRecipe(String recipe_name, String cuisine_type, String includeIngredients, String preparation_time_in_mn, String number_of_expected_results, String meal_type, String diet_type, String max_calories_per_serving, String intolerances, String apiKey) {
        this.recipe_name = recipe_name;
        this.cuisine_type = cuisine_type;
        this.includeIngredients = includeIngredients;
        this.preparation_time_in_mn = preparation_time_in_mn;
        this.number_of_expected_results = number_of_expected_results;
        this.meal_type = meal_type;
        this.diet_type = diet_type;
        this.max_calories_per_serving = max_calories_per_serving;
        this.intolerances = intolerances;
        this.apiKey = apiKey;
    }
    public String myRecipes(){

        String apiUrl = "https://api.spoonacular.com/recipes/complexSearch?apiKey="+
                this.apiKey+"&query="+this.recipe_name+"&cuisine="+this.cuisine_type+
                "&diet="+this.diet_type+"&intolerances="+this.intolerances+
                "&includeIngredients="+this.includeIngredients+"&type="+this.meal_type+
                "&maxReadyTime="+this.preparation_time_in_mn+"&sort=max-used-ingredients&" +
                "maxCalories="+this.max_calories_per_serving+"&number="+this.number_of_expected_results;
        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();

                System.out.println("API response is \n"+response.toString());
            }else {
                System.out.println("Something went wrong -> "+responseCode);
            }
            connection.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }

        return "";
    }
}
