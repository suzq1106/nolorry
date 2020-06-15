package com.mikes.nolorry.test;

import java.net.URL;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.mikes.nolorry.NolorryApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NolorryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		String url = String.format("http://localhost:%d/", port);
		this.base = new URL(url);
	}

	@Test
	public void login() throws Exception {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("account", "15121073881");
		body.add("password", "1!2@3#4$");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(base.toString() + "/login", request, Map.class);

		Assert.assertEquals("account is not 15121073881", "15121073881", response.getBody().get("account"));
	}
}
