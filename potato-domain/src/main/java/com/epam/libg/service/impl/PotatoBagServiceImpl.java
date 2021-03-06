package com.epam.libg.service.impl;

import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import com.epam.libg.service.PotatoBagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Impl of {@link PotatoBagService}
 */
public class PotatoBagServiceImpl implements PotatoBagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagServiceImpl.class);

    /**
     * default limit value to return
     */
    public static final Integer DEFAULT_LIMIT = 3;

    private final PotatoBagDao potatoBagDao;

    public PotatoBagServiceImpl(PotatoBagDao potatoBagDao) {
        this.potatoBagDao = potatoBagDao;
    }

    /**
     * finds potatoBags with optional limit value.
     * If limit is null then accepts {@value #DEFAULT_LIMIT} value
     *
     * @param limit optional limit size to return
     * @return available potatoBags
     */
    @Override
    public List<PotatoBag> findPotatoBags(@Nullable Integer limit) {
        Integer limitToReturn = Optional.ofNullable(limit).
                filter(lim -> lim > 0).
                orElse(DEFAULT_LIMIT);

        LOGGER.info("find all potato bags with limit: " + limitToReturn);

        return potatoBagDao.findPotatoBags(limitToReturn);
    }

    /**
     * generates {@link UUID} and defines it for {@link PotatoBag#id}
     * and then adds potatoBag to {@link PotatoBagDao}
     *
     * @param potatoBag to add
     * @return created potatoBag
     * @throws AddPotatoBagException if potatoBag failed to be added
     */
    @Override
    public PotatoBag addPotatoBag(@NonNull PotatoBag potatoBag) throws AddPotatoBagException {
        LOGGER.info("adding potato bag: " + potatoBag);

        String id = UUID.randomUUID().toString();
        potatoBag.setId(id);

        return potatoBagDao.addPotatoBag(potatoBag);
    }
}
