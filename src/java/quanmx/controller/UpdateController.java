package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quanmx.registration.RegistrationDAO;
import quanmx.utils.MyApplicationConstants;

/**
 *
 * @author Dell
 */
public class UpdateController extends HttpServlet {

//    private final String ERROR_PAGE = "errorPage";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("txtUsername");
        String newPassword = request.getParameter("txtPassword");
        String newRole = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = siteMaps.getProperty(ERROR_PAGE);
        String url = MyApplicationConstants.UpdateAccountFeatures.ERROR_PAGE;

        try {

            //1. call DAO
            RegistrationDAO dao = new RegistrationDAO();
            boolean result = dao.updateUserInfor(username, newPassword, newRole);
            if (result) {
                url = MyApplicationConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER + "?btAction=Search"
                        + "&txtSearchValue="
                        + searchValue;
            }
        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
            log("UpdateController " + ex.getMessage());
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log("UpdateController " + ex.getMessage());

        } catch (NamingException ex) {
//            ex.printStackTrace();
            log("UpdateController " + ex.getMessage());

        } finally {
            response.sendRedirect(url);
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
