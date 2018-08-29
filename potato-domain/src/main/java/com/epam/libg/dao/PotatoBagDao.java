package com.epam.libg.dao;

import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * {@link PotatoBag} dao service
 */
@Validated
public interface PotatoBagDao {

    /**
     * loads all available potatoBags from repo
     *
     * @param limit size to return
     * @return available potatoBags
     */
    List<PotatoBag> findPotatoBags(@NonNull Integer limit);

    /**
     * adds potatoBag into repo
     *
     * @param potatoBag potatoBag to add
     * @return new added potatoBag entity
     * @throws AddPotatoBagException if adding potatoBag failed
     */
    PotatoBag addPotatoBag(@NonNull @Valid PotatoBag potatoBag) throws AddPotatoBagException;
}
