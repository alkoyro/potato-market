package com.epam.libg.controller;

import com.epam.libg.converter.PotatoBagConverter;
import com.epam.libg.domain.BagSupplier;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.model.PotatoBagDTO;
import com.epam.libg.service.PotatoBagService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PotatoBagRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PotatoBagService potatoBagService;

    @Test
    public void findAllPotatoBags() throws Exception {
        //given
        List<PotatoBag> potatoBags = IntStream.range(0, 5).
                mapToObj(i -> {
                    PotatoBag potatoBag = new PotatoBag();

                    potatoBag.setId("123");
                    potatoBag.setPrice(new BigDecimal(12.12));
                    potatoBag.setPotatosNumber((short) 10);

                    potatoBag.setPackedDate(new Date(1000));
                    potatoBag.setBagSupplier(BagSupplier.OWEL);

                    return potatoBag;
                }).collect(Collectors.toList());

        when(potatoBagService.findPotatoBags(anyInt())).
                thenReturn(potatoBags);

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/potato-bags")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("limit", "5"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String responseContent = mvcResult.getResponse().getContentAsString();
        List<PotatoBagDTO> potatoBagDTOS = new ObjectMapper()
                .readValue(responseContent, new TypeReference<List<PotatoBagDTO>>(){});

        Assert.assertEquals(potatoBags.size(), potatoBagDTOS.size());
    }
}
