package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao {
    private DataSource dataSource;
    private ProductDao productDao;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        this.dataSource = dataSource;
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId){

        ShoppingCart cart = new ShoppingCart();

        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");

                Product product = productDao.getById(productId);

                //create Shopping cart item
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);

                //add to cart
                cart.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    @Override
    public void addCartItem(int userId, int productId, int quantity) {
        String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) VALUES (?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement pS = connection.prepareStatement(sql)){
            pS.setInt(1, userId);
            pS.setInt(2, productId);
            pS.setInt(3, quantity);
            pS.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCartItem(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pS = connection.prepareStatement(sql)) {
            pS.setInt(1, quantity);
            pS.setInt(2, userId);
            pS.setInt(3, productId);

            pS.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pS = connection.prepareStatement(sql))
        {
            pS.setInt(1, userId);
            pS.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
