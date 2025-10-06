package com.cafepos.demo;

// cafepos
import com.cafepos.domain.SimpleProduct;
import com.cafepos.domain.Product;
import com.cafepos.decorator.ProductDecorator;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.common.Money;

public final class Week5Demo {

    public static void main(String[] args) {

        System.out.println("Demo of stacked products");

        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        ProductDecorator decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));
        System.out.println("Expected: decorated.name() => Espresso + Extra Shot + Oat Milk (Large)");
        System.out.println("  Actual: decorated.name() => " + decorated.name());
        System.out.println("Expected: decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 = 4.5");
        System.out.println("  Actual: decorated.price() => " + decorated.price());
    }
}
