package model;

import java.time.LocalDateTime;

public class Article {
    private String title;
    private String content;
    private String category;
    private String priority;
    private LocalDateTime timstamp;

    public Article(String title, String content, String category, String priority){
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority;
        this.timstamp = LocalDateTime.now();
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public String getCategory(){
        return category;
    }
    public String getPriority(){
        return priority;
    }
    public LocalDateTime getTimstamp(){
        return timstamp;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setPriority(String priority){
        this.priority = priority;
    }
    public void setTimstamp(LocalDateTime timstamp){
        this.timstamp = timstamp;
    }

    @Override
    public String toString(){
        return title + " - " + category + " (" + priority + ")" + " - " + timstamp.toLocalTime();
    }
}
