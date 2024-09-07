package Controller;

import Model.RecipeService;
import Model.Selection;
import Model.SelectionService;

import java.util.List;

public class SelectionController {

    SelectionService selectionService = new SelectionService();
    RecipeService recipeService = new RecipeService();

    public void saveRecipe(String title, String ingredients, String selection_name){
        recipeService.insert(title,ingredients,selection_name);
    }
    public void insertSelection(String selection_name, String selection_desc){
        selectionService.insert(selection_name,selection_desc);
    }

    public List<Selection> select(){
        return selectionService.select();
    }

    public void updateSelection(int selection_id, String selection_name, String selection_description){
        selectionService.update(selection_id, selection_name, selection_description);
    }
    public int totalRecipe(String selection_name){
        return recipeService.totalRecipeInSelection(selection_name);
    }

    public void deleteRecipe(int id_recipe){
        recipeService.deleteRecipeFromSelection(id_recipe);
    }
    public void delete(int id_selection){
        selectionService.delete(id_selection);
    }
}
