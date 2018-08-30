package com.epam.libg.model;

import com.epam.libg.model.validator.constraint.BagSupplierConstraint;
import com.epam.libg.util.Constants;
import com.epam.libg.model.validator.constraint.DateConstraint;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.epam.libg.util.Constants.PRICE_FORMAT;

/**
 * pojo class for Rest interactions
 */
public class PotatoBagDTO {
    private String id;

    @ExpectedTypeFormat(value = "potatosNumber field expected to be a number", example = "potatosNumber:10")
    @NotNull(message = "potatosNumber field should not be empty")
    @Range(min = 1, max = 10, message = "potatos number should be in range between 1 and 10")
    private Short potatosNumber;

    @NotNull(message = "bagSupplier field should not be empty")
    @BagSupplierConstraint
    private String bagSupplier;

    @NotNull(message = "packedDate field should not be empty")
    @DateConstraint(message = "date format for packedDate field: " + Constants.CUSTOM_DATE_FORMAT)
    private String packedDate;

    @ExpectedTypeFormat(value = "price field currency format: " + PRICE_FORMAT, example = "price:10.25")
    @Digits(integer = 3, fraction = 2, message = "price field currency format: " + PRICE_FORMAT)
    @NotNull(message = "price field should not be empty")
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

    public String getBagSupplier() {
        return bagSupplier;
    }

    public void setBagSupplier(String bagSupplier) {
        this.bagSupplier = bagSupplier;
    }

    public String getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(String packedDate) {
        this.packedDate = packedDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PotatoBagDTO{" +
                "id='" + id + '\'' +
                ", potatosNumber=" + potatosNumber +
                ", bagSupplier='" + bagSupplier + '\'' +
                ", packedDate='" + packedDate + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
