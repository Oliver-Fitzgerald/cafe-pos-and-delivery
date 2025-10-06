package com.cafepos.factory;

//cafepos
import com.cafepos.catalog.*;
import com.cafepos.common.Money;
import com.cafepos.decorator.*;
import com.cafepos.domain.*;

public final class ProductFactory {

    /**
     * create
     * Creates a product from a recipe
     * @param recipe - The recipe the product will be created from
     * @return The final product
     * @throws IllegalArgumentException If the recipe is in an invalid format or contains unkown products
     */
    public Product create(String recipe) {

        if (recipe == null || recipe.isBlank())
            throw new IllegalArgumentException("recipe required");

        // Format recipe
        String[] raw = recipe.split("\\+");
        String[] addOns = java.util.Arrays.stream(raw)
                                         .skip(1)
                                         .map(String::trim)
                                         .map(String::toUpperCase)
                                         .toArray(String[]::new);

        // Get base product
        Product product = switch (raw[0].trim().toUpperCase()) {
            case "ESP" -> new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
            case "LAT" -> new SimpleProduct("P-LAT", "Latte", Money.of(3.20));
            case "CAP" -> new SimpleProduct("P-CAP", "Cappuccino", Money.of(3.00));
            default -> throw new IllegalArgumentException("Unknown base: " + addOns[0]);
        };

        // Add extras
        for (String addOn: addOns) {

            product = switch (addOn) {
                case "SHOT" -> new ExtraShot(product);
                case "OAT" -> new OatMilk(product);
                case "SYP" -> new Syrup(product);
                case "L" -> new SizeLarge(product);
                default -> throw new IllegalArgumentException("Unknown addon: " + addOn);
            };
        }
        return product;
    }
}
