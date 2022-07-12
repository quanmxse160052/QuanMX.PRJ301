package quanmx.controller;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//change to filter
//@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})

public class DispatchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "";
    private final String LOGIN_CONTROLLER = "loginAction";
    private final String SEARCH_LASTNAME_CONTROLLER = "searchAction";
    private final String DELETE_CONTROLLER = "deleteAction";
    private final String UPDATE_CONTROLLER = "updateAction";
    private final String START_APP_CONTROLLER = "startAppAction";
    private final String LOGOUT_CONTROLLER = "logoutAction";
    private final String ADD_TO_CART_CONTROLLER = "addToCartAction";
    private final String SHOPPING_PAGE_CONTROLLER = "shoppingPageAction";
    private final String VIEW_CART_PAGE_CONTROLLER = "viewCartAction";
    private final String REMOVE_PRODUCT_FROM_CART = "removeCartProduct";
    private final String CHECK_OUT_CONTROLLER = "checkOutAction";
    private final String CREATE_NEW_ACCOUNT_CONTROLLER = "createNewAccountAction";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        String url = LOGIN_PAGE;
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = siteMaps.getProperty(LOGIN_PAGE);
        String action = request.getParameter("btAction");

        try {

            if (action == null) {
                url = siteMaps.getProperty(START_APP_CONTROLLER);
            } else if (action.equals("Login")) {
                url = siteMaps.getProperty(LOGIN_CONTROLLER);
            } else if (action.equals("Search")) {
                url = siteMaps.getProperty(SEARCH_LASTNAME_CONTROLLER);
            } else if (action.equals("delete")) {
                url = siteMaps.getProperty(DELETE_CONTROLLER);
            } else if (action.equals("Update")) {
                url = siteMaps.getProperty(UPDATE_CONTROLLER);
            } else if (action.equals("Logout")) {
                url = siteMaps.getProperty(LOGOUT_CONTROLLER);
            } else if (action.equals("Add to cart")) {
                url = siteMaps.getProperty(ADD_TO_CART_CONTROLLER);
            } else if (action.equals("shopping")) {
                url = siteMaps.getProperty(SHOPPING_PAGE_CONTROLLER);
            } else if (action.equals("viewCart")) {
                url = siteMaps.getProperty(VIEW_CART_PAGE_CONTROLLER);
            } else if (action.equals("Remove product(s)")) {
                url = siteMaps.getProperty(REMOVE_PRODUCT_FROM_CART);
            } else if (action.equals("Check out")) {
                url = siteMaps.getProperty(CHECK_OUT_CONTROLLER);
            } else if (action.equals("Create New Account")) {
                url = siteMaps.getProperty(CREATE_NEW_ACCOUNT_CONTROLLER);
            }

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
