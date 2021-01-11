package dojo.supermarket.model;

import java.util.List;

public class BundledOffer {
    private final List<Product> products;
    private final List<Double> unitPrices;

    public BundledOffer(List<Product> products, List<Double> unitPrices) {
        this.products = products;
        this.unitPrices = unitPrices;
    }

    public double getTotalPrice() {
        double price = 0;
        for (Double unitPrice : unitPrices) {
            price += unitPrice;
        }
        return price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Double> getUnitPrices() {
        return unitPrices;
    }
}
