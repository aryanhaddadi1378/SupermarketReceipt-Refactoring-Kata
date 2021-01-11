package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();


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

    private Discount getDiscountThreeForTwo(Product product, double quantity, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 3;
        int numOfBundles = quantityAsInt / bundleSize;
        if (quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numOfBundles * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            return new Discount(product, "3 for 2", -discountAmount);
        }
        return null;
    }


    private Discount getDiscountTwoForAmount(Product product, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 2;
        if (quantityAsInt >= 2) {
            int intDivision = quantityAsInt / bundleSize;
            double pricePerUnit = offer.getUnitPrice() * intDivision;
            double theTotal = (quantityAsInt % 2) * unitPrice;
            double total = pricePerUnit + theTotal;
            double discountN = unitPrice * quantity - total;
            return new Discount(product, "2 for " + offer.getUnitPrice(), -discountN);
        }
        return null;
    }

    private Discount getDiscountFiveForAmount(Product product, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 5;
        int numOfBundles = quantityAsInt / bundleSize;
        if (quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (offer.getUnitPrice() * numOfBundles + quantityAsInt % 5 * unitPrice);
            return new Discount(product, bundleSize + " for " + offer.getUnitPrice(), -discountTotal);
        }
        return null;
    }

    private Discount getDiscountTenPercentDiscount(Product product, double quantity, Offer offer, double unitPrice) {
        return new Discount(product, offer.getUnitPrice() + "% off", -quantity * unitPrice * offer.getUnitPrice() / 100.0);
    }

    private Discount handleOffer(Offer offer, SupermarketCatalog catalog, Product product) {
        double quantity = productQuantities.get(product);
        double unitPrice = catalog.getUnitPrice(product);
        switch (offer.getOfferType()) {
            case ThreeForTwo:
                return getDiscountThreeForTwo(product, quantity, unitPrice);
            case TwoForAmount:
                return getDiscountTwoForAmount(product, quantity, offer, unitPrice);
            case FiveForAmount:
                return getDiscountFiveForAmount(product, quantity, offer, unitPrice);
            case TenPercentDiscount:
                return getDiscountTenPercentDiscount(product, quantity, offer, unitPrice);
            default:
                return null;
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities.keySet()) {
            if (!offers.containsKey(product))
                continue;

            Discount discount = handleOffer(offers.get(product), catalog, product);

            if (discount != null)
                receipt.addDiscount(discount);
        }
    }
}
