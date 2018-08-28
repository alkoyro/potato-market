package com.epam.libg.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * POJO represents Potato Bag entity
 */
public class PotatoBag {

    private String id;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Short potatosNumber;

    @NotNull
    private BagSupplier bagSupplier;

    @NotNull
    private Date packedDate;

    @NotNull
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getPotatosNumber() {
        return potatosNumber;
    }

    public void setPotatosNumber(Short potatosNumber) {
        this.potatosNumber = potatosNumber;
    }

    public BagSupplier getBagSupplier() {
        return bagSupplier;
    }

    public void setBagSupplier(BagSupplier bagSupplier) {
        this.bagSupplier = bagSupplier;
    }

    public Date getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(Date packedDate) {
        this.packedDate = packedDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PotatoBag potatoBag = (PotatoBag) o;
        return Objects.equals(id, potatoBag.id) &&
                Objects.equals(potatosNumber, potatoBag.potatosNumber) &&
                Objects.equals(bagSupplier, potatoBag.bagSupplier) &&
                Objects.equals(packedDate, potatoBag.packedDate) &&
                Objects.equals(price, potatoBag.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, potatosNumber, bagSupplier, packedDate, price);
    }

    @Override
    public String toString() {
        return "PotatoBag{" + "id='" + id + '\'' +
                ", potatosNumber=" + potatosNumber +
                ", bagSupplier=" + bagSupplier +
                ", packedDate=" + packedDate +
                ", price=" + price + '}';
    }
}
