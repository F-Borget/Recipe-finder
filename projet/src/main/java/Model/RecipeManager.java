//This class is used to generate a shopping list 
package Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RecipeManager {
    private List<RecipeAPI> recipes;
    private List<IngredientAPI> fridgeIngredients;
    
    // RecipeManager constructor using a list of RecipeAPI from a selection of recipes and a list of the ingredients
    //from the fridge
    public RecipeManager(List<RecipeAPI> recipes, List<IngredientAPI> fridgeIngredients) {
        this.recipes = recipes;
        this.fridgeIngredients = fridgeIngredients;
    }

    // Method to generate a shopping list based on recipes and ingredients in the fridge
    public List<IngredientAPI> generateShoppingList() {
        // Create a list to aggregate ingredients and their quantities
        List<IngredientAPI> aggregatedIngredients = aggregateIngredientsFromRecipes(recipes);

        // Create a shopping list of missing ingredients
        List<IngredientAPI> shoppingList = findMissingIngredients(aggregatedIngredients, fridgeIngredients);

        return shoppingList;
    }

    public List<IngredientAPI> aggregateIngredientsFromRecipes(List<RecipeAPI> recipes) {
        List<IngredientAPI> aggregatedIngredients = new ArrayList<>();

        for (RecipeAPI recipe : recipes) {
            List<IngredientAPI> recipeIngredients = recipe.getIngredients();
            if (recipeIngredients != null) {
                for (IngredientAPI ingredient : recipeIngredients) {
                    // Check if the ingredient is already in the aggregated list
                    boolean found = false;
                    for (IngredientAPI aggregatedIngredient : aggregatedIngredients) {
                        if (aggregatedIngredient.getName().equals(ingredient.getName()) &&
                            aggregatedIngredient.getUnit().equals(ingredient.getUnit())) {
                            double newAmount = aggregatedIngredient.getAmount() + ingredient.getAmount();
                            aggregatedIngredient.setAmount(newAmount);
                            found = true;
                            break;
                        }
                    }

                    // If not found, add the ingredient to the aggregated list
                    if (!found) {
                        aggregatedIngredients.add(new IngredientAPI(0, ingredient.getName(),null, ingredient.getAmount(), ingredient.getUnit()));
                    }
                }
            }
        }

        return aggregatedIngredients;
    }
    
    public static List<IngredientAPI> aggregateIngredientsFromIngredientList(List<IngredientAPI> recipeIngredients) {
        List<IngredientAPI> aggregatedIngredients = new ArrayList<>();

        
            if (recipeIngredients != null) {
                for (IngredientAPI ingredient : recipeIngredients) {
                    // Check if the ingredient is already in the aggregated list
                    boolean found = false;
                    for (IngredientAPI aggregatedIngredient : aggregatedIngredients) {
                        if (aggregatedIngredient.getName().equals(ingredient.getName()) &&
                            aggregatedIngredient.getUnit().equals(ingredient.getUnit())) {
                            double newAmount = aggregatedIngredient.getAmount() + ingredient.getAmount();
                            aggregatedIngredient.setAmount(newAmount);
                            found = true;
                            break;
                        }
                    }

                    // If not found, add the ingredient to the aggregated list
                    if (!found) {
                        aggregatedIngredients.add(new IngredientAPI(0, ingredient.getName(),"", ingredient.getAmount(), ingredient.getUnit()));
                    }
                }
            
        }

        return aggregatedIngredients;
    }
    
 // Helper method to find missing ingredients
    public static List<IngredientAPI> findMissingIngredients(List<IngredientAPI> aggregatedIngredients, List<IngredientAPI> fridgeIngredients) {
        List<IngredientAPI> shoppingList = new ArrayList<>();

        for (IngredientAPI aggregatedIngredient : aggregatedIngredients) {
            boolean foundInFridge = false;
            for (IngredientAPI fridgeIngredient : fridgeIngredients) {
                if (fridgeIngredient.getName().equals(aggregatedIngredient.getName()) &&
                    fridgeIngredient.getUnit().equals(aggregatedIngredient.getUnit())) {
                    // Calculate the quantity needed to complete the recipe
                    double quantityNeeded = aggregatedIngredient.getAmount() - fridgeIngredient.getAmount();
                     if (quantityNeeded > 0) {
                        // Add the required amount to the shopping list
                        IngredientAPI shoppingIngredient = new IngredientAPI();
                        shoppingIngredient.setName(aggregatedIngredient.getName());
                        shoppingIngredient.setUnit(aggregatedIngredient.getUnit());
                        shoppingIngredient.setAmount(quantityNeeded);
                        shoppingList.add(shoppingIngredient);
                    }
                    foundInFridge = true;
                    break;
                }
            }
            if (!foundInFridge) {
                // If not found in the fridge, add the whole quantity to the shopping list
                shoppingList.add(aggregatedIngredient);
            }
        }

        return shoppingList;
    }
    
    public static IngredientAPI convertToMetric(String originalUnit, double originalQuantity, String name) {
		// Mapping des unités impériales vers les unités métriques
		Map<String, String> unitConversionMap = new HashMap<>();
		unitConversionMap.put("cup", "g");
		unitConversionMap.put("cups", "g");
		unitConversionMap.put("handful", "unit");
		unitConversionMap.put("clove", "unit");
		unitConversionMap.put("cloves", "unit");
		unitConversionMap.put("teaspoon", "g");
		unitConversionMap.put("teaspoons", "g");
		unitConversionMap.put("serving", "unit");
		unitConversionMap.put("servings", "unit");
		unitConversionMap.put("large", "unit");
		unitConversionMap.put("medium", "unit");
		unitConversionMap.put("tablespoon", "g");
		unitConversionMap.put("tablespoons", "g");
		unitConversionMap.put("ounce", "g");
		unitConversionMap.put("ounces", "g");
		unitConversionMap.put("tbsp", "g");
		unitConversionMap.put("tbsps", "g");
		unitConversionMap.put("container", "unit");
		unitConversionMap.put("containers", "unit");

		// Conversion de l'unité
		String originalUnitLowerCase = originalUnit.toLowerCase(); // Convertir en minuscules pour la comparaison
		String convertedUnit = unitConversionMap.getOrDefault(originalUnitLowerCase, "unit");

		// Ajustement de la quantité en fonction de l'unité
		double convertedQuantity = originalQuantity;
		if (originalUnitLowerCase.equals("cup") || originalUnitLowerCase.equals("cups")) {
			// Approximation : 1 cup ≈ 240 mL
			convertedQuantity *= 24;
		} else if (originalUnitLowerCase.equals("teaspoon") || originalUnitLowerCase.equals("teaspoons")) {
			// Approximation : 1 teaspoon ≈ 5 mL
			convertedQuantity *= 0.5;
		} else if (originalUnitLowerCase.equals("tablespoon") || originalUnitLowerCase.equals("tablespoons")
				|| originalUnitLowerCase.equals("tbsp") || originalUnitLowerCase.equals("tbsps")) {
			// Approximation : 1 tablespoon ≈ 15 mL
			convertedQuantity *= 1.5;
		} else if (originalUnitLowerCase.equals("ounce") || originalUnitLowerCase.equals("ounces")) {
			// Approximation : 1 ounce ≈ 28 g
			convertedQuantity *= 28.0;
		}

		// Si l'unité d'origine ne correspond à aucune unité de la map, utilisez la
		// stratégie newName = originalUnit + " " + name
		String newName = unitConversionMap.containsKey(originalUnitLowerCase) ? name : originalUnit + " " + name;

		// Si l'unité d'origine ne correspond à aucune unité de la map, définissez
		// convertedUnit sur "unit"
		if (!unitConversionMap.containsKey(originalUnitLowerCase)) {
			convertedUnit = "unit";
		}

		return new IngredientAPI(0, newName, "", convertedQuantity, convertedUnit);
	}

}



