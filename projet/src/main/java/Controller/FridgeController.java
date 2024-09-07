package Controller;

import Model.IngredientAPI;
import Model.IngredientService;

import java.util.List;

public class FridgeController {
    private static IngredientService ingredientService = new IngredientService();
    public static List<IngredientAPI> selectIngredients(){
        return ingredientService.select();
    }

    protected void insertIngredients(String ingredientName,String expirationDate, String quantity,String unity){
        ingredientService.insert(ingredientName, expirationDate, quantity, unity);
    }

    public void updateIngredient(String ingredientName,String expirationDate, String quantity,String unity, String id){
        ingredientService.update(ingredientName,expirationDate, quantity,unity, id);
    }
    protected void deleteIngredients(String ingredientID){

        ingredientService.deleteIngredient(ingredientID);
    }
    public void resetFridge(){
        ingredientService.delete();
    }
}
