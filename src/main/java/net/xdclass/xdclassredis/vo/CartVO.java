package net.xdclass.xdclassredis.vo;


import java.util.List;

public class CartVO {

    private List<CartItemVO> cartItems;

    /**
     * total price
     */
    private Integer totalAmount;

    /**
     * total price
     * @return */
    public int getTotalAmount() {
        return cartItems.stream().mapToInt(CartItemVO::getTotalPrice).sum();
    }
    public List<CartItemVO> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }
}