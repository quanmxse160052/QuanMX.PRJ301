package quanmx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quanmx.registration.RegistrationCreateError;
import quanmx.registration.RegistrationDAO;
import quanmx.registration.RegistrationDTO;

/**
 *
 * @author Dell
 */
public class CreateNewAccountController extends HttpServlet {

    private final String LOGIN_PAGE = "";
    private final String ERROR_PAGE = "createAccountJspPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirmPassword = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullName");
        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = siteMaps.getProperty(ERROR_PAGE);
        try {
            //1. check all user's constraints
            if (username.trim().length() < 6 || username.trim().length() > 12) {
                errors.setUsernameLengthErr("Username required 6-12 characters");
                foundErr = true;
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                errors.setPasswordLengthErr("Password required 6-30 characters");
                foundErr = true;
            } else if (!password.trim().equals(confirmPassword)) {
                errors.setConfirmNotMatchErr("Password confirm must match password");
                foundErr = true;
            }
            if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
                errors.setFullNameLengthError("Full name required 2-50 characters");
                foundErr = true;
            }
            //2. check if the error occurs, set store errors and then forward to error page
            if (foundErr == true) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {
                //3. call DAO
                RegistrationDTO dto = new RegistrationDTO(username, password, fullName, false);
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createNewAccount(dto);
                if (result) {
                    url = siteMaps.getProperty(LOGIN_PAGE);
                }
            }

        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " existed!");
                request.setAttribute("CREATE_ERROR", errors);
            }
            log("CreateAccountController _SQL " + msg);
        } catch (ClassNotFoundException ex) {
            log("CreateAccountController _SQL " + ex.getMessage());

        } catch (NamingException ex) {
            log("CreateAccountController _SQL " + ex.getMessage());

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
