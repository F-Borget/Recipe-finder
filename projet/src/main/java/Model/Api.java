package Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Api {
	
	// Private API key 
    private String apiKey = "16f5f6291e184b88b2092facd01a5d3b";
    //First URL for the search using the form 
    private String apiUrlComplexSearch = "https://api.spoonacular.com/recipes/complexSearch";
    //Second URL for the search by ingredients
    private String apiUrlFindByIngredients = "https://api.spoonacular.com/recipes/findByIngredients";


    // Form parameters
    private String recipeName = "";
    private String cuisine = "";
    private int preparationTime = 20;
    private String ingredients = "";
    private String type = "";
    private String equipment = "";
    private String diet = "";
    private int maxCalories = 500;
    private String intolerances = "";
    
    // Set the form parameters 
    public void setFormParameters(String recipeName, String cuisine, int preparationTime, String ingredients,
                                  String type, String equipment, String diet, int maxCalories, String intolerances) {
        this.recipeName = recipeName;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.type = type;
        this.equipment = equipment;
        this.diet = diet;
        this.maxCalories = maxCalories;
        this.intolerances = intolerances;
    }

    // Performs the API request with HTTP connection for complex search with form parameters 
    //Returns a list of Recipe 
    public List<RecipeAPI> performComplexSearchApiRequest() {
        List<RecipeAPI> recipes = new ArrayList<>();

        try {
            // Build the URL with optional parameters
            String fullUrl = buildApiUrl(apiUrlComplexSearch, apiKey, recipeName, cuisine, preparationTime, ingredients, type, equipment, diet, maxCalories, intolerances);

            // Create a URL object
            URL url = new URL(fullUrl);

            // Open the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");

            // Get the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Convert the response string to a JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

            // Extract and process the recipe information
            JsonNode resultsArray = jsonResponse.get("results");
            for (JsonNode result : resultsArray) {
            	RecipeAPI recipe = getRecipeInformation(result.get("id").asInt());
                recipe.setId(result.get("id").asInt());
                recipe.setTitle(result.get("title").asText());
                recipe.setImageUrl(result.get("image").asText());
                recipe.setImageType(result.get("imageType").asText());
                recipes.add(recipe);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipes;
    }
    

    // Performs the API request with HTTP connection for finding recipes by ingredients
    //This method is used for a different goal than the performComplexSearchApiRequest(); it returns recipes that 
    // use the most ingredients. 
    //It returns a list of Recipe as well as the missing ingredients(ingredients that are not in the fridge)
    public List<RecipeAPI> performFindByIngredientsApiRequest() {
        List<RecipeAPI> recipes = new ArrayList<>();

        try {
            // Build the URL with ingredients
            String fullUrl = buildFindByIngredientsApiUrl(apiUrlFindByIngredients, apiKey, ingredients);

            // Create a URL object
            URL url = new URL(fullUrl);

            // Open the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");

            // Get the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Convert the response string to a JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

            // Extract and process the recipe information
            for (JsonNode result : jsonResponse) {
                RecipeAPI recipe = new RecipeAPI();
                recipe.setId(result.get("id").asInt());
                recipe.setTitle(result.get("title").asText());
                recipe.setImageUrl(result.get("image").asText());
                recipe.setImageType(result.get("imageType").asText());

                // Extract missed ingredient information
                JsonNode missedIngredientsArray = result.get("missedIngredients");
                List<IngredientAPI> missedIngredients = new ArrayList<>();
                for (JsonNode missedIngredient : missedIngredientsArray) {
                    IngredientAPI ingredient = new IngredientAPI();
                    ingredient.setName(missedIngredient.get("name").asText());
                    ingredient.setAmount(missedIngredient.get("amount").asDouble());
                    missedIngredients.add(ingredient);
                }

                recipe.setMissedIngredients(missedIngredients);
                recipes.add(recipe);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    
    


    // Build the API URL with the form parameters if they are not missing for complex search
    private static String buildApiUrl(String apiUrl, String apiKey, String recipeName, String cuisine, int preparationTime,
                                       String ingredients, String type, String equipment, String diet, int maxCalories, String intolerances) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl + "?apiKey=" + apiKey);

        // Add optional parameters if provided
        if (recipeName != null && !recipeName.isEmpty()) {
            urlBuilder.append("&query=").append(URLEncoder.encode(recipeName, "UTF-8"));
        }
        if (cuisine != null && !cuisine.isEmpty()) {
            urlBuilder.append("&cuisine=").append(URLEncoder.encode(cuisine, "UTF-8"));
        }
        if (preparationTime > 0) {
            urlBuilder.append("&preparationTime=").append(preparationTime);
        }
        if (ingredients != null && !ingredients.isEmpty()) {
            urlBuilder.append("&includeIngredients=").append(URLEncoder.encode(ingredients, "UTF-8"));
        }
        if (type != null && !type.isEmpty()) {
            urlBuilder.append("&type=").append(URLEncoder.encode(type, "UTF-8"));
        }
        if (equipment != null && !equipment.isEmpty()) {
            urlBuilder.append("&equipment=").append(URLEncoder.encode(equipment, "UTF-8"));
        }
        if (diet != null && !diet.isEmpty()) {
            urlBuilder.append("&diet=").append(URLEncoder.encode(diet, "UTF-8"));
        }
        if (maxCalories > 0) {
            urlBuilder.append("&maxCalories=").append(maxCalories);
        }
        if (intolerances != null && !intolerances.isEmpty()) {
            urlBuilder.append("&intolerances=").append(URLEncoder.encode(intolerances, "UTF-8"));
        }

        return urlBuilder.toString();
    }

    // Build the API URL for finding recipes by ingredients
    private static String buildFindByIngredientsApiUrl(String apiUrl, String apiKey, String ingredients) throws UnsupportedEncodingException {
        return apiUrl + "?apiKey=" + apiKey + "&ingredients=" + URLEncoder.encode(ingredients, "UTF-8");
    }
    
    private String apiUrlGetRecipeInstructions = "https://api.spoonacular.com/recipes/{recipeId}/analyzedInstructions";

        // Build the API URL for "Get Analyzed Recipe Instructions"
    private static String buildGetRecipeInstructionsApiUrl(String apiUrl, String apiKey, int recipeId) {
        return apiUrl.replace("{recipeId}", String.valueOf(recipeId)) + "?apiKey=" + apiKey;
    }
   
    
    /* 
    
    
    // Method to perform an "Analyazed Recipe Instructions" request 
    public RecipeAPI performGetRecipeInstructionsApiRequest(int recipeId) {
        RecipeAPI recipe = new RecipeAPI();

        try {
            String fullUrl = buildGetRecipeInstructionsApiUrl(apiUrlGetRecipeInstructions, apiKey, recipeId);

            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

            if (jsonResponse.isArray() && jsonResponse.size() > 0) {
                JsonNode instructions = jsonResponse.get(0);
                recipe.setId(recipeId);
                recipe.setTitle(instructions.get("name").asText());

                List<StepAPI> steps = new ArrayList<>();
                JsonNode stepsArray = instructions.get("steps");
                for (JsonNode stepNode : stepsArray) {
                    StepAPI step = new StepAPI();
                    step.setNumber(stepNode.get("number").asInt());
                    step.setStep(stepNode.get("step").asText());

                    // Extracting Equipment Information
                    List<EquipmentAPI> equipmentList = new ArrayList<>();
                    JsonNode equipmentArray = stepNode.get("equipment");
                    for (JsonNode equipmentNode : equipmentArray) {
                        EquipmentAPI equipment = new EquipmentAPI();
                        equipment.setId(equipmentNode.get("id").asInt());
                        equipment.setName(equipmentNode.get("name").asText());
                        equipment.setImage(equipmentNode.get("image").asText());

                        // Check for temperature
                        if (equipmentNode.has("temperature")) {
                            JsonNode tempNode = equipmentNode.get("temperature");
                            TemperatureAPI temperature = new TemperatureAPI();
                            temperature.setNumber(tempNode.get("number").asDouble());
                            temperature.setUnit(tempNode.get("unit").asText());
                            equipment.setTemperature(temperature);
                        }

                        equipmentList.add(equipment);
                    }
                    step.setEquipment(equipmentList);

                    // Extracting Ingredients Information
                    List<IngredientAPI> ingredientList = new ArrayList<>();
                    JsonNode ingredientsArray = stepNode.get("ingredients");
                    for (JsonNode ingredientNode : ingredientsArray) {
                        IngredientAPI ingredient = new IngredientAPI();
                        ingredient.setId(ingredientNode.get("id").asInt());
                        ingredient.setName(ingredientNode.get("name").asText());
                        ingredient.setImage(ingredientNode.get("image").asText());
                        ingredientList.add(ingredient);
                    }
                    step.setIngredients(ingredientList);

                    steps.add(step);
                }
                recipe.setSteps(steps);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipe;
    }
     */
    
    
    
    
   //Method to perform a "getRecipeInformation" request     
    public RecipeAPI getRecipeInformation(int recipeId) {
        RecipeAPI recipe = new RecipeAPI();

        try {
            String fullUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?includeNutrition=false&apiKey=" + apiKey;

            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

           
            recipe.setId(jsonResponse.get("id").asInt());
            recipe.setTitle(jsonResponse.get("title").asText());
            recipe.setImageUrl(jsonResponse.get("image").asText());
            recipe.setspoonacularScore(jsonResponse.get("spoonacularScore").asInt());
            recipe.setPreparationTime(jsonResponse.get("readyInMinutes").asInt());
            recipe.setSourceUrl(jsonResponse.get("sourceUrl").asText());
    

            // Parse and set ingredients
            JsonNode ingredientsArray = jsonResponse.get("extendedIngredients");
            List<IngredientAPI> ingredients = new ArrayList<>();
            for (JsonNode ingredientNode : ingredientsArray) {
                IngredientAPI ingredient = new IngredientAPI();
                ingredient.setId(ingredientNode.get("id").asInt());
                ingredient.setName(ingredientNode.get("name").asText());
//                ingredient.setImage(ingredientNode.get("image").asText());
                ingredient.setAmount(ingredientNode.get("amount").asDouble());
                ingredient.setUnit(ingredientNode.get("unit").asText());
       
                ingredients.add(ingredient);
            }
            recipe.setIngredients(ingredients);

         
            String instructions = jsonResponse.get("instructions").asText();
            if (instructions != null && !instructions.isEmpty()) {
               
            	String[] stepsArray = instructions.split("\\."); 
            	List<StepAPI> steps = new ArrayList<>();
            	int stepNumber = 1;
            	for (String stepText : stepsArray) {
            	    if (!stepText.trim().isEmpty()) {
            	        StepAPI step = new StepAPI();
            	        step.setNumber(stepNumber++);
            	        step.setStep(stepText.trim());
            	        steps.add(step);
            	    }
            	}
            	recipe.setSteps(steps);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipe;
    }
    
    //Example of how to use the class 
    /*public static void main(String[] args) {
    	
    	//API instance 
        Api spoonacularApi = new Api();
        // this recipId can be optained using the performComplexSearchApiRequest() method.    
        int recipeId = 666456;
        //Recipe instance    
        RecipeAPI recipe = spoonacularApi.getRecipeInformation(recipeId);

  
        if (recipe != null) {
            // Display basic details
            System.out.println(recipe.Display());
        } else {
            System.out.println("Recipe details not available for ID: " + recipeId);
        }
    }*/
}
    
    
    
        
       
    




   
  

      

      

    
    
 
    


   



    
