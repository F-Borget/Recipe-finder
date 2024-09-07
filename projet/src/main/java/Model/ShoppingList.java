package Model;

public class ShoppingList {
    private int id_ingredient;
    private String ingredient_name;
    private Double ingredient_quantity;
    private String ingredient_unit;
    
    public ShoppingList() {
    	
    }

    public ShoppingList(int id_ingredient, String ingredient_name, Double ingredient_quantity, String ingredient_unit) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
        this.ingredient_quantity = ingredient_quantity;
        this.ingredient_unit = ingredient_unit;
    }

    public int getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public Double getIngredient_quantity() {
        return ingredient_quantity;
    }

    public void setIngredient_quantity(Double ingredient_quantity) {
        this.ingredient_quantity = ingredient_quantity;
    }

    public String getIngredient_unit() {
        return ingredient_unit;
    }

    public void setIngredient_unit(String ingredient_unit) {
        this.ingredient_unit = ingredient_unit;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id_ingredient=" + id_ingredient +
                ", ingredient_name='" + ingredient_name + '\'' +
                ", ingredient_quantity='" + ingredient_quantity + '\'' +
                ", ingredient_unit='" + ingredient_unit + '\'' +
                '}';
    }
}
