package dbhelper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Andrey on 28.10.2016.
 */
public interface JDBCWorker {
    Connection establishConnection(String classPath, String url, String dblogin, String dbpassword) throws ClassNotFoundException, SQLException;
}
