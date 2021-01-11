package dojo.supermarket.model;

public class SingleDiscount {
    private final String description;
    private final double discountAmount;
    private final Product product;

    public SingleDiscount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

}
