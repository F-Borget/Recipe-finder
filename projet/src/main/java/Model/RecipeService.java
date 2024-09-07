package Model;

import Controller.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    ConnectionToDB connectionToDB = new ConnectionToDB();
    Connection connection = connectionToDB.connectToDB();
    public void createRecipe(){
        String query = "CREATE TABLE IF NOT EXISTS recipe(" +
                "recipe_id INTEGER PRIMARY KEY ," +
                "title TEXT," +
                "ingredients TEXT," +
                "selection_id INT,"+
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE,"+
                "FOREIGN KEY(selection_id) REFERENCES selection(selection_id)" +
                " ON UPDATE CASCADE " +
                "ON DELETE CASCADE);";
        if(connection != null){
            try(Statement statement = connection.createStatement()){
                statement.execute(query);
                System.out.println("TABLE RECIPE CREATED SUCCESFULLY.");

            }catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
    }

    public void insert(String title, String ingredients, String selection_name){
        String query1 = "SELECT DISTINCT selection_id FROM selection WHERE selection_name='"+selection_name+"' AND user_id = "+ Application.userID+";";
        int selection_id = 0;
        createRecipe();
        if(connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query1);
                while (resultSet.next()){
                    selection_id = Integer.parseInt(resultSet.getString("selection_id"));
                }
                String query2 = "INSERT INTO recipe (title,ingredients, selection_id,user_id)" +
                        "VALUES ('"+title+"','"+ingredients+"','"+selection_id+"',"+Application.userID+");";
                statement.executeUpdate(query2);
                System.out.println("INSERT RECIPE DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // SELECT STATEMENT
    public List<Recipe> select(String selection_name){
        List<Recipe> selectionRecipes= new ArrayList<Recipe>();
        String query = "SELECT * FROM recipe WHERE selection_id=(SELECT DISTINCT selection_id FROM selection WHERE selection_name='"+selection_name+"' AND user_id = "+ Application.userID+") AND user_id = "+ Application.userID+";";
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    int recipe_id = Integer.parseInt(resultSet.getString("recipe_id"));
                    String title= resultSet.getString("title");
                    String ingredients  = resultSet.getString("ingredients");
                    int selection_id = Integer.parseInt(resultSet.getString("selection_id"));
                    selectionRecipes.add(new Recipe(recipe_id,title,ingredients,selection_id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return selectionRecipes;
    }

    public int totalRecipeInSelection(String selection_name){
        int totatRecipesInSelection = 0;
        String query = "SELECT COUNT(*) FROM recipe " +
                "WHERE selection_id=(SELECT DISTINCT selection_id " +
                "FROM selection WHERE selection_name='"+selection_name+"' AND user_id = "+ Application.userID+") " +
                "AND user_id = "+ Application.userID+";";
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    totatRecipesInSelection = Integer.parseInt(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totatRecipesInSelection;
    }

    public void deleteRecipeFromSelection(int id_recipe){
        String query = "DELETE FROM recipe WHERE recipe_id="+id_recipe+" AND user_id = "+ Application.userID +";";
        System.out.println("Let's delete this");
        if (connection!=null){
        	System.out.println("Connection not null");
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void drop() {
    	String query = "drop table recipe;";
    	if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DROP RECIPE DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    	
    }
}
