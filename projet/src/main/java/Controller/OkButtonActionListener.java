
package Controller;


import java.util.ArrayList;
import java.util.List;
import Model.Api; 
import Model.RecipeAPI; 
import javax.swing.JOptionPane;

public class OkButtonActionListener  {
   
    public List<RecipeAPI> actionPerformed(ArrayList<Object> formResult) {
        try {
            // Create an instance of the Spoonacular API
            Api spoonacularApi = new Api();

            // Set form parameters for complex search
            spoonacularApi.setFormParameters(formResult.get(0).toString(), formResult.get(5).toString(),
    				Integer.parseInt(formResult.get(2).toString()), formResult.get(8).toString(),
    				formResult.get(4).toString(), formResult.get(0).toString(), formResult.get(6).toString(),
    				Integer.parseInt(formResult.get(2).toString()), formResult.get(9).toString()/*,Integer.parseInt(formResult.get(3).toString())*/);
    		
            // Perform API request for complex search
            List<RecipeAPI> recipesComplexSearch = spoonacularApi.performComplexSearchApiRequest();

            // Prints results to the terminal to test
            
            return recipesComplexSearch;

        } catch (Exception generalException) {
            generalException.printStackTrace(); // Log the exception or use a logging framework
            displayErrorMessage("An unexpected error occurred. Please try again later.");
            return null;
        }
         
    }

    private void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
