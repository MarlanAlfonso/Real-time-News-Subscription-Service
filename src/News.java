import java.time.Instant;
import java.util.Objects;

public final class News {
    private final String id;
    private final String title;
    private final String content;
    private final String category;
    private final Instant publishedAt;

    public News(String id, String title, String content, String category, Instant publishedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.publishedAt = publishedAt;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public Instant getPublishedAt() { return publishedAt; }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
