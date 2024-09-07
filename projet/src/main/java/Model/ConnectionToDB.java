package Model;


import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionToDB {
    Connection connection = null;

    // CREATE CONNECTION TO DB
    public Connection connectToDB(){
    	if (connection == null) {
            try {
                connection = DataBaseManager.getDataSource().getConnection();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
    	}
        return connection;
    }
    public void closeConnection(Connection connection){
        if(connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
