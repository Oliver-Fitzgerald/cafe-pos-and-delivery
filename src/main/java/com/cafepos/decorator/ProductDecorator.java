package com.cafepos.decorator;

// cafepos
import com.cafepos.domain.Product;
import com.cafepos.common.Money;

public abstract class ProductDecorator implements Product {

    protected final Product base;

    /**
     * ProductDecorator
     * Constructs the base product
     * @param Product - The base Product
     * @throws IllegalArgumentException If base is null
     */
    protected ProductDecorator(Product base) {

        if (base == null)
            throw new IllegalArgumentException("base product required");
        this.base = base;
    }

    /**
     * id
     * @return the products id
     */
    @Override
    public String id() {
        return base.id();
    } 

    /**
     * basePrice
     * @return the products base price
     */
    @Override
    public Money basePrice() {
        return base.basePrice();
    } 

    public abstract Money price();
    // Concrete decorators will override name() and provide a finalPrice() helper if desired.
}
