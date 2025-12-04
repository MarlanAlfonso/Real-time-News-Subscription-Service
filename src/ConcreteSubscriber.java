import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConcreteSubscriber implements Subscriber {
    private final String name;
    // thread-safe view: we swap the reference on updates
    private volatile Set<String> subscribedCategories;

    public ConcreteSubscriber(String name, Set<String> initialCategories) {
        this.name = Objects.requireNonNull(name);
        setPreferences(initialCategories);
    }

    @Override
    public void update(News news) {
        // Simple action: print received news. In a real system this might send push, email, etc.
        System.out.printf("[%s] received: %s - %s (category=%s, at=%s)%n",
                name, news.getId(), news.getTitle(), news.getCategory(), news.getPublishedAt());
    }

    @Override
    public boolean isInterested(News news) {
        Set<String> cats = subscribedCategories;
        // if no preferences set (empty), treat as subscribed to all categories
        return cats.isEmpty() || cats.contains(news.getCategory());
    }

    @Override
    public void setPreferences(Set<String> categories) {
        if (categories == null) categories = Collections.emptySet();
        // copy defensively and make immutable
        this.subscribedCategories = Collections.unmodifiableSet(new HashSet<>(categories));
    }

    @Override
    public String getName() {
        return name;
    }
}
