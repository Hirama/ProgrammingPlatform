import models.UserPOJO;
import models.UserWorker;

import java.io.IOException;
import java.sql.SQLException;

import static utils.SHAHasher.hashText;

/**
 * Created by Andrey on 28.10.2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        UserPOJO userPOJO = new UserPOJO();
        userPOJO.setUserName("Andrey");
        userPOJO.setUserPassword(hashText("123123"));
        userPOJO.setUserEmail("123@123.ru");
        UserWorker userWorker = new UserWorker();
//        ConnectionPool connectionPool = new ConnectionPool();
//        System.out.println(userWorker.updateUserProfile(userPOJO,"123@123.ru")); // Create user
//        System.out.println(connectionPool.getCon().toString());
//        System.out.println(userWorker.checkIfUserExists(userPOJO)); // Check if user exists by email
//        userWorker.deleteUser(userPOJO); // Delete user by email


    }
}
