package org.niis.xroad.restapi.openapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.niis.xroad.restapi.converter.GlobalConfWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ClientsApiControllerIntegrationTest {

    @MockBean
    private GlobalConfWrapper globalConfWrapper;

    @Before
    public void setup() {
        when(globalConfWrapper.getMemberName(any())).thenReturn("test-member-name");
    }

    @Autowired
    private ClientsApiController clientsApiController;

    // tests with TestRestTemplate would be good, but require some extra work
    // for authentication setup.
    //    // WithMockUser will not work with restTemplate, would need
//    // to implement auth manually
//    @Autowired
//    private TestRestTemplate restTemplate;

//    @Test
//    public void test() {
//        ResponseEntity<List> response = this.restTemplate.getForEntity
//                ("/api/clients", List.class);
//        assertEquals(2, response.getBody().size());
//    }
    @Test
    @WithMockUser(roles = "XROAD_REGISTRATION_OFFICER")
    public void getClients() {
        ResponseEntity<List<org.niis.xroad.restapi.openapi.model.Client>> response = clientsApiController.getClients();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        org.niis.xroad.restapi.openapi.model.Client client = response.getBody().get(0);
        assertEquals("test-member-name", client.getMemberName());
        assertEquals("M1", client.getMemberCode());
    }

    @Test
    @WithMockUser(roles = "WRONG_ROLE")
    public void forbidden() {
        try {
            ResponseEntity<List<org.niis.xroad.restapi.openapi.model.Client>> response = clientsApiController.getClients();
            fail("should throw AccessDeniedException");
        } catch (AccessDeniedException expected) {}
    }

}
