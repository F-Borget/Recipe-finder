package Model;
import Controller.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientService {

    ConnectionToDB connectionToDB = new ConnectionToDB();
    Connection connection = connectionToDB.connectToDB();


    // CREATE STATEMENT
    public void createIngredient(){
        String query = "CREATE TABLE IF NOT EXISTS ingredient(" +
                "ingredient_id INTEGER PRIMARY KEY ," +
                "ingredient_name VARCHAR(1000)," +
                "expiration_date DATE," +
                "quantity FLOAT," +
                "unity VARCHAR(7)," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE);";
        if (connection!=null){
            try(Statement statement = connection.createStatement()){
                statement.execute(query);
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // INSERT STATEMENT
    public void insert(String ingredientName,String expirationDate, String quantity,String unity){
        String query = "INSERT INTO ingredient (ingredient_name,expiration_date, quantity,unity,user_id)" +
                "VALUES ('"+ingredientName+"','"+expirationDate+"','"+quantity+"','"+unity+"',"+ Application.userID+");";
        createIngredient();
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON;");
                statement.executeUpdate(query);
                System.out.println("INSERT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // SELECT STATEMENT
    public List<IngredientAPI> select(){
        List<IngredientAPI> ingredientAPIList = new ArrayList<IngredientAPI>();
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ingredient WHERE user_id = "+ Application.userID +" ORDER BY ingredient_name;");
                while (resultSet.next()){
                    int ingredient_id = Integer.parseInt(resultSet.getString("ingredient_id"));
                    String ingredient_name = resultSet.getString("ingredient_name");
                    String expiration_date = resultSet.getString("expiration_date");
                    Double quantity = Double.parseDouble(resultSet.getString("quantity"));
                    String unity = resultSet.getString("unity");
                    ingredientAPIList.add(new IngredientAPI(ingredient_id,ingredient_name,expiration_date,quantity,unity));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientAPIList;
    }

    // UPDATE STATEMENT
    public void update(String ingredientName,String expirationDate, String quantity,String unity, String id){
        String query = "UPDATE ingredient " +
                "SET ingredient_name = '" + ingredientName +
                "', expiration_date = '" +expirationDate+
                "', quantity = '" +quantity+
                "', unity = '"+unity+
                "' WHERE ingredient_id = "+id+";";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("UPDATE INGREDIENT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    
    // DELETE STATEMENT
    public void deleteIngredient(String ingredientID){
        String query = "delete from ingredient where ingredient_id="+ingredientID+" AND user_id = "+ Application.userID+";";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DELETE INGREDIENT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    public void delete(){
        String query = "delete from ingredient WHERE user_id = "+ Application.userID+";";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DELETE INGREDIENT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // SELECT SPECIFIC SEARCH
    public List<IngredientAPI> selectByIngredientAPI(){
        List<IngredientAPI> ingredientAPIList = new ArrayList<IngredientAPI>();
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ingredient WHERE user_id = "+ Application.userID +" ORDER BY ingredient_name;");
                while (resultSet.next()){
                    int ingredient_id = Integer.parseInt(resultSet.getString("ingredient_id"));
                    String ingredient_name = resultSet.getString("ingredient_name");
                    String expiration_date = resultSet.getString("expiration_date");
                    Double quantity = Double.parseDouble(resultSet.getString("quantity"));
                    String unity = resultSet.getString("unity");
                    IngredientAPI ingredientAPI = null;
                    if(ingredientAPIList.size()>0){
                        ingredientAPI = ingredientAPIList.stream().filter(e->e.getName().equals(ingredient_name)).findFirst().orElse(null);
                    }
                    if ((ingredientAPI!=null)&&(ingredientAPI.getUnit().equals(unity))){
                        ingredientAPIList.stream().filter(element->element.getName().equals(ingredient_name)).findFirst().get().setAmount(
                                ingredientAPI.getAmount()+quantity);
                    }else{
                        ingredientAPIList.add(new IngredientAPI(ingredient_id,ingredient_name,expiration_date,quantity,unity));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientAPIList;
    }

    public int totalIngredientsInFridge(){
        int totalIngredients = 0;
        String query = "SELECT COUNT(*) FROM ingredient WHERE user_id = "+ Application.userID +";";
        if (connection!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    totalIngredients = Integer.parseInt(resultSet.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return totalIngredients;
    }
    public void drop() {
    	String query = "drop table ingredient;";
    	if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DROP INGREDIENT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    	
    }

}
