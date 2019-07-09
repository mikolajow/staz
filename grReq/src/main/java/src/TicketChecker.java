package src;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static src.Constants.*;

public class TicketChecker {


    private HttpClient httpClient;

    public TicketChecker() {
        Authenticator.setDefault(new CustomAuthenticator());

        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .authenticator(Authenticator.getDefault())
                .build();
    } // constructor


    public void checkTickets (ArrayList<Ticket> dataToCheck) {
        for (Ticket ticket : dataToCheck) {

            TicketData currentTicketData = getTicketData(ticket);


            ticket.setTicketStatus(currentTicketData.status);
            ticket.setActivationDate(currentTicketData.activationDate);
        }
    } // checkTickets


    public TicketData getTicketData(Ticket ticket) {
        TicketData ticketData;


        String requestPath = BASE_PATH + ticket.getePass() + "/" + ticket.getPassportEnd() + "/";

        HttpRequest request = getRequest(requestPath, HEADER_NAME, HEADER_VALUE);

        ticketData = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(
                        responseVariable -> extractTicketData(responseVariable))
                .join();

        return ticketData;
    } // getTicketData


    private TicketData extractTicketData(HttpResponse<String> httpResponse) {
        TicketData ticketData = new TicketData();

        int responseCode = httpResponse.statusCode();
        String responseBodyString = httpResponse.body();

        JsonObject jsonObject;


        if (responseCode > 399) {
            ticketData.status = "code " + responseCode;
            ticketData.activationDate = null;
        }
        else {
            jsonObject = new Gson().fromJson(responseBodyString, JsonObject.class);
            String status = jsonObject.get("validityState").getAsString();
            ticketData.status = status;

            JsonElement dateElement = jsonObject.get("activationDate");

            if (!dateElement.isJsonNull() && status.equals("NOT_STARTED")) {

                String activationDateString = jsonObject.get("activationDate").getAsString();

                try {
                    StringBuilder sb = new StringBuilder(activationDateString);
                    sb.deleteCharAt(10);

                    ticketData.activationDate = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss.SSS").parse(sb.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                } // catch
            } //  if (gotActivationDate)

        } // else - responseCode

        return ticketData;
    } // extractTicketData


    private HttpRequest getRequest(String requestPath, String headerName, String headerValue) {
        return HttpRequest.newBuilder()
                .uri(URI.create(requestPath))
                .header( headerName, headerValue)
                .GET()
                .build();
    }


} // class


























