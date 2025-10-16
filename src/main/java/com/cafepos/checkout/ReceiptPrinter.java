package com.cafepos.checkout;

public final class ReceiptPrinter {

    public String format(String recipe, int qty, PricingService.PricingResult pricingResult) {

        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(pricingResult.subtotal()).append("\n");

        if (pricingResult.discount().getAmount().signum() > 0)
            receipt.append("Discount: - ").append(pricingResult.discount()).append("\n");

        receipt.append("Tax (").append(pricingResult.taxPercent()).append("%): ").append(pricingResult.tax()).append("\n");
        receipt.append("Total: ").append(pricingResult.total());

        return receipt.toString();
    }
}
