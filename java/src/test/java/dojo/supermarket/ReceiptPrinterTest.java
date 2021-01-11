package dojo.supermarket;

import dojo.supermarket.model.*;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class ReceiptPrinterTest {

    Product toothbrush = new Product("toothbrush", ProductUnit.Each);
    Product apples = new Product("apples", ProductUnit.Kilo);
    Receipt receipt = new Receipt();

    @Test
    public void oneLineItem() {
        receipt.addItem(new ProductQuantity(toothbrush, 1), 0.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void quantityTwo() {
        receipt.addItem(new ProductQuantity(toothbrush, 2), 0.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void looseWeight() {
        receipt.addItem(new ProductQuantity(apples, 2.3), 1.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void total() {
        receipt.addItem(new ProductQuantity(toothbrush, 1), 0.99);
        receipt.addItem(new ProductQuantity(apples, 0.75), 1.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void discounts() {
        receipt.addSingleDiscount(new SingleDiscount(apples, "3 for 2", -0.99));
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void printWholeReceipt() {
        receipt.addItem(new ProductQuantity(toothbrush, 1), 0.99);
        receipt.addItem(new ProductQuantity(toothbrush, 2), 0.99);
        receipt.addItem(new ProductQuantity(apples, 0.75), 1.99);
        receipt.addSingleDiscount(new SingleDiscount(toothbrush, "3 for 2", -0.99));
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

}
