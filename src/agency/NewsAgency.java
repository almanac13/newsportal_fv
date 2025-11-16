package agency;

import model.Article;
import model.ArticleBuilder;
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

    public List<Article> getAllArticle() {
        return new ArrayList<>(articleHistory);
    }
    private void loadSampleArticles() {
        articleHistory.add(new ArticleBuilder()
                .setTitle("Java Basics")
                .setContent("Introduction to Java")
                .setCategory("Technology")
                .setPriority("High")
                .build());

        articleHistory.add(new ArticleBuilder()
                .setTitle("Stock Market Update")
                .setContent("Market rises 2%")
                .setCategory("Finance")
                .setPriority("Medium")
                .build());

        articleHistory.add(new ArticleBuilder()
                .setTitle("World Cup Final")
                .setContent("Argentina wins")
                .setCategory("Sports")
                .setPriority("High")
                .build());

        articleHistory.add(new ArticleBuilder()
                .setTitle("AI Breakthrough")
                .setContent("New AI model released")
                .setCategory("Technology")
                .setPriority("High")
                .build());

        articleHistory.add(new ArticleBuilder()
                .setTitle("Bitcoin News")
                .setContent("Crypto market analysis")
                .setCategory("Finance")
                .setPriority("Low")
                .build());
    }

}
