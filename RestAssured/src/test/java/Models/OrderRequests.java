package models;


public class OrderRequests
{
    public String username;
    public String password;

    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;
    public models.BookingDates bookingdates;
    public String additionalneeds;


    public OrderRequests(String username , String password)
    {
        this.username = username;
        this.password = password;
    }

    public OrderRequests(String firstname , String lastname , int totalprice , boolean depositpaid, models.BookingDates bookingdates, String additionalneeds)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;

    }


}