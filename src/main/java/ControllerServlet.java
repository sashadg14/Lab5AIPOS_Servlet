import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 *
 * @author www.codejava.net
 */
public class ControllerServlet extends HttpServlet {
    private SectionDAO sectionDAO;
    RequestOperations requestOperations;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
        sectionDAO = new SectionDAO(jdbcURL, jdbcUsername, jdbcPassword);
        requestOperations = new RequestOperations(sectionDAO);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/create":
                    requestOperations.insertSection(request, response);
                    break;
                case "/createMain":
                    requestOperations.insertMainSection(request, response);
                    break;
                case "/update":
                    requestOperations.updateSection(request, response);
                    break;
                case "/delete":
                    requestOperations.deleteSection(request, response);
                    break;
                case "/main_delete":
                    requestOperations.deleteMainSection(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/add":
                    requestOperations.showNewForm(request, response);
                    break;
                case "/addMain":
                    requestOperations.showNewMainForm(request, response);
                    break;
                case "/section":
                    requestOperations.getSectionInfo(request, response);
                    break;
                case "/mainSection":
                    requestOperations.listSection(request, response);
                    break;
                case "/":
                    requestOperations.showAuth(request, response);
                    break;
                case "/authVk":
                    requestOperations.vkAUTH(request, response);
                    break;
                case "/authGit":
                    requestOperations.gitAUTH(request, response);
                    break;
                case "/home":
                    requestOperations.showHomePage(request, response);
                    break;
                case "/all":
                    requestOperations.listMainSection(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}