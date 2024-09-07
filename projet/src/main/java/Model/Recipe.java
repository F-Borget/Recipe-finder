package Model;

public class Recipe {
    private int recipe_id;
    private String title;
    private String ingredients;

    private int selection_id;

    public Recipe(int recipe_id, String title, String ingredients, int selection_id) {
        this.recipe_id = recipe_id;
        this.title = title;
        this.ingredients = ingredients;
        this.selection_id = selection_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getSelection_id() {
        return selection_id;
    }

    public void setSelection_id(int selection_nid) {
        this.selection_id= selection_id;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id=" + recipe_id +
                ", title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", selection_name='" + selection_id + '\'' +
                '}';
    }
}
