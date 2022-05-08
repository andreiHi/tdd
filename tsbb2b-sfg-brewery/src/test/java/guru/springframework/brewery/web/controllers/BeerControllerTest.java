package guru.springframework.brewery.web.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import guru.springframework.brewery.services.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
class BeerControllerTest {

    @Mock
    BeerService beerService;

    @InjectMocks
    BeerController controller;

    MockMvc mockMvc;

    BeerDto validBeer;



    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .version(1)
                .beerName("Beer1")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(4)
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .upc(123456789012L)
                .createdDate(OffsetDateTime.now())
                .id(UUID.randomUUID())
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(jackson2HttpMessageConverter())
                .build();
    }

    @Test
    void testGetBeerById() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        given(beerService.findBeerById(any())).willReturn(validBeer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + validBeer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(validBeer.getBeerName())))
                .andExpect(jsonPath("$.createdDate", is(dateTimeFormatter.format(validBeer.getCreatedDate()))))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @DisplayName("List Ops - ")
    @Nested
    class TestListOperations {
        @Captor
        ArgumentCaptor<String>beerNameCaptor;

        @Captor
        ArgumentCaptor<BeerStyleEnum> beerStyleEnumCaptor;

        @Captor
        ArgumentCaptor<PageRequest> pageRequestCaptor;

        BeerPagedList beerPagedList;

        @BeforeEach
        void setUp() {
            List<BeerDto> beers = new ArrayList<>();
            beers.add(validBeer);
            beers.add(BeerDto.builder()
                    .version(1)
                    .beerName("Beer4")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(66)
                    .upc(123456789012L)
                    .lastModifiedDate(OffsetDateTime.now())
                    .createdDate(OffsetDateTime.now())
                    .id(UUID.randomUUID())
                    .build());
            beerPagedList = new BeerPagedList(beers, PageRequest.of(1, 1), 2L);

            given(beerService.listBeers(
                    beerNameCaptor.capture(),
                    beerStyleEnumCaptor.capture(),
                    pageRequestCaptor.capture())
            ).willReturn(beerPagedList);
        }

        @Test
        @DisplayName("Test list beers - no parameters")
        void testListBeers() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
                            .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.content[0].id", is(validBeer.getId().toString())));

        }

    }
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}