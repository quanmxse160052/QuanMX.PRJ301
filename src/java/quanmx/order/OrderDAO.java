package quanmx.order;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.naming.NamingException;
import quanmx.cart.CartProduct;
import quanmx.utils.DBHelper;

public class OrderDAO implements Serializable {

    public int insertNewOrder(List<CartProduct> products) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        CallableStatement cStm = null;
        ResultSet rs = null;

        int lastId = -1;
        try {
            //1. make connection
            connection = DBHelper.makeConnection();
            //calculate total price
            double total = 0;
            for (CartProduct product : products) {
                total += product.getProduct().getPrice() * product.getQuantity();
            }

            //2. query string
            String sql = "Insert into [Order](date, total)"
                    + "Values(?, ?)";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            Date date = new Date(System.currentTimeMillis());
            stm.setTimestamp(1, new Timestamp(date.getTime()));

            stm.setDouble(2, total);
            //4. execute query
            int rowAffected = stm.executeUpdate();
            //5. process result
            if (rowAffected > 0) {
                sql = "Select IDENT_CURRENT(?)";
                cStm = connection.prepareCall(sql);
                cStm.setString(1, "Order");
                rs = cStm.executeQuery();
                if (rs.next()) {
                    lastId = rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cStm != null) {
                cStm.close();
            }
            if (stm != null) {
                stm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return lastId;
    }
}
