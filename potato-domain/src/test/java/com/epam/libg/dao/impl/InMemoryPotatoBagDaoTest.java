package com.epam.libg.dao.impl;

import com.epam.libg.PotatoBagUtil;
import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Answers.RETURNS_MOCKS;

public class InMemoryPotatoBagDaoTest {

    @Mock(answer = RETURNS_MOCKS)
    private Map<String, PotatoBag> potatoBagMap;

    @InjectMocks
    private PotatoBagDao potatoBagDao = new InMemoryPotatoBagDao();

    @Test
    public void testFindPotatoBags() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(potatoBagMap.values()).thenReturn(PotatoBagUtil.getFilledPotatoBags().subList(0, 3));

        //test when request limit > PotatoBags size
        Mockito.when(potatoBagMap.size()).thenReturn(3);
        Assert.assertEquals(potatoBagDao.findPotatoBags(10).size(), 3);


        //test when request limit < PotatoBags size
        Mockito.when(potatoBagMap.size()).thenReturn(2);
        Assert.assertEquals(potatoBagDao.findPotatoBags(2).size(), 2);
    }


    @Test
    public void testAddPotatoBag() {
        PotatoBagUtil.getFilledPotatoBags().
                forEach(pb -> {
                    try {
                        potatoBagDao.addPotatoBag(pb);
                    } catch (AddPotatoBagException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test(expected = AddPotatoBagException.class)
    public void testFailAddPotatoBag() throws AddPotatoBagException {
        PotatoBag mockedPotBag = Mockito.mock(PotatoBag.class);
        Mockito.when(mockedPotBag.getId()).thenReturn("123");

        //adding two with the same id

        potatoBagDao.addPotatoBag(mockedPotBag);
        potatoBagDao.addPotatoBag(mockedPotBag);
    }
}
