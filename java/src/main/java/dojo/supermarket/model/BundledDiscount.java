package dojo.supermarket.model;

import java.util.List;

public class BundledDiscount {
    private final String description;
    private final List<Product> products;
    private final double discountAmount;

    public BundledDiscount(List<Product> products, String description, double discountAmount) {
        this.products = products;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
