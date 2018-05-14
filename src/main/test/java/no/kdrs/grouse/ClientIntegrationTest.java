package no.kdrs.grouse;

import no.kdrs.grouse.model.Functionality;
import no.kdrs.grouse.model.GrouseUser;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static no.kdrs.grouse.utils.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Set of integration tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test all CRUD operations on the User entity. The following tests are
     * run within this test.
     *
     * 1. Test CREATE of User object
     * 2. Test READ of newly created user object
     * 3. Test UPDATE of newly created user object
     * 4. Test DELETE of newly created user object
     *
     */
    @Test
    public void testCRUDUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = "test@example.com";
        String firstName = "Grouse user firstname";
        String lastName = "Grouse user lastname";
        String password = "password";

        // 1. Test Create
        GrouseUser user = new GrouseUser();
        user.setUsername(username);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setPassword(password);

        ResponseEntity<GrouseUser> responseEntity =
                restTemplate.postForEntity(SLASH + USER, user,
                        GrouseUser.class);

        GrouseUser returnedUser = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(username, returnedUser.getUsername());
        assertEquals(firstName, returnedUser.getFirstname());
        assertEquals(lastName, returnedUser.getLastname());
        assertTrue(encoder.matches(password, returnedUser.getPassword()));

        // 2. Test Read
        responseEntity = restTemplate.getForEntity(SLASH + USER + SLASH +
                returnedUser.getUsername(), GrouseUser.class);
        returnedUser = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(username, returnedUser.getUsername());
        assertEquals(firstName, returnedUser.getFirstname());
        assertEquals(lastName, returnedUser.getLastname());
        assertTrue(encoder.matches(password, returnedUser.getPassword()));

        // 3. Test Update
        user.setFirstname(firstName + "_");
        user.setLastname(lastName + "_");
        user.setPassword(password + "_");

        HttpEntity<GrouseUser> requestEntity = new HttpEntity<>(user);
        ResponseEntity<GrouseUser> response =
                restTemplate.exchange(SLASH + USER + SLASH +
                                returnedUser.getUsername(), HttpMethod.PUT,
                        requestEntity, GrouseUser.class);

        returnedUser = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstName + "_", returnedUser.getFirstname());
        assertEquals(lastName + "_", returnedUser.getLastname());
        assertTrue(encoder.matches(password + "_",
                returnedUser.getPassword()));

        // 4. Test delete
        ResponseEntity<String> deleteResponseEntity = restTemplate.exchange
                (SLASH + USER + SLASH + user.getUsername(),
                        HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, deleteResponseEntity.getStatusCode());
    }

    @Test
    public void testCRUDUserWithErrors() {

        // 1. Test GET for non-existing user
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(SLASH + USER + SLASH + "no@no.no",
                        String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // 2. Test DELETE on non-existing user
        ResponseEntity<String> deleteResponseEntity =
                restTemplate.exchange(SLASH + USER + SLASH + "no@no.no",
                        HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, deleteResponseEntity.getStatusCode());
    }

    /**
     *
     */
    @Test
    public void createClient() {

        Functionality functionality = new
                Functionality.FunctionalityBuilder()
                .functionalityNumber("99")
                .description("description")
                .sectionTitle("title")
                .build();

        ResponseEntity<Functionality> responseEntity =
                restTemplate.postForEntity(SLASH + FUNCTIONALITY,
                        functionality, Functionality.class);
        Functionality client = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("description", client.getDescription());
    }
}
