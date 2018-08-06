package com.epam.libg.domain;

import java.util.stream.Stream;

public enum BagSupplier {
    DE_COSTER("De Coster"),
    OWEL("Owel"),
    PATATAS_RUBEN("Patatas Ruben"),
    YUNNAN_SPICES("Yunnan Spices");

    private String supplierName;

    BagSupplier(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public static BagSupplier findBySupplierName(String supplierName) {
        return Stream.of(values()).
                filter(v -> v.supplierName.equals(supplierName)).
                findFirst().
                orElse(null);
    }

}
