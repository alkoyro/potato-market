package com.epam.libg.controller;

import com.epam.libg.converter.PotatoBagConverter;
import com.epam.libg.domain.BagSupplier;
import com.epam.libg.domain.PotatoBag;
import com.epam.libg.service.PotatoBagService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(PotatoBagRestController.class)
public class PotatoBagRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PotatoBagService potatoBagService;

    @MockBean
    private PotatoBagConverter potatoBagConverter;

    @Test
    public void findAllPotatoBags() throws Exception {
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

        Mockito.when(potatoBagService.findPotatoBags(Mockito.anyInt())).
                thenReturn(potatoBags);

        mvc.perform(MockMvcRequestBuilders.get("/potato-bags").
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
//                andExpect(content().string(""));
    }
}
