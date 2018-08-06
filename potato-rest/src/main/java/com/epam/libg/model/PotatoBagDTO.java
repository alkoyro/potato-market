package com.epam.libg.model;

import com.epam.libg.model.validator.constraint.BagSupplierConstraint;
import com.epam.libg.util.Constants;
import com.epam.libg.model.validator.constraint.DateConstraint;
import com.epam.libg.model.validator.constraint.PriceConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * pojo class for Rest interactions
 */
//TODO think on JsonDeserializer for both bagSupplier and packedDate
public class PotatoBagDTO {
    private String id;

    @NotNull(message = "potatosNumber field should not be empty")
    @Min(value = 1, message = "potatos number should be in range between 1 and 10")
    @Max(value = 10, message = "potatos number should be in range between 1 and 10")
    private Short potatosNumber;

    @NotNull(message = "bagSupplier field should not be empty")
    @BagSupplierConstraint
    private String bagSupplier;

    @NotNull(message = "packedDate field should not be empty")
    @DateConstraint(message = "date format for packedDate field: " + Constants.CUSTOM_DATE_FORMAT)
    private String packedDate;

    @NotNull(message = "price field should not be empty")
    @PriceConstraint(message = "currency format for price field: #,###.##")
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
