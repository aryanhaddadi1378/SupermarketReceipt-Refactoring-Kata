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

    private SingleDiscount getDiscountThreeForTwo(Product product, double quantity, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 3;
        int numOfBundles = quantityAsInt / bundleSize;
        if (quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numOfBundles * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            return new SingleDiscount(product, "3 for 2", -discountAmount);
        }
        return null;
    }


    private SingleDiscount getDiscountTwoForAmount(Product product, double quantity, SingleOffer singleOffer, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 2;
        if (quantityAsInt >= 2) {
            int intDivision = quantityAsInt / bundleSize;
            double pricePerUnit = singleOffer.getArgument() * intDivision;
            double theTotal = (quantityAsInt % 2) * unitPrice;
            double total = pricePerUnit + theTotal;
            double discountN = unitPrice * quantity - total;
            return new SingleDiscount(product, "2 for " + singleOffer.getArgument(), -discountN);
        }
        return null;
    }

    private SingleDiscount getDiscountFiveForAmount(Product product, double quantity, SingleOffer singleOffer, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int bundleSize = 5;
        int numOfBundles = quantityAsInt / bundleSize;
        if (quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (singleOffer.getArgument() * numOfBundles + quantityAsInt % 5 * unitPrice);
            return new SingleDiscount(product, bundleSize + " for " + singleOffer.getArgument(), -discountTotal);
        }
        return null;
    }

    private SingleDiscount getDiscountTenPercentDiscount(Product product, double quantity, SingleOffer singleOffer, double unitPrice) {
        return new SingleDiscount(product, singleOffer.getArgument() + "% off", -quantity * unitPrice * singleOffer.getArgument() / 100.0);
    }

    private SingleDiscount handleSingleOffer(SingleOffer singleOffer, SupermarketCatalog catalog, Product product) {
        double quantity = productQuantities.get(product);
        double unitPrice = catalog.getUnitPrice(product);
        switch (singleOffer.getOfferType()) {
            case ThreeForTwo:
                return getDiscountThreeForTwo(product, quantity, unitPrice);
            case TwoForAmount:
                return getDiscountTwoForAmount(product, quantity, singleOffer, unitPrice);
            case FiveForAmount:
                return getDiscountFiveForAmount(product, quantity, singleOffer, unitPrice);
            case TenPercentDiscount:
                return getDiscountTenPercentDiscount(product, quantity, singleOffer, unitPrice);
            default:
                return null;
        }
    }

    private BundledDiscount handleBundledOffers(Map<List<Product>, BundledOffer> bundledOffers) {
        for (List<Product> bundledOfferProducts : bundledOffers.keySet()) {
            boolean contains = true;
            for(Product product : bundledOfferProducts) {
                if (!productQuantities.containsKey(product)) {
                    contains = false;
                    break;
                }
            }
            if (!contains)
                continue;
            return new BundledDiscount(bundledOfferProducts, "Discounted Bundle",
                                        -bundledOffers.get(bundledOfferProducts).getTotalPrice() * 0.1);
        }
        return null;
    }

    public void handleOffers(Receipt receipt, Map<Product, SingleOffer> offers,
                      Map<List<Product>, BundledOffer> bundledOffers, SupermarketCatalog catalog) {

        for (Product product: productQuantities.keySet()) {
            if (!offers.containsKey(product))
                continue;

            SingleDiscount singleDiscount = handleSingleOffer(offers.get(product), catalog, product);

            if (singleDiscount != null)
                receipt.addSingleDiscount(singleDiscount);
        }

        BundledDiscount bundledDiscount = handleBundledOffers(bundledOffers);
        receipt.addBundledDiscount(bundledDiscount);
    }
}
