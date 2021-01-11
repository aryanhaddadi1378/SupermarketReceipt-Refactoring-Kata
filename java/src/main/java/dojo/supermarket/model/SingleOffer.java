package dojo.supermarket.model;

public class SingleOffer {
    private final SpecialOfferType offerType;
    private final Product product;
    private final double argument;

    public SingleOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public SpecialOfferType getOfferType() {
        return this.offerType;
    }

    public double getArgument() {
        return this.argument;
    }

}
