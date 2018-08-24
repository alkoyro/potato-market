package com.epam.libg.dao.impl;

import com.epam.libg.PotatoBagUtil;
import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryPotatoBagDaoTest {

    @Spy
    private Map<String, PotatoBag> potatoBagMap = new HashMap<>();

    @InjectMocks
    private PotatoBagDao potatoBagDao = new InMemoryPotatoBagDao();

    @Test
    public void testFindPotatoBags() {
        //given potatoBags
        List<PotatoBag> givenPotatoBags = PotatoBagUtil.getFilledPotatoBags()
                .subList(0, 3);
        givenPotatoBags.forEach(pb -> potatoBagMap.put(UUID.randomUUID().toString(), pb));

        //when limit > available size
        List<PotatoBag> actualPotatoBags = potatoBagDao.findPotatoBags(10);
        //then
        Assert.assertEquals(actualPotatoBags.size(), givenPotatoBags.size());

        //when limit == available size
        actualPotatoBags = potatoBagDao.findPotatoBags(3);
        //then
        Assert.assertEquals(actualPotatoBags.size(), 3);

        //when limit < available size
        actualPotatoBags = potatoBagDao.findPotatoBags(2);
        //then
        Assert.assertEquals(actualPotatoBags.size(), 2);

    }

    @Test
    public void testAddPotatoBags() throws AddPotatoBagException {

        //given potatoBags with same id
        List<PotatoBag> givenPotatoBags = PotatoBagUtil.getFilledPotatoBags();
        List<PotatoBag> exceptedPotatoBags = new ArrayList<>();

        //when add potatoBags
        for (PotatoBag potatoBag : givenPotatoBags) {
            exceptedPotatoBags.add(potatoBagDao.addPotatoBag(potatoBag));
        }

        //then expect all will be added with unique generated ids
        Assert.assertEquals(givenPotatoBags.size(), exceptedPotatoBags.size());
        long uniqueIdsSize = exceptedPotatoBags.stream()
                .map(PotatoBag::getId)
                .distinct()
                .count();

        Assert.assertEquals(uniqueIdsSize, givenPotatoBags.size());
    }

    @Test
    public void testAddPotatoBag() throws AddPotatoBagException {

        //given
        PotatoBag potatoBag = PotatoBagUtil.getFilledPotatoBag();

        //when
        PotatoBag returnedPotatoBag = potatoBagDao.addPotatoBag(potatoBag);

        //then
        Assert.assertNotNull(returnedPotatoBag);
        Assert.assertNotNull(returnedPotatoBag.getId());
    }

    @Test(expected = AddPotatoBagException.class)
    public void testFailAddPotatoBag() throws AddPotatoBagException {

        //given
        PotatoBag mockedPotBag = mock(PotatoBag.class);

        //given mock
        when(mockedPotBag.getId()).thenReturn("123");

        //when adding two with the same id
        potatoBagDao.addPotatoBag(mockedPotBag);

        //then throw exception
        potatoBagDao.addPotatoBag(mockedPotBag);
    }
}
