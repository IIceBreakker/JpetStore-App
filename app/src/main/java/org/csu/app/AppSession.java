package org.csu.app;

import android.app.Application;
import org.csu.app.domain.Cart;
import org.csu.app.domain.User;


public class AppSession extends Application {

    private User user;
    private Cart cart;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
