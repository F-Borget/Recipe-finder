package Model;

import java.sql.*;

public class UserService {
    ConnectionToDB connectionToDB = new ConnectionToDB();
    Connection connection = connectionToDB.connectToDB();


    // CREATE STATEMENT
    public void createUser(){
        String query = "CREATE TABLE IF NOT EXISTS user(" +
                "user_id INTEGER PRIMARY KEY ," +
                "username TEXT," +
                "email TEXT," +
                "password TEXT," +
                "CONSTRAINT email_unique UNIQUE (email));";
        if (connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.execute();
                System.out.println("TABLE USER CREATED WITH SUCCESS");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    // INSERT STATEMENT
    public int register(String username,String email, String password){
        String encryptPassword = EncryptDecryptPassword.encryptPassword(password);
        String query = "INSERT INTO user (username,email,password)" +
                "VALUES ('"+username+"','"+email+"','"+encryptPassword+"');";
        createUser();
        int rowAffected = 0;
        if (connection!=null){
            try(Statement statement = connection.createStatement()) {
                rowAffected = statement.executeUpdate(query);
                System.out.println("REGISTER USER DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return rowAffected;
    }

    public void updateUser(String username,String email, String password, String id){
        String encryptPassword = EncryptDecryptPassword.encryptPassword(password);
        String query = "UPDATE user " +
                "SET username = '" + username +
                "', email = '" +email+
                "', password = '" +encryptPassword+
                "' WHERE ingredient_id = "+id+";";
        if(connection!=null){
            try (Statement pragmaStatement = connection.createStatement()) {
                pragmaStatement.execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("UPDATE USER DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    public int checkUserEmail(String email){
        String query = "SELECT COUNT(*) FROM user WHERE email = '"+email+"';";
        int count = 0;
        if (connection!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    count = Integer.parseInt(resultSet.getString(1));
                    System.out.println(count);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    // DELETE STATEMENT
    public void deleteAccount(int id){
        String query = "DELETE FROM user WHERE user_id = "+id+";";
        if(connection!=null){
            try (Statement pragmaStatement = connection.createStatement()) {
                pragmaStatement.execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("DELETE USER ACCOUNT DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    public int checkUserExistance(String username,String email, String password){
        String query1 = "SELECT password FROM user WHERE username = '"+username+"' AND email = '"+email+"';";
        String query = "SELECT user_id FROM user WHERE username = '"+username+"' AND email = '"+email+"';";
        String encryptedPassword = null;
        int count = 0;
        int userID = 0;
        if (connection!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query1);
                while (resultSet.next()){
                    encryptedPassword = resultSet.getString(1);
                }
                if (EncryptDecryptPassword.checkPassword(password,encryptedPassword)){
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()){
                        userID = Integer.parseInt(resultSet.getString(1));
                        count++;
                    }
                    if(count!=1){
                        userID = 0;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userID;
    }

    public void delete(){
        String query = "DELETE FROM user";
        if(connection!=null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
                System.out.println("RESET USERS DONE WITH SUCCESS.");
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

}
