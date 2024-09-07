// RecipeAPI.java
//Call the Display method to display information about the recipe
package Model;

import java.util.List;

public class RecipeAPI {
    private int id;
    private String title;
    private String imageUrl;
    private String imageType;
    private List<IngredientAPI> missedIngredients;
    private List<IngredientAPI> ingredients;
    private List<StepAPI> steps;
    private String instructions;
    private int spoonacularScore;
    private int preparationTime;
    private String sourceUrl;

    // Getters and Setters
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public List<IngredientAPI> getMissedIngredients() {
        return missedIngredients;
    }

    public void setMissedIngredients(List<IngredientAPI> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public List<StepAPI> getSteps() {
        return steps;
    }

    public void setSteps(List<StepAPI> steps) {
        this.steps = steps;
    }
    public void setIngredients(List<IngredientAPI> ingredients) {
        this.ingredients = ingredients; 
    }
    public List<IngredientAPI> getIngredients() {
        return ingredients;
    }
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions; 
    }
    public int getspoonacularScore() {
    	return spoonacularScore;
    }
    public void setspoonacularScore(int spoonacularScore) {
    	this.spoonacularScore = spoonacularScore;
    }
    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    // Method to display all the information about the recipe 
    public String Display() {
        StringBuilder details = new StringBuilder();

        // Add basic recipe information
        details.append("Recipe: ").append(this.getTitle()).append("\n");
        details.append("ID: ").append(this.getId()).append("\n");
        details.append("Image URL: ").append(this.getImageUrl()).append("\n");
        details.append("Spoonacular Score: ").append(this.getspoonacularScore()).append("\n");
        details.append("Preparation Time: ").append(this.getPreparationTime()).append(" minutes\n");
        details.append("Source URL: ").append(this.getSourceUrl()).append("\n\n");

        // Add ingredients
        details.append("Ingredients:\n");
        if (this.getIngredients() != null) {
            for (IngredientAPI ingredient : this.getIngredients()) {
                String ingredientDetails = String.format("- %s - %.1f %s", ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
                details.append(ingredientDetails).append("\n");
            }
        } else {
            details.append("No ingredients available.\n");
        }
        details.append("\n");

        // Add instructions
        details.append("Instructions:\n");
        if (this.getSteps() != null) {
            for (StepAPI step : this.getSteps()) {
                details.append("Step ").append(step.getNumber()).append(": ").append(step.getStep()).append("\n");
            }
        } else {
            details.append("No instructions available.\n");
        }

        return details.toString();
    }


  
    @Override
    public String toString() {
        return "RecipeAPI{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageType='" + imageType + '\'' +
                ", missedIngredients=" + missedIngredients +
                ", steps=" + steps +
                ", preparationTime=" + preparationTime + 
                ", sourceUrl='" + sourceUrl + '\'' + 
                '}';
    }
}
