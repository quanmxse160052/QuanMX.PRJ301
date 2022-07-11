package quanmx.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import quanmx.cart.CartProduct;
import quanmx.utils.DBHelper;

/**
 *
 * @author Dell
 */
public class OrderDetailDAO implements Serializable{

    public int insertIntoOrderDetail(List<CartProduct> products, int orderPK) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        int insertedRow = 0;
        try {
            //1. make connection
            connection = DBHelper.makeConnection();
            //2. query string
            String sql = "Insert into OrderDetail(sku, orderId, quantity, price, total) "
                    + "Values(?,?,?,?,?)";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            double total = 0;
            int result;
            for (CartProduct product : products) {
                total = product.getProduct().getPrice() * product.getQuantity();
                stm.setInt(1, product.getProduct().getSku());
                stm.setInt(2, orderPK);
                stm.setInt(3, product.getQuantity());
                stm.setDouble(4, product.getProduct().getPrice());
                stm.setDouble(5, total);
                //4. execute
                result = stm.executeUpdate();
                if (result > 0) {
                    insertedRow += result;
                }
            }
            //5. process result
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return insertedRow;
    }
}
