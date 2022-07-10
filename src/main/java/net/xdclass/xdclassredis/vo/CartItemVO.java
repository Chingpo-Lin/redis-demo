package net.xdclass.xdclassredis.vo;

public class CartItemVO {

    private Integer productId;

    private Integer buyNum;

    private String productTitle;

    private String productImg;

    private int price ;

    private int totalPrice;

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public Integer getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getProductTitle() {
        return productTitle;
    }
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    public String getProductImg() {
        return productImg;
    }
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * total price
     * @return
     */
    public int getTotalPrice() {
        return this.price * this.buyNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}