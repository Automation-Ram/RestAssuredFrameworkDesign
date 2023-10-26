package api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.Userendpoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayloads;

	@BeforeClass

	public void setUpData() {
		faker = new Faker();
		userPayloads = new User();

		userPayloads.setId(faker.idNumber().hashCode());
		userPayloads.setUserName(faker.name().username());
		userPayloads.setFirstName(faker.name().firstName());
		userPayloads.setLastName(faker.name().lastName());
		userPayloads.setEmail(faker.internet().safeEmailAddress());
		userPayloads.setPassword(faker.internet().password(5, 10));
		userPayloads.setPhone(faker.phoneNumber().cellPhone());

	}

	@Test(priority = 1)
	public void testPostUser() {
		Response response = Userendpoints.createUser(userPayloads);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2)
	public void testgetUser() {
		Response response = Userendpoints.readUser(this.userPayloads.getUserName());
		response.then().log().all();
		//response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority = 3)
	public void testUpdateUser() {
		userPayloads.setFirstName(faker.name().firstName());
		userPayloads.setLastName(faker.name().lastName());
		userPayloads.setEmail(faker.internet().safeEmailAddress());
		Response response = Userendpoints.updateUser(this.userPayloads.getUserName(),userPayloads);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		//checking data after update
		Response resafterUpdate=Userendpoints.readUser(this.userPayloads.getUserName());
		Assert.assertEquals(resafterUpdate.getStatusCode(), 200);
	}
	@Test(priority = 4)
	public void testDeleteuser()
	{
		Response response = Userendpoints.deleteUser(this.userPayloads.getUserName());
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
