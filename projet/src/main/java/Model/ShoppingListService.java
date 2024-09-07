package Model;

import Controller.Application;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListService {

    ConnectionToDB connectionToDB = new ConnectionToDB();
    Connection connection = connectionToDB.connectToDB();


    // CREATE STATEMENT
    public void createShoppingList(){
        String query = "CREATE TABLE IF NOT EXISTS shoppingList(" +
                "ingredient_id INTEGER PRIMARY KEY ," +
                "ingredient_name VARCHAR(1000)," +
                "quantity FLOAT," +
                "unity VARCHAR(7)," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id) " +
                " ON UPDATE CASCADE" +
                " ON DELETE CASCADE);";
        if (connection!=null){
            try(Statement statement = connection.createStatement()){
                statement.execute(query);
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // INSERT STATEMENT
    public void insert(String ingredientName, String quantity,String unity){
        String query = "INSERT INTO shoppingList (ingredient_name,quantity,unity,user_id)" +
                "VALUES ('"+ingredientName+"','"+quantity+"','"+unity+"',"+Application.userID+");";
        createShoppingList();
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                System.out.println("INSERT INGREDIENT IN SHOPPING LIST DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // SELECT STATEMENT
    public List<ShoppingList> select(){
        List<ShoppingList> shoppingList = new ArrayList<ShoppingList>();
        //deleteIngredientByQuantity("ok");
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM shoppingList WHERE user_id = "+ Application.userID +" ORDER BY ingredient_name;");
                while (resultSet.next()){
                    int ingredient_id = Integer.parseInt(resultSet.getString("ingredient_id"));
                    String ingredient_name = resultSet.getString("ingredient_name");
                    Double quantity = Double.parseDouble(resultSet.getString("quantity"));
                    String unity = resultSet.getString("unity");
                    shoppingList.add(new ShoppingList(ingredient_id,ingredient_name,quantity,unity));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return shoppingList;
    }

    // UPDATE STATEMENT
    public void update(String ingredientName, String quantity,String unity, String id){
        String query = "UPDATE shoppingList " +
                "SET ingredient_name = '" + ingredientName +
                "', quantity = '" +quantity+
                "', unity = '"+unity+
                "' WHERE ingredient_id = "+id+";";
        System.out.println(query);
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("UPDATE INGREDIENT IN SHOPPING LIST DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // DELETE STATEMENT
    public void delete(int ingredientID){
        String query = "delete from shoppingList where ingredient_id="+ingredientID+" AND user_id = "+ Application.userID +";";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DELETE INGREDIENT FROM SHOPPING LIST DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public void delete(){
        String query = "delete from shoppingList WHERE user_id = "+ Application.userID +" ;";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("RESET SHOPPING LIST DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public int totalIngredientsInShoppingList(){
        int totalIngredients = 0;
        String query = "SELECT COUNT(*) FROM shoppingList WHERE user_id = "+ Application.userID +";";
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

    public void downloadShoppingList(){
        List<ShoppingList> shoppingList = select();
        Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document,new FileOutputStream("MyShoppingList.pdf"));
            document.open();
            for(ShoppingList ingredient:shoppingList){
                document.add(new Paragraph(ingredient.getIngredient_quantity()+" "+ingredient.getIngredient_unit()+" "+ingredient.getIngredient_name()));
            }
            document.close();
            Desktop.getDesktop().open(new File("MyShoppingList.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void drop() {
    	String query = "drop table shoppingList;";
    	if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DROP SHOPPING LIST DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    	
    }

}
