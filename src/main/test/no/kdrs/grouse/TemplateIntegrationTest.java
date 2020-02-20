package no.kdrs.grouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.persistence.user.UserRepository;
import no.kdrs.grouse.spring.AuditConfiguration;
import no.kdrs.grouse.spring.GrouseUserDetailsService;
import no.kdrs.grouse.spring.TestSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static no.kdrs.grouse.utils.Constants.*;
import static no.kdrs.grouse.utils.TestConstants.*;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = GrouseApplication.class,
        webEnvironment = RANDOM_PORT)
//@AutoConfigureRestDocs(outputDir = "target/snippets")
@ContextConfiguration(classes = {TestSecurityConfiguration.class})
@ActiveProfiles("test")
public class TemplateIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

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
        String url = SLASH + CONTEXT_PATH + SLASH + TEMPLATE + SLASH;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new Gson().toJson(template))
                .with(user(grouseUserDetailsService
                        .loadUserByUsername("admin@kdrs.no"))));

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        System.out.println(response.getContentAsString());

        Pattern selfRel = compile(".+" + CONTEXT_PATH + SLASH + TEMPLATE +
                SLASH + "\\d+$");
        Pattern functionRel = compile(".+" + CONTEXT_PATH + SLASH + TEMPLATE +
                SLASH + "\\d+" + SLASH + FUNCTIONALITY + "$");
        Pattern documentRel = compile(".+" + CONTEXT_PATH + SLASH + TEMPLATE +
                SLASH + "\\d+" + SLASH + DOCUMENT + "$");

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.templateId")
                        .exists())
                .andExpect(jsonPath("$.createdDate")
                        .exists())
                .andExpect(jsonPath("$.lastModifiedDate")
                        .exists())
                .andExpect(jsonPath("$.ownedBy")
                        .exists())
                .andExpect(jsonPath("$.templateName")
                        .value(TEST_TEMPLATE_NAME))
                .andExpect(jsonPath("$._links.self.href",
                        matchesPattern(selfRel)))
                .andExpect(jsonPath("$._links.function.href",
                        matchesPattern(functionRel)))
                .andExpect(jsonPath("$._links.document.href",
                        matchesPattern(documentRel))
                );

        resultActions
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
    }

    @Test
    @Sql("/db-tests/single-template.sql")
    public void testCreateFunctionality() throws Exception {
        TemplateFunctionality templateFunctionality =
                new TemplateFunctionality.FunctionalityBuilder()
                        .description(TEST_FUNCTIONALITY_DESCRIPTION)
                        .consequence(TEST_FUNCTIONALITY_CONSEQUENCE)
                        .explanation(TEST_FUNCTIONALITY_EXPLANATION)
                        .functionalityNumber(TEST_FUNCTIONALITY_NUMBER)
                        .showMe(true)
                        .sectionTitle(TEST_FUNCTIONALITY_TITLE)
                        .sectionOrder(1)
                        .build();

        // 2 is the template number defined in single-template.sql
        String url =
                SLASH + CONTEXT_PATH + SLASH + TEMPLATE + SLASH + "2" + SLASH +
                        FUNCTIONALITY;
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new Gson().toJson(templateFunctionality))
                .with(user(grouseUserDetailsService
                        .loadUserByUsername("admin@kdrs.no"))));

        Pattern pattern = compile(".+" + CONTEXT_PATH + SLASH + TEMPLATE +
                SLASH + FUNCTIONALITY + SLASH + "\\d$");
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.functionalityId")
                        .exists())
                .andExpect(jsonPath("$.functionalityNumber")
                        .value(TEST_FUNCTIONALITY_NUMBER))
                .andExpect(jsonPath("$.createdDate")
                        .exists())
                .andExpect(jsonPath("$.lastModifiedDate")
                        .exists())
                .andExpect(jsonPath("$.ownedBy")
                        .exists())
                .andExpect(jsonPath("$.description")
                        .value(TEST_FUNCTIONALITY_DESCRIPTION))
                .andExpect(jsonPath("$.consequence")
                        .value(TEST_FUNCTIONALITY_CONSEQUENCE))
                .andExpect(jsonPath("$.explanation")
                        .value(TEST_FUNCTIONALITY_EXPLANATION))
                .andExpect(jsonPath("$.title")
                        .value(TEST_FUNCTIONALITY_TITLE))
                .andExpect(jsonPath("$._links.self.href",
                        matchesPattern(pattern))
                );

        resultActions
                .andDo(document("home",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(halLinks(),
                                linkWithRel(SELF).
                                        description("Self REL")
                        )
                ));
    }

    @Test
    public void testDeleteNonExistingTemplate() throws Exception {
        String url = SLASH + CONTEXT_PATH + SLASH + TEMPLATE + SLASH + "9999";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON));

        resultActions
                .andExpect(status().isNotFound());
    }
}