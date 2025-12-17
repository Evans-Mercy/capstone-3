package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao {
    private DataSource dataSource;

    public MySqlShoppingCartDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ShoppingCart getByUserId(int userId){
        return new ShoppingCart();
    }

    @Override
    public void addCartItem(int userId, int productId, int quantity) {
        String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) VALUES (?, ?, ?)";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(1, productId);
            statement.setInt(1, quantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
