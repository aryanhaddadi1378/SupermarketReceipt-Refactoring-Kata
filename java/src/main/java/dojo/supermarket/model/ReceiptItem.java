package dojo.supermarket.model;

import java.util.Objects;

public class ReceiptItem {
    private final Product product;
    private final double unitPrice;
//    private final double totalPrice;
    private final double quantity;

    public ReceiptItem(Product p, double quantity, double unitPrice) {
        this.product = p;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return Double.compare(that.unitPrice, unitPrice) == 0 &&
                Double.compare(that.getTotalPrice(), getTotalPrice()) == 0 &&
                Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, unitPrice, getTotalPrice(), quantity);
    }


}
