import java.util.Set;

public interface Subscriber {
    void update(News news);

    boolean isInterested(News news);

    void setPreferences(Set<String> categories);

    String getName();
}
