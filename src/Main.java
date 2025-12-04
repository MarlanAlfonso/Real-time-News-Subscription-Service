import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        NewsAgency agency = new NewsAgency("GlobalNews", 4);

        ConcreteSubscriber alice = new ConcreteSubscriber("Alice", Set.of("politics", "world"));
        ConcreteSubscriber bob   = new ConcreteSubscriber("Bob", Set.of("sports"));
        ConcreteSubscriber carol = new ConcreteSubscriber("Carol", Set.of()); // empty = all categories

        agency.subscribe(alice);
        agency.subscribe(bob);
        agency.subscribe(carol);

        //For sample output
        // Publish a few items
        agency.publish("N001", "Elections: surprise result", "Detailed content...", "politics");
        agency.publish("N002", "Championship final tonight", "Match details...", "sports");
        agency.publish("N003", "Tech startup raises funds", "Startup content...", "business");

        // dynamic preference update: Bob now wants business too
        bob.setPreferences(Set.of("sports", "business"));
        System.out.println("Bob updated preferences.");

        // publish more
        agency.publish("N004", "New startup launches product", "Product details...", "business");

        // unsubscribe Alice
        agency.unsubscribe(alice);

        // publish breaking news: only Carol and Bob (if interested)
        agency.publish("N005", "Major world event", "Breaking news...", "world");

        // allow short time for async tasks to run
        TimeUnit.SECONDS.sleep(1);

        // shutdown
        agency.shutdown(5, TimeUnit.SECONDS);
    }
}
