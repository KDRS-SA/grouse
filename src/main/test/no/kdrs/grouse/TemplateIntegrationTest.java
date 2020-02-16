package no.kdrs.grouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.spring.AuditConfiguration;
import no.kdrs.grouse.spring.GrouseUserDetailsService;
import no.kdrs.grouse.spring.TestSecurityConfiguration;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static no.kdrs.grouse.utils.Constants.*;
import static no.kdrs.grouse.utils.TestConstants.TEST_TEMPLATE_NAME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = GrouseApplication.class,
        webEnvironment = RANDOM_PORT)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@ContextConfiguration(classes = {TestSecurityConfiguration.class})
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@ActiveProfiles("test")
public class TemplateIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GrouseUserDetailsService grouseUserDetailsService;

    @Autowired
    private AuditConfiguration auditConfiguration;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void testCreateTemplate() throws Exception {
        Template template = new Template();
        template.setTemplateName(TEST_TEMPLATE_NAME);
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/grouse/" + TEMPLATE + SLASH)
                                .contextPath("/grouse")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(new Gson().toJson(template)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.templateId")
                                .exists())
                        .andExpect(jsonPath("$.createdDate")
                                .exists())
                        .andExpect(jsonPath("$.lastModifiedDate")
                                .exists())
                        .andExpect(jsonPath("$.ownedBy")
                                .exists())
                        .andExpect(jsonPath("$.templateId")
                                .exists())
                        .andExpect(jsonPath("$.templateName")
                                .value(TEST_TEMPLATE_NAME))
                        .andDo(document("home",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                links(halLinks(),
                                        linkWithRel
                                                (FUNCTIONALITY).
                                                description("Get list of " +
                                                        "functionality"),
                                        linkWithRel
                                                (DOCUMENT).
                                                description("Download document"),
                                        linkWithRel(SELF).
                                                description("Self REL")
                                )
                        ));
        /*
                "referenceTemplateFunctionality":[],
            "_links":{"self":{"href":"http://localhost:8080/grousetemplate/{templateId}","templated":true},
            "function":{"href":"http://localhost:8080/grousetemplate/{templateId}function",
                    "templated":true},"dokument":{"href":"http://localhost:8080/grouse/dokument"}}}
         */
        MockHttpServletResponse response = actions.andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testDeleteNonExistingTemplate() throws Exception {
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/grouse/" + TEMPLATE + SLASH + "1")
                                .contextPath("/grouse")
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isNotFound());

        MockHttpServletResponse response = actions.andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }
}