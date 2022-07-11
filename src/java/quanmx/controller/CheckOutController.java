package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import quanmx.cart.CartObject;
import quanmx.cart.CartProduct;
import quanmx.order.OrderDAO;
import quanmx.orderdetail.OrderDetailDAO;
import quanmx.product.ProductDAO;

/**
 *
 * @author Dell
 */
public class CheckOutController extends HttpServlet {

    private String SHOPPING_PAGE = "shoppingPage";
    private String VIEW_CART_PAGE = "viewCartPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");

        String url = siteMaps.getProperty(VIEW_CART_PAGE);
        try {
            //1. get session
            HttpSession session = request.getSession(false);
            //2. check session has existed
            if (session != null) {
                // 3. get cart in session
                CartObject cart = (CartObject) session.getAttribute("CART");
                //4. check cart has existed
                if (cart != null) {
                    //5. check items in cart has existed
                    Map<Integer, Integer> items = cart.getItems();
                    //6. check items has existed
                    if (items != null) {
                        //7. get product list
                        List<CartProduct> productList = new ArrayList<>();
                        ProductDAO proDao = new ProductDAO();
                        for (int sku : items.keySet()) {
                            CartProduct product = new CartProduct(proDao.getProductBySku(sku), items.get(sku));
                            productList.add(product);
                        }
                        //8. call DAO: insert order to Order
                        OrderDAO dao = new OrderDAO();
                        int lastId = dao.insertNewOrder(productList);
                        //9. check last identity of above action
                        if (lastId > 0) {
                            //10. call DAO: insert products to OrderDetail
                            OrderDetailDAO orderDetailDao = new OrderDetailDAO();
                            int insertedRow = orderDetailDao.insertIntoOrderDetail(productList, lastId);
                            if (insertedRow == productList.size()) {
                                session.removeAttribute("CART");
                                url = siteMaps.getProperty(SHOPPING_PAGE);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (NamingException ex) {
            ex.printStackTrace();

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
