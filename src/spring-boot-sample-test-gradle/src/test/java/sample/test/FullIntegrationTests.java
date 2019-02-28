/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.test;

import ee.ria.xroad.common.conf.serverconf.model.ClientType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sample.test.domain.VehicleIdentificationNumber;
import sample.test.repository.ClientRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * go to database instead mocking
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class FullIntegrationTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
    private ClientRepository clientRepository;

	@Test
	public void test() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/{username}/vehicle", String.class, "sframework");
		assertEquals("ford smax", response.getBody());
	}


    @Test
    public void testClientRepository() {
	    List<ClientType> clients = clientRepository.getAllClients();
        assertEquals(2, clients.size());
    }

}
