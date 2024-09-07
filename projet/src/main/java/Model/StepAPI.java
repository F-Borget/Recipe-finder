// StepAPI.java
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a step in a recipe with associated equipment, ingredients, step number, and description.
 */
public class StepAPI {
    private List<EquipmentAPI> equipment;
    private List<IngredientAPI> ingredients;
    private int number;
    private String step;

    // Constructors
    public StepAPI() {
        this.ingredients = new ArrayList<>();
        this.equipment = new ArrayList<>();
    }

    // Getters and Setters
    public List<EquipmentAPI> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentAPI> equipment) {
        this.equipment = equipment;
    }

    public List<IngredientAPI> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientAPI> ingredients) {
        this.ingredients = ingredients;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    // Overrides toString method for convenient debugging
    @Override
    public String toString() {
        return "StepAPI{" +
                "equipment=" + equipment +
                ", ingredients=" + ingredients +
                ", number=" + number +
                ", step='" + step + '\'' +
                '}';
    }
}
