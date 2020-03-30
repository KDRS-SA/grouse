package no.kdrs.grouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.persistence.user.UserRepository;
import no.kdrs.grouse.spring.AuditConfiguration;
import no.kdrs.grouse.spring.GrouseUserDetailsService;
import no.kdrs.grouse.spring.TestSecurityConfiguration;
import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.PatchObjectsSerializer;
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
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = GrouseApplication.class,
        webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = {TestSecurityConfiguration.class})
@ActiveProfiles({"test", "do-not-import-templates"})
public class ProjectIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrouseUserDetailsService grouseUserDetailsService;

    @Autowired
    private AuditConfiguration auditConfiguration;

    private final String uuid = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4" +
            "}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    private final Pattern selfRelUUIDPattern =
            compile(".+" + CONTEXT_PATH + SLASH + PROJECT + SLASH + uuid);
    private final Pattern selfRelProjectFunctionalityPattern =
            compile(".+" + CONTEXT_PATH + SLASH + PROJECT_FUNCTIONALITY + SLASH
                    + "\\d+");
    private final Pattern functionRelPattern =
            compile(".+" + CONTEXT_PATH + SLASH + PROJECT + SLASH + uuid +
                    SLASH + FUNCTIONALITY + "$");
    private final Pattern documentRelPattern =
            compile(".+" + CONTEXT_PATH + SLASH + PROJECT + SLASH + uuid +
                    SLASH + DOCUMENT + "$");

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
    @Sql({"/db-tests/empty-database.sql"})
    public void testCreateProject() throws Exception {
        Project project = new Project();
        project.setProjectName(TEST_PROJECT_NAME);
        String url = SLASH + CONTEXT_PATH + SLASH + PROJECT;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new Gson().toJson(project))
                .with(user(grouseUserDetailsService
                        .loadUserByUsername("admin@example.com"))));

        MockHttpServletResponse response = resultActions
                .andReturn().getResponse();
        System.out.println(response.getContentAsString());

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.projectId")
                        .exists())
                .andExpect(jsonPath("$.createdDate")
                        .exists())
                .andExpect(jsonPath("$.lastModifiedDate")
                        .exists())
                .andExpect(jsonPath("$.ownedBy")
                        .exists())
                .andExpect(jsonPath("$.projectName")
                        .value(TEST_PROJECT_NAME))
                .andExpect(jsonPath("$._links.self.href",
                        matchesPattern(selfRelUUIDPattern)))
                .andExpect(jsonPath("$._links.function.href",
                        matchesPattern(functionRelPattern)))
                .andExpect(jsonPath("$._links.document.href",
                        matchesPattern(documentRelPattern))
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
    // empty any projects from table and populate new values
    @Sql({"/db-tests/empty-database.sql", "/db-tests/multiple-project.sql"})
    public void testReadListProjects() throws Exception {
        String url = SLASH + CONTEXT_PATH + SLASH + PROJECT;
        Pattern selfRelProjectPaged =
                compile(".+" + CONTEXT_PATH + SLASH + PROJECT + ESCAPE +
                        QUESTION_MARK + PAGE + EQUALS + DEFAULT_PAGE_NUMBER +
                        AMPERSAND + SIZE + EQUALS + DEFAULT_PAGE_SIZE + "$");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON)
                .with(user(grouseUserDetailsService
                        .loadUserByUsername("admin@example.com"))));

        MockHttpServletResponse response = resultActions
                .andReturn().getResponse();
        System.out.println(response.getContentAsString());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.projects",
                        hasSize(3)))
                .andExpect(jsonPath(
                        "$._embedded.projects[0].projectId",
                        is(notNullValue())))
                .andExpect(jsonPath(
                        "$._embedded.projects[0].createdDate",
                        is(notNullValue())))
                .andExpect(jsonPath(
                        "$._embedded.projects[0].lastModifiedDate",
                        is(notNullValue())))
                .andExpect(jsonPath(
                        "$._embedded.projects[0].ownedBy",
                        is(notNullValue())))
                .andExpect(jsonPath(
                        "$._embedded.projects[0].projectName")
                        .value(TEST_PROJECT_NAME))
                .andExpect(jsonPath(
                        "$._embedded.projects[0]._links.self.href",
                        matchesPattern(selfRelUUIDPattern)))
                .andExpect(jsonPath(
                        "$._embedded.projects[0]._links.function.href",
                        matchesPattern(functionRelPattern)))
                .andExpect(jsonPath(
                        "$._links.self.href",
                        matchesPattern(selfRelProjectPaged)));

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
    // empty any projects from table
    @Sql({"/db-tests/empty-database.sql", "/db-tests/single-project.sql"})
    public void testCreateFunctionality() throws Exception {
        ProjectFunctionality projectFunctionality =
                new ProjectFunctionality.FunctionalityBuilder()
                        .description(TEST_FUNCTIONALITY_DESCRIPTION)
                        .consequence(TEST_FUNCTIONALITY_CONSEQUENCE)
                        .explanation(TEST_FUNCTIONALITY_EXPLANATION)
                        .functionalityNumber(TEST_FUNCTIONALITY_NUMBER)
                        .showMe(true)
                        .sectionTitle(TEST_FUNCTIONALITY_TITLE)
                        .sectionOrder(1)
                        .build();
        String url =
                SLASH + CONTEXT_PATH + SLASH + PROJECT + SLASH +
                        "8b05d4d0-8663-4654-8a0f-65c88f57fc96" + SLASH +
                        FUNCTIONALITY;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contextPath(SLASH + CONTEXT_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new Gson().toJson(projectFunctionality))
                .with(user(grouseUserDetailsService
                        .loadUserByUsername("admin@example.com"))));

        MockHttpServletResponse response = resultActions
                .andReturn().getResponse();
        System.out.println(response.getContentAsString());
        System.out.println(response.getStatus());

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.projectFunctionalityId")
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
                        matchesPattern(selfRelProjectFunctionalityPattern))
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

    /**
     * Add a single project to the database and test that it is possible to
     * delete it. The expected result is:
     * <p>
     * - 206 No content
     * - the body is empty
     *
     * @throws Exception can be thrown if there is a problem with perform or
     *                   andExpect
     */
    @Test
    // empty any projects from table and populate new value
    @Sql({"/db-tests/empty-database.sql", "/db-tests/single-project.sql",
            "/db-tests/multiple-functionality.sql",
            "/db-tests/multiple-requirements.sql"})
    public void testDeleteProject() throws Exception {
        String url = SLASH + CONTEXT_PATH + SLASH + PROJECT + SLASH +
                "8b05d4d0-8663-4654-8a0f-65c88f57fc96";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete(url)
                .contextPath(SLASH + CONTEXT_PATH));

        resultActions
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Add a single project to the database and test that it is possible to
     * update it. The expected result is:
     * <p>
     * - Updated values are reflected in returned result
     * - the ETAG value is updated
     *
     * @throws Exception can be thrown if there is a problem with perform or
     *                   andExpect
     */
    @Test
    // empty any projects from table and populate new value
    @Sql({"/db-tests/empty-database.sql", "/db-tests/single-project.sql"})
    public void testUpdateProject() throws Exception {
        String url = SLASH + CONTEXT_PATH + SLASH + PROJECT + SLASH +
                "8b05d4d0-8663-4654-8a0f-65c88f57fc96";

        PatchObject patchObject = new PatchObject();
        patchObject.setPath(SLASH + "projectName");
        patchObject.setValue(TEST_PROJECT_NAME_UPDATED);
        patchObject.setOp(REPLACE);
        PatchObjects patchObjects = new PatchObjects(patchObject);

        objectMapper.registerModule(new SimpleModule("SimpleModule")
                .addSerializer(new PatchObjectsSerializer()));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .patch(url)
                .contentType(APPLICATION_JSON)
                .header(ETAG, "0")
                .contextPath(SLASH + CONTEXT_PATH)
                .content(objectMapper.writeValueAsString(patchObjects)));

        resultActions
                .andExpect(header().string(ETAG, instanceOf(String.class)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("/db-tests/empty-database.sql")
    public void testDeleteNonExistingProject() throws Exception {
        String url = SLASH + CONTEXT_PATH + SLASH + PROJECT + SLASH +
                "9ba5d6d0-a623-f6a4-3a0c-25c83f47fc9f";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete(url)
                .contextPath(SLASH + CONTEXT_PATH));

        resultActions
                .andExpect(status().isNotFound());
    }
}