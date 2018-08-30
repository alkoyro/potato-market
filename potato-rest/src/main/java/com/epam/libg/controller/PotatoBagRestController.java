package com.epam.libg.controller;

import com.epam.libg.domain.PotatoBag;
import com.epam.libg.exception.AddPotatoBagException;
import com.epam.libg.exception.ConvertingException;
import com.epam.libg.model.ExpectedTypeFormat;
import com.epam.libg.model.PotatoBagDTO;
import com.epam.libg.service.PotatoBagService;
import com.epam.libg.converter.PotatoBagConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rest controller that interact with base endpoint "/potato-bags"
 */
@RestController
@RequestMapping(value = "/potato-bags",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PotatoBagRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagRestController.class);

    @Autowired
    private PotatoBagService potatoBagService;

    @Autowired
    private PotatoBagConverter potatoBagConverter;

    @PostMapping
    ResponseEntity<PotatoBagDTO> addPotatoBag(@Valid @RequestBody PotatoBagDTO potatoBagDTO)
            throws AddPotatoBagException, ConvertingException {

        PotatoBag potatoBagToAdd = potatoBagConverter.convertPotatoBagDTOToPotatoBag(potatoBagDTO);
        PotatoBag addedPotatoBag = potatoBagService.addPotatoBag(potatoBagToAdd);

        PotatoBagDTO potatoBagDTOToReturn = potatoBagConverter.convertPotatoBagToPotatoBagDTO(addedPotatoBag);

        LOGGER.debug("created potato back return: " + potatoBagDTOToReturn);
        return new ResponseEntity<>(potatoBagDTOToReturn, HttpStatus.CREATED);
    }

    @GetMapping
    List<PotatoBagDTO> findAllPotatoBags(@RequestParam(required = false)
                                         @ExpectedTypeFormat(value = "limit value should a numeric value", example = "?limit=3")
                                                 Integer limit) {
        List<PotatoBag> potatoBags = potatoBagService.findPotatoBags(limit);
        List<PotatoBagDTO> convertedPotatoBags = potatoBags.stream().
                map(pb -> potatoBagConverter.convertPotatoBagToPotatoBagDTO(pb)).
                collect(Collectors.toList());

        LOGGER.debug("found potato bags: " + convertedPotatoBags);

        return convertedPotatoBags;
    }
}
