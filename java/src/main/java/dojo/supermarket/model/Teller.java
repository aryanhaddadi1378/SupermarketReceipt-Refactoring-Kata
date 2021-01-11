package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private final Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart cart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = cart.getItems();

        for (ProductQuantity productQuantity: productQuantities) {
            Product product = productQuantity.getProduct();
            double unitPrice = this.catalog.getUnitPrice(product);
            receipt.addProduct(productQuantity, unitPrice);
        }
        cart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
