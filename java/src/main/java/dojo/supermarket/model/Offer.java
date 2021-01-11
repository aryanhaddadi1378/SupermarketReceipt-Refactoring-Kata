package dojo.supermarket.model;

public class Offer {
    private final SpecialOfferType offerType;
    private final Product product;
    private final double unitPrice;

    public Offer(SpecialOfferType offerType, Product product, double unitPrice) {
        this.offerType = offerType;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public SpecialOfferType getOfferType() {
        return this.offerType;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

}
