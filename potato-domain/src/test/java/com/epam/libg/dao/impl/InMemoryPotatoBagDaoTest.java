package com.epam.libg.dao.impl;

import com.epam.libg.ServiceContextConfiguration;
import com.epam.libg.PotatoBagUtil;
import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                ServiceContextConfiguration.class,
                InMemoryPotatoBagDaoTest.TestServiceContextConfiguration.class})
public class InMemoryPotatoBagDaoTest {

    @Autowired
    private PotatoBagDao potatoBagDao;

    @TestConfiguration
    static class TestServiceContextConfiguration {

        @Bean
        @Primary
        public PotatoBagDao potatoBagDao() {
            return new InMemoryPotatoBagDao();
        }
    }

    @Test
    public void testFindPotatoBags() throws AddPotatoBagException {
        //given potatoBags
        List<PotatoBag> givenPotatoBags = PotatoBagUtil.getFilledPotatoBags(3);
        for (PotatoBag pb : givenPotatoBags) {
            pb.setId(UUID.randomUUID().toString());
            potatoBagDao.addPotatoBag(pb);
        }

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
    public void testAddPotatoBag() throws AddPotatoBagException {

        //given
        PotatoBag potatoBag = PotatoBagUtil.getFilledPotatoBag();

        //when
        PotatoBag returnedPotatoBag = potatoBagDao.addPotatoBag(potatoBag);

        //then
        Assert.assertNotNull(returnedPotatoBag);
        Assert.assertNotNull(returnedPotatoBag.getId());
    }

    @Test
    public void testAddPotatoBagFailNotValid() {

        //given
        PotatoBag givenPotatoBag = new PotatoBag();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> potatoBagDao.addPotatoBag(givenPotatoBag));

        //then
        Assert.assertNotNull(throwable);
        Assert.assertTrue(throwable instanceof ConstraintViolationException);
    }

    @Test(expected = AddPotatoBagException.class)
    public void testAddPotatoBagFailSameIds() throws AddPotatoBagException {

        //given
        PotatoBag potatoBag1 = PotatoBagUtil.getFilledPotatoBag();
        PotatoBag potatoBag2 = PotatoBagUtil.getFilledPotatoBag();
        potatoBag2.setId(potatoBag1.getId());

        //when adding two with the same id
        potatoBagDao.addPotatoBag(potatoBag1);

        //then throw exception
        potatoBagDao.addPotatoBag(potatoBag2);
    }
}
