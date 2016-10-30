package models;

/**
 * Created by Andrey on 28.10.2016.
 */
public class UserPOJO {
    String userName;
    String userPassword;
    String userEmail;

    /**
     * Get user email
     * @return user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Set user email
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Return user name
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get user password
     * @return user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Set user password
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
