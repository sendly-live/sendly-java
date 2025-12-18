package examples;

import com.sendly.Sendly;
import com.sendly.models.ListMessagesRequest;
import com.sendly.models.Message;
import com.sendly.models.MessageList;

/**
 * Example: Listing and iterating messages.
 */
public class ListMessagesExample {
    public static void main(String[] args) {
        String apiKey = System.getenv("SENDLY_API_KEY");
        if (apiKey == null) {
            apiKey = "sk_test_v1_example";
        }

        Sendly client = new Sendly(apiKey);

        // List recent messages
        System.out.println("=== Recent Messages ===");
        MessageList messages = client.messages().list(
            ListMessagesRequest.builder()
                .limit(10)
                .build()
        );

        System.out.println("Total: " + messages.getTotal());
        System.out.println("Has more: " + messages.hasMore());
        System.out.println();

        for (Message msg : messages) {
            System.out.println(msg.getId() + ": " + msg.getTo() + " - " + msg.getStatus());
        }

        // List with filters
        System.out.println("\n=== Delivered Messages ===");
        MessageList delivered = client.messages().list(
            ListMessagesRequest.builder()
                .status("delivered")
                .limit(5)
                .build()
        );

        for (Message msg : delivered) {
            System.out.println(msg.getId() + ": Delivered at " + msg.getDeliveredAt());
        }

        // Iterate all with auto-pagination
        System.out.println("\n=== All Messages (paginated) ===");
        int count = 0;
        for (Message msg : client.messages().each()) {
            System.out.println(msg.getId() + ": " + msg.getTo());
            count++;
            if (count >= 20) break; // Limit for demo
        }
    }
}
