package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    void addCartItem(int userId, int productId, int quantity);

    void updateCartItem(int userId, int productId, int quantity);

    void clearCart(int userId);
}
