package Services;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BookingDates;
import models.OrderRequests;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestSample
{
    int bookingID;
    String token;

    @BeforeClass
    public void getHealthcheck()
    {
        given().
                log().all().
                when().get("https://restful-booker.herokuapp.com/ping").
                then()
                .statusCode(201)
                .log().all();
    }
    @Test(priority = 1)
    public void postCreateToken()
    {
        OrderRequests orderRequest = new OrderRequests("admin", "password123");
        String request = new Gson().toJson(orderRequest);

        RequestSpecification requests =  given()
                .body(request)
                .contentType(ContentType.JSON)
                .log().all();
        Response response = requests.post("https://restful-booker.herokuapp.com/auth");
        token = (response.jsonPath().get("token"));
        System.out.println(token);

    }
    @Test(priority = 2)
    public void postCreateBooking()
    {
        BookingDates bookingdates = new BookingDates("2022-04-28","2022-05-01");
        OrderRequests orderRequest = new OrderRequests("Kerem","Ocak",500,true,bookingdates,"Breakfast");
        String request = new Gson().toJson(orderRequest);

        RequestSpecification requests =  given()
                .body(request)
                .contentType(ContentType.JSON)
                .log().all();
        Response response = requests.post("https://restful-booker.herokuapp.com/booking");
        bookingID = (response.jsonPath().get("bookingid"));
        System.out.println(bookingID);

    }
    @DataProvider(name = "dataProviderGet")
    public Object[][] dataProviderCreate(){
        return new Object[][]{
                {bookingID, 200,"Kerem","Ocak",500,true,"Breakfast"},
        };
    }
    @Test(priority = 3, dataProvider = "dataProviderGet")
    public void getBooking(int bookingId,int statusCode, String firstName,String lastName,int totalprice,boolean depositpaid,String additionalneeds)
    {
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingId).
                then()
                .statusCode(statusCode)
                .body("firstname",equalTo(firstName))
                .body("lastname",equalTo(lastName))
                .body("totalprice",equalTo(totalprice))
                .body("depositpaid",equalTo(depositpaid))
                .body("additionalneeds",equalTo(additionalneeds))
                .log().all();
    }

    @DataProvider(name = "dataProviderGetId")
    public Object[][] dataProviderGet(){
        return new Object[][]{
                {200},
        };
    }
    @Test(priority = 4, dataProvider = "dataProviderGetId")
    public void getBookingId(int statusCode)
    {
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking?firstname=Kerem&lastname=Ocak").
                then()
                .statusCode(statusCode)
                .log().all();
    }

    @Test(priority = 5 )
    public void updateBooking()
    {

        BookingDates bookingdates = new BookingDates("2022-04-28","2022-05-01");
        OrderRequests orderRequest = new OrderRequests("Pelin","Kuney",400,true,bookingdates,"Dinner");
        String request = new Gson().toJson(orderRequest);

        RequestSpecification requests = given()
                .log().all()
                .header("Cookie","token= "+token+"")
                .header("Content-Type", "application/json")
                .body(request)
                .log().all();
        requests.put("https://restful-booker.herokuapp.com/booking/"+bookingID);
    }

    @DataProvider(name = "dataProviderUpdate")
    public Object[][] dataProviderUpdate(){
        return new Object[][]{
                {bookingID, 200,"Pelin","Kuney",400,true,"Dinner"},
        };
    }

    @Test(priority = 6, dataProvider = "dataProviderUpdate")
    public void getBookingUpdate(int bookingId,int statusCode, String firstName,String lastName,int totalprice,boolean depositpaid,String additionalneeds)
    {
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingId).
                then()
                .statusCode(statusCode)
                .body("firstname",equalTo(firstName))
                .body("lastname",equalTo(lastName))
                .body("totalprice",equalTo(totalprice))
                .body("depositpaid",equalTo(depositpaid))
                .body("additionalneeds",equalTo(additionalneeds))
                .log().all();
    }

    @Test(priority = 7 )
    public void partialUpdateBooking()
    {
        BookingDates bookingdates = new BookingDates("2012-02-02","2022-01-02");
        OrderRequests orderRequest = new OrderRequests("Pembe","Panter",987,true,bookingdates,"Allin");
        String request = new Gson().toJson(orderRequest);

        RequestSpecification requests = given()
                .log().all()
                .header("Cookie","token= "+token+"")
                .header("Content-Type", "application/json")
                .body(request)
                .log().all();
        requests.patch("https://restful-booker.herokuapp.com/booking/"+bookingID);
    }

    @DataProvider(name = "dataProviderPartialUpdate")
    public Object[][] dataProviderPartialUpdate(){
        return new Object[][]{
                {bookingID, 200,"Pembe","Panter"},
        };
    }

    @Test(priority = 8, dataProvider = "dataProviderPartialUpdate")
    public void getBookingPartialUpdate(int bookingId,int statusCode, String firstName,String lastName)
    {
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingId).
                then()
                .statusCode(statusCode)
                .body("firstname",equalTo(firstName))
                .body("lastname",equalTo(lastName))
                .log().all();
    }

    @AfterClass
    public void deleteBooking()
    {
        given()
                .log().all()
                .header("Cookie","token= "+token+"")
                .header("Content-Type", "application/json").
                when()
                .delete("https://restful-booker.herokuapp.com/booking/"+ bookingID).
                then()
                .statusCode(201)
                .log().all();
    }
}