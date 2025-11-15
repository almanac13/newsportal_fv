package agency;

import model.Article;
import observer.Observer;
import observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements Subject {
    private List<Observer> subscribers;
    private Article latestArticle;
    private List<Article> articleHistory;

    public NewsAgency() {
        subscribers = new ArrayList<>();
        articleHistory = new ArrayList<>();
        loadSampleArticles();
    }


    @Override
    public void registerObserver(Observer observer) {
        if (!subscribers.contains(observer)) {
            subscribers.add(observer);
            System.out.println("Observer registered: " + observer);
        }else System.out.println("Observer already registered");

    }

    @Override
    public void removeObserver(Observer observer) {
        subscribers.remove(observer);
        System.out.println("Observer removed: " + observer);

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : subscribers) {
            observer.update(latestArticle);
        }
    }

    public void publishArticle(Article article) {
        System.out.println("Publishing article: " + article);
        latestArticle = article;
        articleHistory.add(article);
        notifyObservers();
    }

    public List<Article> getArticleHistory() {
        return new ArrayList<>(articleHistory);
    }
    private void loadSampleArticles() {
        articleHistory.add(new Article("Java Basics", "Introduction to Java", "Technology", "High"));
        articleHistory.add(new Article("Stock Market Update", "Market rises 2%", "Finance", "Medium"));
        articleHistory.add(new Article("World Cup Final", "Argentina wins", "Sports", "High"));
        articleHistory.add(new Article("AI Breakthrough", "New AI model released", "Technology", "High"));
        articleHistory.add(new Article("Bitcoin News", "Crypto market analysis", "Finance", "Low"));
    }
}
