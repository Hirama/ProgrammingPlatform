package dbhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Andrey on 28.10.2016.
 */

public class DatabaseHelper implements JDBCWorker, Closeable {
    private static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private Connection connectionDB;

    {
        String classPath = "org.postgresql.Driver";
        String dbpassword = "dacota20";
        String dblogin = "postgres";
        String url = "jdbc:postgresql://localhost:5432/test";
        connectionDB = establishConnection(classPath, url, dblogin, dbpassword);
    }

    public DatabaseHelper() throws SQLException, ClassNotFoundException {
    }

    /**
     * Create connection to db
     * @param classPath
     * @param url
     * @param dblogin
     * @param dbpassword
     * @return db connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    /**
     * Return connection
     * @param classPath
     * @param url
     * @param dblogin
     * @param dbpassword
     * @return connection to db
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public Connection establishConnection(String classPath, String url, String dblogin, String dbpassword) throws ClassNotFoundException, SQLException {
        Class.forName(classPath);
        Connection connection;
        connection = DriverManager.getConnection(url, dblogin, dbpassword);
        logger.info("Connection to db established!");
        return connection;
    }

    /**
     * Get connection
     * @return db connection
     */
    public Connection getConnectionDB(){
        return this.connectionDB;
    }

    /**
     * Close connection to db
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        try {
            this.connectionDB.close();
            logger.info("Connection was closed!");
        } catch (SQLException e) {
            logger.trace("Sql exception "+e.toString());
        }
    }
}
