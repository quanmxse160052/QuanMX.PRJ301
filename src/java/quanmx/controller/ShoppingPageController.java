package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
import quanmx.product.ProductDAO;
import quanmx.product.ProductDTO;
import quanmx.utils.MyApplicationConstants;

/**
 *
 * @author Dell
 */
public class ShoppingPageController extends HttpServlet {

//    private String SHOPPING_PAGE = "shoppingJspPage";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = siteMaps.getProperty(SHOPPING_PAGE);
        String url = siteMaps.getProperty(MyApplicationConstants.ShoppingPageFeatures.SHOPPING_PAGE);

        try {
            //1. call DAO
            ProductDAO dao = new ProductDAO();
            dao.loadAllProduct();
            List<ProductDTO> products = dao.getFullProductList();
            request.setAttribute("PRODUCTS", products);
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log("ShoppingPageController " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
            log("ShoppingPageController " + ex.getMessage());

        } catch (NamingException ex) {
//            ex.printStackTrace();
            log("ShoppingPageController " + ex.getMessage());
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
