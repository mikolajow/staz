package src;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(value = TestInstance.Lifecycle.PER_METHOD)
class TicketCheckerTest {

    private static TicketChecker ticketChecker;

    @BeforeAll
    public static void initialize() {
        ticketChecker = new TicketChecker();
    }


    @Test
    void getTicketStatusTest2() {
        Ticket ticket = new Ticket("gE5TE9uV", "RT0");
        String status = ticketChecker.getTicketData(ticket).status;
        assertEquals("NOT_STARTED", status);
    }


    @ParameterizedTest
//    @MethodSource({"ticketsProvider"})
    @MethodSource({"ticketsProviderFunctions"})
    public void getTicketStatusTest(Ticket ticket, String expected) {
        String status = ticketChecker.getTicketData(ticket).status;
        assertEquals(expected, status);
    }


    static Stream<Arguments> ticketsProviderFunctions() {

        return Streams.zip(
                Stream.of(new Ticket("gE5TE9uV", "RT0"),
                        new Ticket("banana", "banananaa"),
                        new Ticket("ns5w8nPu", "002"),
                        new Ticket("WHRH56Pv", "234"),
                        new Ticket("gw7zeQMd", "RT0"),
                        new Ticket("3CjwFMadd", "234"),
                        new Ticket("pr6irYz5", "234"),
                        new Ticket("R6WxsnEm", "234"),
                        new Ticket("R2iTZgZ5", "234"),
                        new Ticket("WHRH56Pv", "234")
                        ),
                Stream.of("NOT_STARTED",
                        "code 500",
                        "NOT_VALID",
                        "NOT_STARTED",
                        "NOT_VALID",
                        "code 500",
                        "NOT_VALID",
                        "NOT_VALID",
                        "NOT_VALID",
                        "NOT_STARTED"
                ),
                (ticket, expectedStatus) -> Arguments.of(ticket, expectedStatus)
        );
    } // ticketsProviderFunctions


//    static Stream<Arguments> ticketsProvider() {
//        return Stream.of(
//                Arguments.of(new Ticket("gE5TE9uV", "RT0"), "NOT_STARTED"),
//                Arguments.of(new Ticket("banana", "banananaa"), "code 500"));
//    }


} // class























