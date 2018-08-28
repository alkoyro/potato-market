package com.epam.libg.service.impl;

import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.dao.impl.InMemoryPotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import com.epam.libg.service.PotatoBagService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;

import static com.epam.libg.PotatoBagUtil.getFilledPotatoBag;
import static com.epam.libg.PotatoBagUtil.getFilledPotatoBags;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PotatoBagServiceImplTest.PotatoBagServiceImplTestContextConfiguration.class})
@RunWith(SpringRunner.class)
public class PotatoBagServiceImplTest {

    @Autowired
    @Spy
    private PotatoBagService potatoBagService;

    @MockBean
    private PotatoBagDao potatoBagDao;

    @TestConfiguration
    static class PotatoBagServiceImplTestContextConfiguration {

        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }

        @Bean
        public PotatoBagService potatoBagService() {
            return new PotatoBagServiceImpl(potatoBagDao());
        }

        @Bean
        public PotatoBagDao potatoBagDao() {
            return new InMemoryPotatoBagDao();
        }
    }

    @Test
    public void testAddPotatoBagSuccess() throws AddPotatoBagException {
        //given
        PotatoBag givenPotatoBag = getFilledPotatoBag();

        when(potatoBagDao.addPotatoBag(eq(givenPotatoBag))).thenReturn(givenPotatoBag);

        //when
        PotatoBag actualPotatoBag = potatoBagService.addPotatoBag(givenPotatoBag);

        //then
        verify(potatoBagDao).addPotatoBag(eq(givenPotatoBag));

        Assert.assertNotNull(actualPotatoBag);
        Assert.assertEquals(actualPotatoBag, givenPotatoBag);
    }

    @Test
    public void testAddPotatoBagFailed() {
        //given
        PotatoBag givenPotatoBag = new PotatoBag();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> potatoBagService.addPotatoBag(givenPotatoBag));

        //then
        verifyZeroInteractions(potatoBagDao);
        Assert.assertNotNull(throwable);
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
