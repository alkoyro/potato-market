package com.epam.libg.converter;

import com.epam.libg.domain.BagSupplier;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.ConvertingException;
import com.epam.libg.model.PotatoBagDTO;
import com.epam.libg.util.DateConverter;
import com.epam.libg.util.PriceConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * converts {@link PotatoBag} to {@link PotatoBagDTO} and vice-versa
 */
@Component
public class PotatoBagConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagConverter.class);

    public PotatoBag convertPotatoBagDTOToPotatoBag(@NonNull PotatoBagDTO potatoBagDTO)
            throws ConvertingException {

        PotatoBag potatoBag = new PotatoBag();
        potatoBag.setPotatosNumber(potatoBagDTO.getPotatosNumber());

        try {
            BagSupplier bagSupplier = BagSupplier.findBySupplierName(potatoBagDTO.getBagSupplier());
            potatoBag.setBagSupplier(bagSupplier);
            potatoBag.setPackedDate(DateConverter.convert(potatoBagDTO.getPackedDate()));
            potatoBag.setPrice(PriceConverter.convert(potatoBagDTO.getPrice()));
        } catch (ConvertingException e) {
            LOGGER.error("Error while converting: " + potatoBag, e);
            throw new ConvertingException("Error converting potatoBag: " + potatoBag, e);
        }

        return potatoBag;
    }

    public PotatoBagDTO convertPotatoBagToPotatoBagDTO(@NonNull PotatoBag potatoBag) {
        PotatoBagDTO potatoBagDTO = new PotatoBagDTO();
        potatoBagDTO.setId(potatoBag.getId());
        potatoBagDTO.setBagSupplier(potatoBag.getBagSupplier().getSupplierName());
        potatoBagDTO.setPackedDate(DateConverter.convert(potatoBag.getPackedDate()));
        potatoBagDTO.setPotatosNumber(potatoBag.getPotatosNumber());
        potatoBagDTO.setPrice(PriceConverter.convert(potatoBag.getPrice()));

        return potatoBagDTO;
    }


}
