package Model;

import java.util.ArrayList;
import java.util.List;

public class ConvertIngredientToShoppingList {

	
	public static List<IngredientAPI> convertToIngredientAPI(List<ShoppingList> shoppingList) {
		List<IngredientAPI> ingredientList = new ArrayList<IngredientAPI>();
		for (int i=0; i<shoppingList.size(); i++) {
			IngredientAPI ingredient = new IngredientAPI();
			ingredient.setName(shoppingList.get(i).getIngredient_name());
			ingredient.setAmount(shoppingList.get(i).getIngredient_quantity());
			ingredient.setUnit(shoppingList.get(i).getIngredient_unit());
			ingredient.setId(shoppingList.get(i).getId_ingredient());
			ingredientList.add(ingredient);
		}
		return ingredientList;
	}
	
	public static List<ShoppingList> convertToShoppingList(List<IngredientAPI> ingredientList) {
		List<ShoppingList> shoppingList = new ArrayList<ShoppingList>();
		for (int i=0; i<ingredientList.size(); i++) {
			ShoppingList shopping = new ShoppingList();
			shopping.setIngredient_name(ingredientList.get(i).getName());
			shopping.setIngredient_quantity(ingredientList.get(i).getAmount());
			shopping.setIngredient_unit(ingredientList.get(i).getUnit());
			shopping.setId_ingredient(ingredientList.get(i).getId());
			shoppingList.add(shopping);
		}
		return shoppingList;
	}
}
