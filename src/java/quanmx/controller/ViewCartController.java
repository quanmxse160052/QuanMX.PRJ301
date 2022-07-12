package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import javax.servlet.http.HttpSession;
import quanmx.cart.CartObject;
import quanmx.cart.CartProduct;
import quanmx.product.ProductDAO;
import quanmx.product.ProductDTO;
import quanmx.utils.MyApplicationConstants;

/**
 *
 * @author Dell
 */
public class ViewCartController extends HttpServlet {

//    private String CART_PAGE = "cartPage";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = siteMaps.getProperty(CART_PAGE);
        String url = siteMaps.getProperty(MyApplicationConstants.ViewCartFearures.CART_PAGE);

        try {
            HttpSession session = request.getSession(false);
            // 1. check session has existed
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                //2. check cart has existed
                if (cart != null) {
                    Map<Integer, Integer> products = cart.getItems();
                    //3. check empty cart
                    if (products != null) {
                        List<CartProduct> productList = new ArrayList<>();
                        ProductDAO dao = new ProductDAO();
                        //4. get all product infor
                        for (Integer sku : products.keySet()) {
                            CartProduct cartProduct = new CartProduct(dao.getProductBySku(sku), products.get(sku));
                            productList.add(cartProduct);
                        }
                        request.setAttribute("PRODUCTS", productList);

                    }
                }
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log("ViewCartController " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
            log("ViewCartController " + ex.getMessage());

        } catch (NamingException ex) {
//            ex.printStackTrace();
            log("ViewCartController " + ex.getMessage());

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
