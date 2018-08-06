package com.epam.libg.service.impl;

import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.domain.validator.PotatoBagValidator;
import com.epam.libg.exception.AddPotatoBagException;
import com.epam.libg.service.PotatoBagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.epam.libg.PotatoBagUtil.getFilledPotatoBag;
import static com.epam.libg.PotatoBagUtil.getFilledPotatoBags;

@RunWith(SpringRunner.class)
public class PotatoBagServiceImplTest {

    @Autowired
    private PotatoBagService potatoBagService;

    @MockBean
    private PotatoBagDao potatoBagDao;

    @TestConfiguration
    static class PotatoBagServiceImplTestContextConfiguration {

        @Bean
        public PotatoBagService potatoBagService() {
            return new PotatoBagServiceImpl();
        }

        @Bean
        public PotatoBagValidator potatoBagValidator() {
            return new PotatoBagValidator();
        }
    }

    @Before
    public void initMocks() throws AddPotatoBagException {
        PotatoBag mockPotatoBag = getFilledPotatoBag();

        Mockito.when(potatoBagDao.addPotatoBag(Mockito.any(PotatoBag.class))).thenReturn(mockPotatoBag);
        potatoBagDao.findPotatoBags(1);
    }

    @Test
    public void testAddPotatoBagSuccess() throws AddPotatoBagException {
        PotatoBag potatoBag = potatoBagService.addPotatoBag(getFilledPotatoBag());

        Assert.assertEquals(potatoBag, getFilledPotatoBag());
    }

    @Test(expected = AddPotatoBagException.class)
    public void testAddPotatoBagFailed() throws AddPotatoBagException {
        potatoBagService.addPotatoBag(new PotatoBag());
    }

    @Test
    public void testFindPotatoBags() {
        //mock data
        List<PotatoBag> mockedPotatoBags = getFilledPotatoBags();

        //test DEFAULT_LIMIT will return the correct value
        Mockito.when(potatoBagDao.findPotatoBags(Mockito.anyInt())).
                thenReturn(mockedPotatoBags.subList(0, PotatoBagServiceImpl.DEFAULT_LIMIT)).
                thenReturn(mockedPotatoBags.subList(0, PotatoBagServiceImpl.DEFAULT_LIMIT)).
                thenReturn(mockedPotatoBags);

        Assert.assertTrue(potatoBagService.findPotatoBags(null).size() == PotatoBagServiceImpl.DEFAULT_LIMIT);

        //test limit < 0
        Assert.assertTrue(potatoBagService.findPotatoBags(-10).size() == PotatoBagServiceImpl.DEFAULT_LIMIT);

        //test limit > DEFAULT_LIMIT
        Assert.assertTrue(potatoBagService.findPotatoBags(5).size() == mockedPotatoBags.size());
    }
}
