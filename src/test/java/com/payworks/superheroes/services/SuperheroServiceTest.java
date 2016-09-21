package com.payworks.superheroes.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class SuperheroServiceTest {
	private Client client;

	// private static String serviceUrl =
	// "http://localhost:8080/services/superheroes";

	private static String serviceUrl = "http://52.3.13.170/services/superheroes";

	private JSONObject wolverin;

	public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final SimpleDateFormat isoFormatter = new SimpleDateFormat(
			ISO_FORMAT);

	@Before
	public void setUpBefore() {
		client = ClientBuilder.newClient();
		wolverin = new JSONObject();

		wolverin.put("name", "Wolverin");
		wolverin.put("pseudonym", "Agent Ten");
		wolverin.put("publisher", "Marvel Comics");

		List<String> wolverinSkills = new ArrayList<>();
		wolverinSkills.add("Extended longevity");
		wolverinSkills.add("Superhuman senses");
		wolverinSkills.add("Regenerative healing factor");
		wolverin.put("skills", wolverinSkills);

		List<String> wolverinAllies = new ArrayList<>();
		wolverinAllies.add("Cannonball");
		wolverinAllies.add("Nightcrawler");
		wolverinAllies.add("Professor Charles Xavier");
		wolverin.put("allies", wolverinAllies);

		wolverin.put("firstAppearance", isoFormatter.format(new Date()));
	}

	@After
	public void setUpAfter() {
		client.close();
	}

	@Test
	public void testSuperheroService() throws Exception {

		ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client
				.target(serviceUrl);
		resteasyWebTarget.register(new BasicAuthentication("notAdminUser",
				"notAdminPassword"));

		Response response = resteasyWebTarget.request().post(
				Entity.json(wolverin.toString()));

		// We check that when we do a POST request authenticated with a user
		// without the admin role, the server denies the access (401)
		Assert.assertEquals(401, response.getStatus());
		response.close();

		// //////////////////////////////////////////////////////////////////////
		resteasyWebTarget = (ResteasyWebTarget) client.target(serviceUrl);
		resteasyWebTarget.register(new BasicAuthentication("adminUser",
				"adminPassword"));
		response = resteasyWebTarget.request().post(
				Entity.json(wolverin.toString()));
		// We check that the response status code says the creation was
		// succesful when we are authenticated with a user with the admin role
		Assert.assertEquals(201, response.getStatus());
		String location = response.getLocation().toString();
		response.close();

		// //////////////////////////////////////////////////////////////////////
		String retrievedSuperheroString = client.target(location).request()
				.get(String.class);
		JSONObject retrievedSuperhero = new JSONObject(retrievedSuperheroString);

		// We put the id in our JSONObject to be able to do the comparison with
		// the received one.
		wolverin.put("id", retrievedSuperhero.getInt("id"));
		// We check that the retrieved object using the location is equal to the
		// one we sent to store.
		JSONAssert.assertEquals(retrievedSuperhero, wolverin, false);

		// //////////////////////////////////////////////////////////////////////
		retrievedSuperheroString = client
				.target(serviceUrl + "/" + wolverin.getInt("id")).request()
				.get(String.class);
		retrievedSuperhero = new JSONObject(retrievedSuperheroString);
		// We check that the retrieved object using the id is equal to the
		// one we sent to store.
		JSONAssert.assertEquals(retrievedSuperhero, wolverin, false);

		// //////////////////////////////////////////////////////////////////////
		resteasyWebTarget = (ResteasyWebTarget) client.target(serviceUrl);
		resteasyWebTarget.register(new BasicAuthentication("adminUser",
				"adminPassword"));
		response = resteasyWebTarget.request().post(Entity.json(null));
		// We check that the response status code is 400 when we try to store a
		// null object.
		Assert.assertEquals(400, response.getStatus());
		response.close();

		// //////////////////////////////////////////////////////////////////////
		wolverin.remove("name");
		resteasyWebTarget = (ResteasyWebTarget) client.target(serviceUrl);
		resteasyWebTarget.register(new BasicAuthentication("adminUser",
				"adminPassword"));
		response = resteasyWebTarget.request().post(
				Entity.json(wolverin.toString()));
		// We check that the response status code is 400 when we try to store an
		// object without the name attribute.
		Assert.assertEquals(400, response.getStatus());
		response.close();

	}
}
