package Model;

import Controller.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionService {

    ConnectionToDB connectionToDB = new ConnectionToDB();
    Connection connection = connectionToDB.connectToDB();

    // CREATE STATEMENT
    public void createSelection(){
        String query = "CREATE TABLE IF NOT EXISTS selection(" +
                "selection_id INTEGER PRIMARY KEY ," +
                "selection_name TEXT," +
                "selection_description TEXT," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE);";
        if(connection!=null){
            try(Statement statement = connection.createStatement()){
                statement.execute(query);
                System.out.println("CREATE SELECTION TABLE DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }

    }

    // INSERT STATEMENT
    public void insert(String name_selection, String description_selection){
        String query = "INSERT INTO selection (selection_name,selection_description,user_id)" +
                "VALUES ('"+name_selection+"','"+description_selection+"',"+ Application.userID+");";
        createSelection();
        if(connection!=null){
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                System.out.println("INSERT SELECTION DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // SELECT STATEMENT
    public List<Selection> select(){
        List<Selection> selectionList = new ArrayList<Selection>();
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM selection WHERE user_id = "+ Application.userID +" ORDER BY selection_name;");
                while (resultSet.next()){
                    int selection_id = Integer.parseInt(resultSet.getString("selection_id"));
                    String selection_name = resultSet.getString("selection_name");
                    String selection_description = resultSet.getString("selection_description");
                    selectionList.add(new Selection(selection_id,selection_name,selection_description));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return selectionList;
    }

    // UPDATE STATEMENT
    public void update(int selection_id, String selection_name, String selection_description){
        String query = "UPDATE selection SET selection_name = '"+selection_name+
                "' , selection_description = '"+selection_description+
                "' WHERE selection_id = "+selection_id+";";
        System.out.println(query);
        if(connection!=null){
            try (Statement pragmaStatement = connection.createStatement()) {
                pragmaStatement.execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("UPDATE SELECTION DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // DELETE STATEMENT
    public void delete(int id_selection){
        String query = "DELETE FROM selection WHERE selection_id="+id_selection+" AND user_id = "+ Application.userID +";";
        if(connection!=null){
            try (Statement pragmaStatement = connection.createStatement()) {
                pragmaStatement.execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DELETE SELECTION DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    
    public void drop() {
    	String query = "drop table selection;";
    	if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DROP SELECTION DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    	
    }
}
