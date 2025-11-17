package model;

import java.time.LocalDateTime;

public class ArticleBuilder {
    private String title;
    private String content;
    private String category;
    private String priority = "MEDIUM";
    private LocalDateTime timestamp;

    public ArticleBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public ArticleBuilder setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public ArticleBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Article build() {
        System.out.println("Builder: Constructing article '" + title + "'");
        return new Article(title, content, category, priority);
    }
}
