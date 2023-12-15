import java.util.Scanner;
import java.util.Map;

public class EventTicketingSystemTester {

    private static EventTicketingSystem ets = new EventTicketingSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeEvents(); // Initialize some events for testing

        char choice;
        do {
            displayMenu();
            choice = scanner.nextLine().charAt(0);
            processChoice(choice);
        } while (choice != '5');

        scanner.close();
    }

    private static void initializeEvents() {
        ets.createEvent("EVENT1", 10); // Event 1 with 10 tickets
        ets.createEvent("EVENT2", 5);  // Event 2 with 5 tickets
    }

    private static void displayMenu() {
        System.out.println("\nEvent Ticketing System Tester");
        System.out.println("1. Purchase Ticket");
        System.out.println("2. Cancel Ticket");
        System.out.println("3. Check Ticket Status");
        System.out.println("4. Display Event Details");
        System.out.println("5. Quit");
        System.out.print("Enter choice (1-5): ");
    }

    private static void processChoice(char choice) {
        switch (choice) {
            case '1':
                testPurchaseTicket();
                break;
            case '2':
                testCancelTicket();
                break;
            case '3':
                testCheckTicketStatus();
                break;
            case '4':
                displayEventDetails();
                break;
            case '5':
                System.out.println("Exiting Tester.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void testPurchaseTicket() {
        System.out.print("Enter ticket ID to purchase (e.g., EVENT1-T1): ");
        String ticketId = scanner.nextLine();
        System.out.println(ets.purchaseTicket(ticketId));
    }

    private static void testCancelTicket() {
        System.out.print("Enter ticket ID to cancel (e.g., EVENT1-T1): ");
        String ticketId = scanner.nextLine();
        System.out.println(ets.cancelTicket(ticketId));
    }

    private static void testCheckTicketStatus() {
        System.out.print("Enter ticket ID to check status (e.g., EVENT1-T1): ");
        String ticketId = scanner.nextLine();
        System.out.println(ets.getTicketStatus(ticketId));
    }

    private static void displayEventDetails() {
        System.out.println("\nEvent Details:");
        for (String eventId : ets.getEvents().keySet()) {
            int totalTickets = ets.getEvents().get(eventId);
            int soldTickets = countSoldTickets(eventId);
            int availableTickets = totalTickets - soldTickets;

            System.out.println("Event ID: " + eventId);
            System.out.println("  Total Tickets: " + totalTickets);
            System.out.println("  Tickets Sold: " + soldTickets);
            System.out.println("  Tickets Available: " + availableTickets);
        }
    }

    private static int countSoldTickets(String eventId) {
        int soldCount = 0;
        for (Map.Entry<String, Map<String, EventTicketingSystem.TicketStatus>> entry : ets.getTickets().entrySet()) {
            String ticketId = entry.getKey();
            if (ticketId.startsWith(eventId) && entry.getValue().get(eventId) == EventTicketingSystem.TicketStatus.SOLD) {
                soldCount++;
            }
        }
        return soldCount;
    }
}
