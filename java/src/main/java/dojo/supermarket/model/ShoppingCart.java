package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int x = 1;
                if (offer.getOfferType() == SpecialOfferType.ThreeForTwo) {
                    x = 3;

                } else if (offer.getOfferType() == SpecialOfferType.TwoForAmount) {
                    x = 2;
                    if (quantityAsInt >= 2) {
                        int intDivision = quantityAsInt / x;
                        double pricePerUnit = offer.getArgument() * intDivision;
                        double theTotal = (quantityAsInt % 2) * unitPrice;
                        double total = pricePerUnit + theTotal;
                        double discountN = unitPrice * quantity - total;
                        discount = new Discount(p, "2 for " + offer.getArgument(), -discountN);
                    }

                } if (offer.getOfferType() == SpecialOfferType.FiveForAmount) {
                    x = 5;
                }
                int numberOfXs = quantityAsInt / x;
                if (offer.getOfferType() == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(p, "3 for 2", -discountAmount);
                }
                if (offer.getOfferType() == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(p, offer.getArgument() + "% off", -quantity * unitPrice * offer.getArgument() / 100.0);
                }
                if (offer.getOfferType() == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    double discountTotal = unitPrice * quantity - (offer.getArgument() * numberOfXs + quantityAsInt % 5 * unitPrice);
                    discount = new Discount(p, x + " for " + offer.getArgument(), -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
