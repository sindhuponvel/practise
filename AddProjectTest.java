package optimised;

import java.time.Duration;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import genericutility.DataBaseUtility;
import genericutility.FileUtility;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import pojoutility.ProjectPojo;

public class AddProjectTest {
	static FileUtility fLib = new FileUtility();
	DataBaseUtility dLib = new DataBaseUtility();
	Random ran = new Random();
	int ranInt = ran.nextInt(1300);
	ProjectPojo pObj = null;

	@Test
	public void projectTest() throws Throwable {

		pObj = new ProjectPojo("Me", "AK_" + ranInt, "Created", 0);

		Response resp = given().contentType(ContentType.JSON).body(pObj).when()
				.post("http://49.249.28.218:8091/addProject");
		resp.then().log().all();
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getContentType());

		System.out.println(resp.getHeader("X-Content-Type-Options"));
		System.out.println(resp.getHeader("Content-Type"));
		System.out.println(resp.getHeaders().asList());
		System.out.println(resp.getBody().asString());
		// System.out.println(resp.getDetailedCookie("session_id"));
		resp.then().statusCode(201);
		resp.then().statusLine("HTTP/1.1 201 ");
		resp.then().header("Content-Type", "application/json");

		String actualProName = resp.jsonPath().get("projectName");

		Assert.assertEquals(actualProName, "AK_" + ranInt);

		//boolean flag = dLib.executeQueryVerifyAndGetData("select * from project", 4, "AK_" + ranInt);
		//Assert.assertTrue(flag,"Data not verified");
		System.out.println("==END==");

	}

}
