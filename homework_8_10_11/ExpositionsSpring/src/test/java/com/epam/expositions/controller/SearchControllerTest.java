package com.epam.expositions.controller;

import com.epam.expositions.service.ExpositionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/init.sql")
public class SearchControllerTest {
    private final ExpositionService expositionService = mock(ExpositionService.class);
    private final SearchController searchController = new SearchController(expositionService);

    private final MockHttpSession mockHttpSession = new MockHttpSession();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(searchController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void shouldGetSearchPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
    }

    @Test
    public void shouldRedirectToHomePage() throws Exception {
        String paramSearch = "Test Search";
        String paramOrder = "date_startDESC";

        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .accept(MediaType.TEXT_HTML)
                .session(mockHttpSession)
                .param("search", paramSearch)
                .param("order", paramOrder))
                .andExpect(status().isOk())
                .andExpect(model().attribute("expositionList", Matchers.anything()));
    }
}
