package Model;

public class DBTables {
    private UserService userService = new UserService();
    private IngredientService ingredientService = new IngredientService();
    private SelectionService selectionService = new SelectionService();
    private RecipeService recipeService = new RecipeService();
    private ShoppingListService shoppingListService = new ShoppingListService();

    public void createDBTables(){
        userService.createUser();
        ingredientService.createIngredient();
        selectionService.createSelection();
        recipeService.createRecipe();
        shoppingListService.createShoppingList();
    }
    public void dropDBTables(){
        ingredientService.drop();
        selectionService.drop();
        selectionService.drop();
        recipeService.drop();
        shoppingListService.drop();
    }
}
