import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EventTicketingSystem {

    private Map<String, Integer> events;
    private Map<String, Map<String, TicketStatus>> tickets;

    public EventTicketingSystem() {
        this.events = new HashMap<>();
        this.tickets = new HashMap<>();
    }
    public enum TicketStatus {
        AVAILABLE, SOLD
    }

    public void createEvent(String eventId, int numTickets) {
        events.put(eventId, numTickets);
        for (int i = 1; i <= numTickets; i++) {
            String ticketId = eventId + "-T" + i;
            tickets.put(ticketId, new HashMap<>() {{
                put(eventId, TicketStatus.AVAILABLE);
            }});
        }
    }

    public String purchaseTicket(String ticketId) {
        if (!tickets.containsKey(ticketId)) {
            return "Invalid ticket ID.";
        }
        Map<String, TicketStatus> ticketInfo = tickets.get(ticketId);
        if (ticketInfo.get(ticketInfo.keySet().iterator().next()) == TicketStatus.AVAILABLE) {
            ticketInfo.replaceAll((eventId, status) -> TicketStatus.SOLD);
            return "Ticket " + ticketId + " has been purchased.";
        } else {
            return "Ticket " + ticketId + " is already sold.";
        }
    }

    public String cancelTicket(String ticketId) {
        if (!tickets.containsKey(ticketId)) {
            return "Invalid ticket ID.";
        }
        Map<String, TicketStatus> ticketInfo = tickets.get(ticketId);
        if (ticketInfo.get(ticketInfo.keySet().iterator().next()) == TicketStatus.SOLD) {
            ticketInfo.replaceAll((eventId, status) -> TicketStatus.AVAILABLE);
            return "Ticket " + ticketId + " has been canceled.";
        } else {
            return "Ticket " + ticketId + " is not sold yet.";
        }
    }

    public String getTicketStatus(String ticketId) {
        if (!tickets.containsKey(ticketId)) {
            return "Invalid ticket ID.";
        }
        TicketStatus status = tickets.get(ticketId).values().iterator().next();
        return "Ticket " + ticketId + " status: " + status;
    }

    // Getter methods for testing
    public Map<String, Integer> getEvents() {
        return events;
    }

    public Map<String, Map<String, TicketStatus>> getTickets() {
        return tickets;
    }

    public static void main(String[] args) {
        EventTicketingSystem ets = new EventTicketingSystem();
        Scanner scanner = new Scanner(System.in);

        ets.createEvent("EID1", 3); // Create event EID1 with 3 tickets

        while (true) {
            System.out.println("Enter an action (purchase, cancel, status, exit):");
            String action = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(action)) {
                break;
            }

            System.out.println("Enter ticket ID (e.g., EID1-T1):");
            String ticketId = scanner.nextLine().trim();

            switch (action.toLowerCase()) {
                case "purchase":
                    System.out.println(ets.purchaseTicket(ticketId));
                    break;
                case "cancel":
                    System.out.println(ets.cancelTicket(ticketId));
                    break;
                case "status":
                    System.out.println(ets.getTicketStatus(ticketId));
                    break;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
