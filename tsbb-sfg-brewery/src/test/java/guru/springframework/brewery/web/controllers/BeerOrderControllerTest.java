package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.services.BeerOrderService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderLineDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerTest {

    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    Customer customer;

    BeerOrderDto beerOrder;

    BeerOrderPagedList beerOrderPagedList;

    @BeforeEach
    void setUp() {
        customer = Customer
                .builder()
                .id(UUID.randomUUID())
                .build();
        BeerDto beer = BeerDto
                .builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("beerName")
                .price(new BigDecimal("125.00"))
                .quantityOnHand(4)
                .upc(124234235L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        BeerOrderLineDto beerOrderLines = BeerOrderLineDto.builder().beerId(beer.getId()).build();
        beerOrder = BeerOrderDto
                .builder()
                .id(UUID.randomUUID())
                .customerId(customer.getId())
                .customerRef("12345678")
                .beerOrderLines(List.of(beerOrderLines))
                .build();
        beerOrderPagedList = new BeerOrderPagedList(List.of(beerOrder), PageRequest.of(1, 1), 1L);
    }

    @AfterEach
    void tearDown() {
        reset(beerOrderService);
    }

    @Test
    void listOrders() throws Exception {
        given(beerOrderService.listOrders(any(), any())).willReturn(beerOrderPagedList);
        mockMvc.perform(get("/api/v1/customers/" + customer.getId() + "/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].customerId", is(customer.getId().toString())));
    }

    @Test
    void getOrder() throws Exception {
        given(beerOrderService.getOrderById(any(), any())).willReturn(beerOrder);
        mockMvc.perform(get("/api/v1/customers/" + customer.getId() + "/orders/" + beerOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(customer.getId().toString())))
                .andExpect(jsonPath("$.id", is(beerOrder.getId().toString())));
    }
}