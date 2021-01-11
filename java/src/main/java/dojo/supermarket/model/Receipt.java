package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<SingleDiscount> singleDiscounts = new ArrayList<>();
    private final List<BundledDiscount> bundledDiscounts = new ArrayList<>();


    public Double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : this.items) {
            total += item.getTotalPrice();
        }
        for (SingleDiscount singleDiscount : this.singleDiscounts) {
            total += singleDiscount.getDiscountAmount();
        }
        for (BundledDiscount bundledDiscount : this.bundledDiscounts) {
            total += bundledDiscount.getDiscountAmount();
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

    public void addSingleDiscount(SingleDiscount singleDiscount) {
        this.singleDiscounts.add(singleDiscount);
    }

    public void addBundledDiscount(BundledDiscount bundledDiscount) {
        this.bundledDiscounts.add(bundledDiscount);
    }

    public List<SingleDiscount> getSingleDiscounts() {
        return singleDiscounts;
    }
}
