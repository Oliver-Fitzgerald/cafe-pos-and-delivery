package com.cafepos.common;

import java.util.Objects;
import java.math.MathContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money implements Comparable<Money> {

    private final BigDecimal amount;
    private static final MathContext context = new MathContext(1000000,RoundingMode.HALF_UP); //Max Digits is 1 million e.g 10,000.01 && Rounding is to nearest neighbour e.g 0.5 to 1 or 2.1 to 2

    /**
     * of
     * Constructs a Money object ensuring that the value is a positive number
     * @param value {double} - The amount this Money object will represent
     * @return money {Money} - The constructed Money object
     */
    public static Money of(double value) throws IllegalArgumentException { 

        sanatize(value);
        return new Money(new BigDecimal(Double.toString(value), context));
    }
    /** zero
     * Constructs a Money object that represents the amount 0
     */
    public static Money zero() { 

        return new Money(new BigDecimal(0, context));
    }
    /** Money
     * Constructs a Money object by setting the amount to a passed BigDecimal scaling it to 2 decimal places
     * @param amount The BigDecimal object this money represents
     */
    private Money(BigDecimal amount) {

        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return this.amount;
    }


    /**
     * add
     * Sums this Money object's amount with that of the passed Money object and returns a new Money object.
     * Note: It leaves the original Money objects unchanged
     * @param qty the integer quantity to multiply this Money amount by
     * @return a new Money object containing the result of the multiplication
     * @throws IllegalArgumentException if the quantity passed is negative
     */
    public Money add(Money money) throws IllegalArgumentException { 
        return of(this.amount.doubleValue() + money.getAmount().doubleValue());
    }

    /**
     * subtract
     * Subtracts the passed Money object's amount from this Money object and returns the new Money object.
     * Note: It leaves the original Money objects unchanged
     * @param money The money to be subtracted from this Money
     * @return a new Money object containing the result of the subtraction
     * @throws IllegalArgumentException if the result is negative
     */
    public Money subtract(Money money) throws IllegalArgumentException { 
        return of(this.amount.doubleValue() - money.getAmount().doubleValue());
    }

    /**
     * multiply
     * Multiplies this Money object's amount by the specified quantity and returns a new Money object.
     * Note: It leaves the original Money objects unchanged
     * @param qty the integer quantity to multiply this Money amount by
     * @return a new Money object containing the result of the multiplication
     * @throws IllegalArgumentException if the quantity passed is negative
     */
    public Money multiply(double qty) throws IllegalArgumentException { 
        return of(this.amount.multiply(BigDecimal.valueOf(qty)).doubleValue());
    }

    /**
     * divide
     * Divides this Money object's amount by the passed Money object  and returns a new Money object.
     * Note: It leaves the original Money objects unchanged
     * @param divisor the Money object this this money object will be divided by
     * @return a new Money object containing the result of the division
     * @throws IllegalArgumentException if the passed divisor is less than or equal to zero or if result is less than 0
     */
    public Money divide(Money divisor) throws IllegalArgumentException { 
        if (divisor.compareTo(Money.of(0)) == 0)
            throw new IllegalArgumentException("Divisor must be greater than 0");

        return of(this.amount.divide(divisor.getAmount()).doubleValue());
    }

    /**
     * divide
     * Divides this Money object's amount by the passed Money object  and returns a new Money object.
     * Note: It leaves the original Money objects unchanged
     * @param divisor the Money object this this money object will be divided by
     * @return a new Money object containing the result of the division
     * @throws IllegalArgumentException if the passed divisor is less than or equal to zero or if result is less than 0
     */
    public Money divide(double divisor) throws IllegalArgumentException { 
        if (divisor <= 0)
            throw new IllegalArgumentException("Divisor must be greater than 0");

        return of(this.amount.divide(BigDecimal.valueOf(divisor)).doubleValue());
    }

    /**
     * sanatize
     * Ensures that the passed number is a valid amount for the Money object i.e not null and greater than zero
     */
    private static void sanatize(double number) throws IllegalArgumentException {

        if ( number < 0 )
            throw new IllegalArgumentException("A money object must represent a positive number, received: " + number);
    }

    @Override
    public String toString() {
        return amount.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || getClass() != obj.getClass()) return false;
        if (this == obj) return true;

        Money money = (Money) obj;
        return money.getAmount().doubleValue() == this.amount.doubleValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amount);
    }

     @Override
    public int compareTo(Money other) throws IllegalArgumentException {
        if (other == null)
            throw new IllegalArgumentException("Cannot compare to null Money");

        return this.amount.compareTo(other.getAmount());
    }
}
