package com.cafepos.application;

// CafePOS
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.checkout.PricingService.PricingResult;
// Java
import java.util.List;

/*
 * ReceiptFormatter
 */
public final class ReceiptFormatter {

    public String format(long id, List<LineItem> items, PricingResult pr, int taxPercent) {

        StringBuilder sb = new StringBuilder();

        sb.append("Order #").append(id).append("\n");

        for (LineItem li : items) {
            sb.append(" - ").append(li.product().name()).append(" x").append(li.quantity()).append(" = ").append(li.lineTotal()).append("\n");
        }

        sb.append("Subtotal: ").append(pr.subtotal()).append("\n");

        if (pr.discount().getAmount().signum() > 0)
            sb.append("Discount: -").append(pr.discount()).append("\n");

        sb.append("Tax (").append(taxPercent).append("%): ").append(pr.tax()).append("\n");
        sb.append("Total: ").append(pr.total());
        return sb.toString();
    }
}
