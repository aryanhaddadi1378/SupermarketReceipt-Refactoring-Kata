package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private final Map<Product, SingleOffer> offers = new HashMap<>();
    private final Map<List<Product>, BundledOffer> bundledOffers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double unitPrice) {
        this.offers.put(product, new SingleOffer(offerType, product, unitPrice));
    }

    public void addBundledSpecialOffer(List<Product> products, List<Double> unitPrices) {
        this.bundledOffers.put(products, new BundledOffer(products, unitPrices));
    }

    private void addItemToReceipt(Receipt receipt, ProductQuantity productQuantity) {
        Product product = productQuantity.getProduct();
        double unitPrice = this.catalog.getUnitPrice(product);
        receipt.addItem(productQuantity, unitPrice);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart cart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = cart.getItems();

        for (ProductQuantity productQuantity: productQuantities) {
            addItemToReceipt(receipt, productQuantity);
        }
        cart.handleOffers(receipt, this.offers, this.bundledOffers, this.catalog);

        return receipt;
    }

}
