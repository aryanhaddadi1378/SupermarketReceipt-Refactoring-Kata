package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public Double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : this.items) {
            total += item.getTotalPrice();
        }
        for (Discount discount : this.discounts) {
            total += discount.getDiscountAmount();
        }
        return total;
    }

    public void addItem(ProductQuantity productQuantity, double unitPrice) {
        double quantity = productQuantity.getQuantity();
        Product product = productQuantity.getProduct();
        this.items.add(new ReceiptItem(product, quantity, unitPrice));
    }

    public List<ReceiptItem> getItems() {
        return new ArrayList<>(this.items);
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
