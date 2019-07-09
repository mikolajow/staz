package src;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(value = TestInstance.Lifecycle.PER_METHOD)
class MainTest {

    private static HttpClient httpClient;


    @BeforeAll
    public static void initialize() {
        Authenticator.setDefault(new CustomAuthenticator());

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .authenticator(Authenticator.getDefault())
                .build();
    }


    @Test
    public void serverConectionTest() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://eurail-inspection.ticketingsuite.com/api/eiw/ticket/gE5TE9uV/RT0"))
                .header("TENANT", "eurail")
                .GET()
                .build();


        String requestBody = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        assertEquals(
                "{\"validityState\":\"NOT_STARTED\",\"activationDate\":\"2019-05-07T11:18:03.777\",\"validityDate\":\"2020-04-06\",\"ePassDetails\":{\"kind\":\"Italy Mobile Tickets\",\"type\":null,\"traveler\":{\"fullName\":\"J. L******k\",\"passportNumber\":\"******T0\"},\"status\":\"NOT_STARTED\",\"validityPeriod\":{\"startDate\":\"2020-04-06\",\"endDate\":\"2020-05-06\"}}}"
                , requestBody);
    }



} // class





















