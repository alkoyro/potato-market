package com.epam.libg.service.impl;

import com.epam.libg.ServiceContextConfiguration;
import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import com.epam.libg.service.PotatoBagService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.epam.libg.PotatoBagUtil.getFilledPotatoBags;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ServiceContextConfiguration.class})
@RunWith(SpringRunner.class)
public class PotatoBagServiceImplTest {

    @Autowired
    @Spy
    private PotatoBagService potatoBagService;

    @MockBean
    private PotatoBagDao potatoBagDao;

    @Test
    public void testAddPotatoBag() throws AddPotatoBagException {
        //given
        PotatoBag givenPotatoBag = new PotatoBag();

        when(potatoBagDao.addPotatoBag(givenPotatoBag)).thenReturn(givenPotatoBag);

        //when
        PotatoBag actualPotatoBag = potatoBagService.addPotatoBag(givenPotatoBag);

        //then
        verify(potatoBagDao).addPotatoBag(givenPotatoBag);

        Assert.assertNotNull(actualPotatoBag);
        Assert.assertNotNull(actualPotatoBag.getId());
    }

    @Test
    public void testFindPotatoBags() {
        //test limit > DEFAULT_LIMIT
        //given
        List<PotatoBag> givenPotatoBags = getFilledPotatoBags(PotatoBagServiceImpl.DEFAULT_LIMIT + 1);
        when(potatoBagDao.findPotatoBags(PotatoBagServiceImpl.DEFAULT_LIMIT + 1))
                .thenReturn(givenPotatoBags);

        //when
        List<PotatoBag> actualPotatoBags = potatoBagService
                .findPotatoBags(PotatoBagServiceImpl.DEFAULT_LIMIT + 1);

        //then
        Assert.assertEquals(givenPotatoBags.size(), actualPotatoBags.size());
        Assert.assertEquals(actualPotatoBags, givenPotatoBags);

        //test 3 given potBags and limit null -> will return DEFAULT_LIMIT
        //given
        givenPotatoBags = givenPotatoBags.subList(0, PotatoBagServiceImpl.DEFAULT_LIMIT);
        when(potatoBagDao.findPotatoBags(PotatoBagServiceImpl.DEFAULT_LIMIT))
                .thenReturn(givenPotatoBags);

        //when
        actualPotatoBags = potatoBagService.findPotatoBags(null);

        //then
        Assert.assertTrue(PotatoBagServiceImpl.DEFAULT_LIMIT == actualPotatoBags.size());
        Assert.assertEquals(actualPotatoBags, givenPotatoBags);


        //test 3 given potBags and limit < 0 -> will return DEFAULT_LIMIT
        //given
        givenPotatoBags = givenPotatoBags.subList(0, PotatoBagServiceImpl.DEFAULT_LIMIT);
        when(potatoBagDao.findPotatoBags(PotatoBagServiceImpl.DEFAULT_LIMIT))
                .thenReturn(givenPotatoBags);

        //when
        actualPotatoBags = potatoBagService.findPotatoBags(-1);

        //then
        Assert.assertTrue(PotatoBagServiceImpl.DEFAULT_LIMIT == actualPotatoBags.size());
        Assert.assertEquals(actualPotatoBags, givenPotatoBags);
    }
}
