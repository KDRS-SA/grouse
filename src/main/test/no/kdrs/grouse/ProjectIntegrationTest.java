package no.kdrs.grouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import no.kdrs.grouse.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static no.kdrs.grouse.utils.Constants.*;
import static no.kdrs.grouse.utils.TestConstants.TEST_PROJECT_NAME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = GrouseApplication.class,
        webEnvironment = RANDOM_PORT)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ProjectIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @WithMockUser
    public void testCreateProject() throws Exception {
        Project project = new Project();
        project.setProjectName(TEST_PROJECT_NAME);
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/grouse/" + PROJECT + SLASH)
                                .contextPath("/grouse")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(new Gson().toJson(project)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.projectName")
                                .value(TEST_PROJECT_NAME));

        MockHttpServletResponse response = actions.andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    @WithMockUser
    public void testDeleteNonExistingProject() throws Exception {

        System.out.println(PROJECT_NUMBER_PARAMETER);

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/grouse/" + PROJECT + SLASH + "1")
                                .contextPath("/grouse")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isNotFound());

        MockHttpServletResponse response = actions.andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }
}