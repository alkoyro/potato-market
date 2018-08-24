package com.epam.libg;

import com.epam.libg.domain.BagSupplier;
import com.epam.libg.domain.PotatoBag;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PotatoBagUtil {
    private PotatoBagUtil() {
    }

    public static PotatoBag getFilledPotatoBag() {
        PotatoBag potatoBag = new PotatoBag();

        potatoBag.setId("123");
        potatoBag.setPrice(new BigDecimal(12.12));
        potatoBag.setPotatosNumber((short) 10);

        potatoBag.setPackedDate(new Date(1000));
        potatoBag.setBagSupplier(BagSupplier.OWEL);

        return potatoBag;
    }

    public static List<PotatoBag> getFilledPotatoBags() {
        return IntStream.range(0, 5)
                .mapToObj(pb -> getFilledPotatoBag())
                .collect(Collectors.toList());
    }


}
