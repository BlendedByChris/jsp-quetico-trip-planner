package jsp_quetico_trip_planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB Canoe
 *
 * This class abstracts the canoe database for use as an object
 * @author cleblanc
 */
public class DbCanoe {
    private static Connection connection = null;

    private static String url = "jdbc:odbc:timetrex";
    private static String username = "";
    private static String password = "";

    /**
     * Constructor
     *
     * Sets the connection for use
     */
    public DbCanoe() throws SQLException
    {
        connection = getConnection();
    }

    /**
     * Get All Canoes
     *
     * Get all canoes from the database
     * @return ResultSet
     */
    public ResultSet getAllCanoes() throws SQLException
    {
        String query = "SELECT * FROM Canoe";
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        return statement.executeQuery(query);
    }

    /**
     * Get Canoe By Canoe
     *
     * Returns a resultset for a specified canoe
     * 
     * @return ResultSet
     */
    public ResultSet getCanoeByCanoe(String canoe) throws SQLException
    {
        String query = "SELECT * " +
                       "FROM Canoe " +
                       "WHERE clCanoe = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, canoe);
        return statement.executeQuery();
    }

    /**
     * Get Connection
     * 
     * Returns a connection to the database.
     *
     * @return Connection
     */
    private Connection getConnection() throws SQLException
    {
       return DriverManager.getConnection(url, username, password);
    }
}
