package Controller;

import Model.ShoppingList;
import Model.ShoppingListService;

import java.util.List;

public class ShoppingListController {
    private  static ShoppingListService shoppingListService = new ShoppingListService();
    public static List<ShoppingList> listShoppingList(){
        return shoppingListService.select();

    }

    public static void insertIngredientsInShoppingList(String ingredientName,String quantity,String unity){
        shoppingListService.insert(ingredientName,quantity,unity);
    }

    public void updateIngredientInShoppingList(String ingredientName,String quantity,String unity, String id){
        shoppingListService.update(ingredientName,quantity,unity,id);
    }
    protected void deleteIngredientFromShoppingList(int ingredientID){
        shoppingListService.delete(ingredientID);
    }
    public static void resetShoppingList(){
        shoppingListService.delete();
    }

    public static int countIngredientsInShoppingList(){
        return shoppingListService.totalIngredientsInShoppingList();
    }

    public void downloadShoppingList(){
        shoppingListService.downloadShoppingList();
    }
}
