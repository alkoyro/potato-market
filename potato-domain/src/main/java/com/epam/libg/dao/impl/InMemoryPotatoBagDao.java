package com.epam.libg.dao.impl;

import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * represent in-memory impl of {@link PotatoBagDao}
 */
@Repository
public class InMemoryPotatoBagDao implements PotatoBagDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryPotatoBagDao.class);

    /**
     * synchronized map container for {@link PotatoBag}
     * key is {@link PotatoBag#id}
     * value is {@link PotatoBag}
     */
    private Map<String, PotatoBag> potatoBagMap = new ConcurrentHashMap<>();

    /**
     * loads potato bags from {@link InMemoryPotatoBagDao#potatoBagMap} depending on limit
     *
     * @param limit size to return
     * @return potatoBags
     */
    public List<PotatoBag> findPotatoBags(@NonNull Integer limit) {
        if (potatoBagMap.size() >= limit) {
            return new ArrayList<>(potatoBagMap.values()).subList(0, limit);
        } else {
            return new ArrayList<>(potatoBagMap.values());
        }
    }

    /**
     * generates {@link UUID} and defines it for {@link PotatoBag#id}
     *
     * @param potatoBag potatoBag to add
     * @return created potatoBag
     * @throws AddPotatoBagException if adding operation failed
     */
    public PotatoBag addPotatoBag(@NonNull PotatoBag potatoBag) throws AddPotatoBagException {
        String id = UUID.randomUUID().toString();
        potatoBag.setId(id);

        if (potatoBagMap.putIfAbsent(potatoBag.getId(), potatoBag) == null) {
            return potatoBag;
        } else {
            LOGGER.error("potatoBag: " + potatoBag + " already defined");
            throw new AddPotatoBagException("failded to add potato bag: " + potatoBag);
        }
    }
}
