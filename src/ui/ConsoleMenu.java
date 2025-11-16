package ui;

import agency.NewsAgency;
import factory.NotificationStrategyFactory;
import model.Article;
import model.ArticleBuilder;
import model.Category;
import model.Subscriber;
import strategy.NotificationStrategy;
import visitor.CounterVisitor;
import visitor.SimplePrintVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private NewsAgency newsAgency = new NewsAgency();
    private Scanner scanner = new Scanner(System.in);
    private List<Subscriber> subscribers = new ArrayList<>();

    public void start() {
        System.out.println("=== Welcome to News Portal System ===\n");
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            if (choice == 1) {
                addSubscriber();
            } else if (choice == 2) {
                publishArticle();
            } else if (choice == 3) {
                changeNotificationMethod();
            } else if (choice == 4) {
                manageCategories();
            } else if (choice == 5) {
                listSubscribers();
            } else if (choice == 6) {
                viewAllArticles();
            } else if (choice == 7) {
                countArticles();
            } else if (choice == 8) {
                removeSubscriber();
            } else if (choice == 0) {
                System.out.println("Exiting from News Portal. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add Subscriber");
        System.out.println("2. Publish Article");
        System.out.println("3. Change Notification Method");
        System.out.println("4. Manage Categories");
        System.out.println("5. List Subscribers");
        System.out.println("6. View All Articles");
        System.out.println("7. Count Articles (by priority)");
        System.out.println("8. Remove Subscriber");
        System.out.println("0. Exit");
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void addSubscriber() {
        System.out.println("Add new Subscriber");
        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String phoneNumber = getStringInput("Phone number: ");
        String deviceId = getStringInput("Device ID: ");
        System.out.println("1.Email  2.SMS  3.Push  4.Fax  5.Paper");
        int choice = getIntInput("Choice: ");
        String strategyType;
        if (choice == 1)
            strategyType = "email";
        else if (choice == 2)
            strategyType = "sms";
        else if (choice == 3)
            strategyType = "push";
        else if (choice == 4)
            strategyType = "fax";
        else if (choice == 5)
            strategyType = "paper";
        else
            strategyType = "email";
        NotificationStrategy strategy = NotificationStrategyFactory.createNotificationStrategy(strategyType);
        Subscriber subscriber = new Subscriber(name, email, phoneNumber, deviceId, strategy);
        newsAgency.registerObserver(subscriber);
        subscribers.add(subscriber);
        System.out.println("Subscriber added.");
    }

    private void publishArticle() {
        System.out.println("Publish new article");
        String title = getStringInput("Title: ");
        String content = getStringInput("Content: ");
        Category.printAllCategories();
        int categoryId = getIntInput("Category ID: ");
        String category;
        if (categoryId >= 1 && categoryId <= Category.ALL_CATEGORIES.length) {
            category = Category.ALL_CATEGORIES[categoryId - 1];
        } else {
            System.out.println("Invalid category ID. Please enter a valid category ID.");
            category = " ";
        }
        System.out.println("Priority: 1.HIGH  2.MEDIUM  3.LOW");
        int choice = getIntInput("Enter your choice: ");
        String priority;
        if (choice == 1) {
            priority = "HIGH";
        } else if (choice == 2) {
            priority = "MEDIUM";
        } else if (choice == 3) {
            priority = "LOW";
        } else {
            priority = "MEDIUM";
        }
        Article article = new ArticleBuilder()
                .setTitle(title)
                .setContent(content)
                .setCategory(category)
                .setPriority(priority)
                .build();
        newsAgency.publishArticle(article);
        System.out.println("Published.");
    }

    private void changeNotificationMethod() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers found.");
            return;
        }
        listSubscribers();
        int index = getIntInput("Enter your choice: ") - 1;
        if (index < 0 || index >= subscribers.size()) {
            System.out.println("Invalid choice. Please enter a number between 1 and " + subscribers.size());
            return;
        }
        Subscriber subscriber = subscribers.get(index);
        System.out.println("=== Notification Methods ===");
        System.out.println("Choose notification: 1.Email  2.SMS  3.Push  4.Fax  5.Paper");
        int choice = getIntInput("Choice: ");
        String strategyType;
        if (choice == 1)
            strategyType = "email";
        else if (choice == 2)
            strategyType = "sms";
        else if (choice == 3)
            strategyType = "push";
        else if (choice == 4)
            strategyType = "fax";
        else if (choice == 5)
            strategyType = "paper";
        else
            strategyType = "email";
        try {
            NotificationStrategy newStrategy = NotificationStrategyFactory.createNotificationStrategy(strategyType);
            subscriber.setNotificationStrategy(newStrategy);
            System.out.println("Notification method updated successfully for " + subscriber.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void manageCategories() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers found.");
            return;
        }
        listSubscribers();
        int index = getIntInput("Enter your choice: ") - 1;
        if (index < 0 || index >= subscribers.size()) {
            System.out.println("Invalid choice. Please enter a number between 1 and " + subscribers.size());
            return;
        }
        Subscriber subscriber = subscribers.get(index);
        System.out.println("1.Add category  2.Remove  3.Set  4.All");
        int choice = getIntInput("Choice: ");
        if (choice == 1)
            addCategory(subscriber);
        else if (choice == 2)
            removeCategory(subscriber);
        else if (choice == 3)
            setCategories(subscriber);
        else if (choice == 4) {
            for (String category : Category.ALL_CATEGORIES) {
                subscriber.subscribeToCategory(category);
            }
            System.out.println(subscriber.getName() + " subscribed to all categories!");
        } else
            System.out.println("Invalid!");
    }

    private void addCategory(Subscriber subscriber) {
        Category.printAllCategories();
        int categoryId = getIntInput("Enter category ID to add: ");
        if (categoryId >= 1 && categoryId <= Category.ALL_CATEGORIES.length) {
            String category = Category.ALL_CATEGORIES[categoryId - 1];
            subscriber.subscribeToCategory(category);
        } else {
            System.out.println("Invalid category ID.");
        }
    }

    private void removeCategory(Subscriber subscriber) {
        List<String> subscribedCategories = subscriber.getSubscribedCategories();
        if (subscribedCategories.isEmpty()) {
            System.out.println(subscriber.getName() + " has no subscribed categories.");
            return;
        }
        System.out.println("\n=== Subscribed Categories ===");
        for (int i = 0; i < subscribedCategories.size(); i++) {
            System.out.println((i + 1) + ". " + subscribedCategories.get(i));
        }
        int choice = getIntInput("Enter category number to remove: ");
        if (choice >= 1 && choice <= subscribedCategories.size()) {
            String category = subscribedCategories.get(choice - 1);
            subscriber.unsubscribeFromCategory(category);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void setCategories(Subscriber subscriber) {
        List<String> newCategories = new ArrayList<>();
        Category.printAllCategories();
        System.out.println("Enter category IDs (comma-separated, e.g., 1,3,5):");
        String input = getStringInput("Categories: ");
        try {
            String[] ids = input.split(",");
            for (String id : ids) {
                int categoryId = Integer.parseInt(id);
                if (categoryId >= 1 && categoryId <= Category.ALL_CATEGORIES.length) {
                    newCategories.add(Category.ALL_CATEGORIES[categoryId - 1]);
                }
            }
            subscriber.setSubscribedCategories(newCategories);
            System.out.println("Categories updated for " + subscriber.getName());
            System.out.println("New categories: " + newCategories);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please use comma-separated numbers.");
        }
    }

    private void listSubscribers() {
        if (subscribers.isEmpty()) {
            System.out.println("There are no subscribers.");
            return;
        }
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber subscriber = subscribers.get(i);
            System.out.println((i + 1) + ". " + subscriber.getName() + " Email: " + subscriber.getEmail() + " Phone Number: " + subscriber.getPhoneNumber());
        }
    }

    private void viewAllArticles() {
        List<Article> articles = newsAgency.getAllArticle();
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }
        SimplePrintVisitor visitor = new SimplePrintVisitor();
        newsAgency.processWithVisitor(visitor);
        System.out.println("\nTotal articles: " + visitor.getCount());
    }

    private void countArticles() {
        List<Article> articles = newsAgency.getAllArticle();
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }
        CounterVisitor visitor = new CounterVisitor();
        newsAgency.processWithVisitor(visitor);
        visitor.printResult();
    }

    private void removeSubscriber() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers available.");
            return;
        }
        listSubscribers();
        int subscriberNumber = getIntInput("Enter subscriber number to remove: ");
        if (subscriberNumber < 1 || subscriberNumber > subscribers.size()) {
            System.out.println("Invalid subscriber number. Must be between 1 and " + subscribers.size());
            return;
        }
        int index = subscriberNumber - 1;
        Subscriber subscriber = subscribers.get(index);
        String name = subscriber.getName();
        subscribers.remove(index);
        newsAgency.removeObserver(subscriber);
        System.out.println("Subscriber '" + name + "' removed successfully.");
    }
}
