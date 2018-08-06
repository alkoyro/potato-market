package com.epam.libg.service;

import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * service for adding and finding potatoBags
 */
public interface PotatoBagService {

    /**
     * loads available potatoBags
     *
     * @param limit optional limitSize to return
     * @return found potatoBags
     */
    List<PotatoBag> findPotatoBags(@Nullable Integer limit);

    /**
     * adds new potatoBag
     *
     * @param potatoBag to add
     * @return created potatoBag
     * @throws AddPotatoBagException if potatoBag failed to be added
     */
    PotatoBag addPotatoBag(@NonNull PotatoBag potatoBag) throws AddPotatoBagException;
}
