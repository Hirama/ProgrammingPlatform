package models;

import dbhelper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.SHAHasher.hashText;

/**
 * Created by Andrey on 28.10.2016.
 */
public class UserWorker {
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private static Logger logger = LoggerFactory.getLogger(UserWorker.class);

    public UserWorker() throws SQLException, ClassNotFoundException {
    }

    /**
     * Create new user in database
     *
     * @param userPOJO
     * @return return int if row was added in database
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public int createUser(UserPOJO userPOJO) throws SQLException, IOException, ClassNotFoundException {
        StringBuilder stringBuilder = new StringBuilder()
                .append("INSERT INTO public.users(user_name, user_password, user_email) ")
                .append("VALUES (?, ?, ?)");
        DatabaseHelper databaseHelper = new DatabaseHelper();
        preparedStatement = databaseHelper.getConnectionDB().prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, userPOJO.getUserName());
        preparedStatement.setString(2, hashText(userPOJO.getUserPassword()));
        preparedStatement.setString(3, userPOJO.getUserEmail());
        int i = preparedStatement.executeUpdate();
        databaseHelper.close();
        logger.info("User was created!");
        return i;
    }

    /**
     * Method update user fields
     *
     * @param userPOJO
     * @return return true if user was updated
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public boolean updateUserProfile(UserPOJO userPOJO, String email) throws SQLException, ClassNotFoundException, IOException {
        boolean status = false;
        DatabaseHelper databaseHelper = new DatabaseHelper();
        StringBuilder stringBuilder = new StringBuilder()
                .append("UPDATE users SET user_name=?, user_password = ?, user_email=? WHERE user_email = ?;");
        PreparedStatement preparedStatement = databaseHelper.getConnectionDB().prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, userPOJO.getUserName());
        preparedStatement.setString(2, hashText(userPOJO.getUserPassword()));
        preparedStatement.setString(3, userPOJO.getUserEmail());
        preparedStatement.setString(4, email);
        if (preparedStatement.executeUpdate() > 0) {
            status = true;
        }
        preparedStatement.close();
        databaseHelper.close();
        logger.info("User was updated!");
        return status;
    }

    /**
     * Method check if user exist by user email
     *
     * @param userPOJO
     * @return true if user exist
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public boolean checkIfUserExists(UserPOJO userPOJO) throws SQLException, ClassNotFoundException, IOException {
        Boolean status = false;
        StringBuilder stringBuilder = new StringBuilder()
                .append("SELECT EXISTS (SELECT 1 FROM users ")
                .append("WHERE user_email='")
                .append(userPOJO.getUserEmail())
                .append("') AS \"exists\"");
        DatabaseHelper databaseHelper = new DatabaseHelper();
        statement = databaseHelper.getConnectionDB().createStatement();
        ResultSet resultSet = statement.executeQuery(stringBuilder.toString());
        while (resultSet.next()) {
            status = resultSet.getBoolean(1);
        }
        resultSet.close();
        databaseHelper.close();
        return status;
    }

    /**
     * Method check if user password and email are correct
     *
     * @param userPOJO
     * @return Return true if user email exists and password are correct
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean checkIfCredentialsCorrect(UserPOJO userPOJO) throws SQLException, IOException, ClassNotFoundException {
        Boolean status = false;
        DatabaseHelper databaseHelper = new DatabaseHelper();
        PreparedStatement preparedStatement = null;
        if (checkIfUserExists(userPOJO)) {
            try {
                StringBuilder stringBuilder = new StringBuilder()
                        .append("SELECT u.user_password FROM users u ")
                        .append("WHERE u.user_email =?;");
                preparedStatement = databaseHelper.getConnectionDB().prepareStatement(stringBuilder.toString());
                preparedStatement.setString(1, userPOJO.getUserEmail());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (hashText(userPOJO.getUserPassword()).equals(resultSet.getString("user_password"))) {
                        status = true;
                        logger.info("Requested users  mail and password are correct!");
                    }
                }
            } catch (SQLException e) {
                logger.trace("SQl exception ", e.toString());
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    databaseHelper.close();
                    logger.info("Resources are closed");
                }
            }
        }
        logger.info("Requested user does not exists!");
        return status;
    }

    /**
     * Method delete user by email
     *
     * @param userPOJO
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void deleteUser(UserPOJO userPOJO) throws SQLException, ClassNotFoundException, IOException {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        StringBuilder stringBuilder = new StringBuilder()
                .append("DELETE from users where user_email = ?;");
        preparedStatement = databaseHelper.getConnectionDB().prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, userPOJO.getUserEmail());
        preparedStatement.execute();
        preparedStatement.close();
        databaseHelper.close();
        logger.info("User was deleted from db!");
    }

    /**
     * Get user by email
     * @param email
     * @return new user
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public UserPOJO getUser(String email) throws SQLException, ClassNotFoundException, IOException {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        UserPOJO newUser = new UserPOJO();
        StringBuilder stringBuilder = new StringBuilder()
                .append("SELECT * FROM users u ")
                .append("WHERE u.user_email =?;");
        preparedStatement = databaseHelper.getConnectionDB().prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            newUser.setUserName(resultSet.getString("user_name"));
            newUser.setUserPassword(resultSet.getString("user_password"));
            newUser.setUserEmail(resultSet.getString("user_email"));
        }
        preparedStatement.close();
        databaseHelper.close();
        logger.info("user was updated from database");
        return newUser;
    }

}
