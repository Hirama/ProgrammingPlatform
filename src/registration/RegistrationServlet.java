package registration;

import models.UserPOJO;
import models.UserWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrey on 30.10.2016.
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static UserPOJO userPOJO = new UserPOJO();
    private UserWorker userWorker = new UserWorker();
    private static Logger logger = LoggerFactory.getLogger(RegistrationServlet.class);

    public RegistrationServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * Check if entered email is valid
     * @param emailStr
     * @return true if email is valid
     */
    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Method register new user in db
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userPOJO.setUserName(req.getParameter("username"));
        userPOJO.setUserEmail(req.getParameter("email"));
        userPOJO.setUserPassword(req.getParameter("password"));
        if (userPOJO.getUserName().length() < 1) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.html");
            PrintWriter out = resp.getWriter();
            out.println("<font color = red>User Name is empty!</font>");
            rd.include(req, resp);
            logger.info("User name is empty!");
        } else if (userPOJO.getUserPassword().length() < 4) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.html");
            PrintWriter out = resp.getWriter();
            out.println("<font color = red>Password is too low</font>");
            rd.include(req, resp);
            logger.info("Password is too low");
        }
        if (validate(userPOJO.getUserEmail())) {
            try {
                if (userWorker.checkIfUserExists(userPOJO)) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.html");
                    PrintWriter out = resp.getWriter();
                    out.println("<font color=red>User with such email exists</font>");
                    rd.include(req, resp);
                    logger.info("User with such email exists");
                } else {
                    userWorker.createUser(userPOJO);
                    resp.sendRedirect("login.html");
                    logger.info("User was created successfully");
                }
            } catch (ClassNotFoundException | SQLException e) {
                logger.trace("Exception", e.toString());
            }
        }
    }
}
