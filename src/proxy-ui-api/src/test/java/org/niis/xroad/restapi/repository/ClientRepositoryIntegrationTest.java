package org.niis.xroad.restapi.repository;

import ee.ria.xroad.common.conf.serverconf.model.ClientType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ClientRepositoryIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void test() {
        List<ClientType> clients = clientRepository.getAllClients();
        assertEquals(2, clients.size());
    }
}


