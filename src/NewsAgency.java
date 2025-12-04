import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NewsAgency {
    private final String name;
    private final CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService notifier;

    public NewsAgency(String name, int notifierThreads) {
        this.name = Objects.requireNonNull(name);
        this.notifier = Executors.newFixedThreadPool(Math.max(1, notifierThreads));
    }

    public void subscribe(Subscriber s) {
        if (s == null) return;
        subscribers.addIfAbsent(s);
        System.out.printf("Subscriber '%s' subscribed to %s%n", s.getName(), name);
    }

    public void unsubscribe(Subscriber s) {
        if (s == null) return;
        boolean removed = subscribers.remove(s);
        if (removed) System.out.printf("Subscriber '%s' unsubscribed from %s%n", s.getName(), name);
    }

    public void publish(News news) {
        System.out.printf("Publishing news: %s at %s. Notifying %d subscribers...%n",
                news.getTitle(), news.getPublishedAt(), subscribers.size());
        for (Subscriber s : subscribers) {
            // check interest first (keeps notification smaller)
            if (!s.isInterested(news)) continue;

            // dispatch asynchronously
            notifier.submit(() -> {
                try {
                    s.update(news);
                } catch (Exception e) {
                    // protect the agency from subscriber exceptions
                    System.err.printf("Error notifying subscriber '%s': %s%n", s.getName(), e);
                }
            });
        }
    }

    public void publish(String id, String title, String content, String category) {
        publish(new News(id, title, content, category, Instant.now()));
    }

    public void shutdown(long timeout, TimeUnit unit) throws InterruptedException {
        notifier.shutdown();
        if (!notifier.awaitTermination(timeout, unit)) {
            notifier.shutdownNow();
        }
        System.out.println("NewsAgency notifier shutdown complete.");
    }
}
