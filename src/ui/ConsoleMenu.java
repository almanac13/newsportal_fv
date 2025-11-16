package ui;

import agency.NewsAgency;
import factory.NotificationStrategyFactory;
import model.Article;
import model.ArticleBuilder;
import model.Category;
import model.Subscriber;
import strategy.NotificationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("=== MENU ===");
        System.out.println("1. Add Subscriber");
        System.out.println("2. Publish Article");
        System.out.println("3. Change Notification Method");
        System.out.println("4. Manage Categories");
        System.out.println("5. List Subscribers");
        System.out.println("6. View All Articles");
        System.out.println("7. Count Articles");
        System.out.println("8. Remove Subscriber");
        System.out.println("0. Exit");
    }

    // getStringInput
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // getIntInput
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Empty method stubs (to be filled later)
    private void addSubscriber() {
        System.out.println("Add new Subscriber");
        String name = getStringInput("Name:");
        String email = getStringInput("Email:");
        String phoneNumber = getStringInput("Phone number:");
        String deviceId = getStringInput("Device ID:");

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
        System.out.println("subscriber added.");

    }

    private void publishArticle() {
        System.out.println("Publish new article");
        String title = getStringInput("Title:");
        String content = getStringInput("Content:");
        Category.printAllCategories();
        int categoryId = getIntInput("Category ID:");

        String category;
        if (categoryId >=1 && categoryId <= Category.ALL_CATEGORIES.length) {
            category = Category.ALL_CATEGORIES[categoryId - 1];
        }else {
            System.out.println("Invalid category ID. Please enter a valid category ID.");
            category = " ";
        }

        System.out.println("Priority: \n" +
                "1.HIGH 2.MEDIUM 3.LOW");
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

        System.out.println("published.");
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

        if(subscribers.isEmpty()) {
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
        int choice = getIntInput("choice: ");
        if (choice == 1)
            addCategory(subscriber);
        else if (choice == 2)
            removeCategory(subscriber);
        else if (choice == 3)
            setCategories(subscriber);
        else if (choice == 4) {
            // Subscribe to all categories
            for (String category : Category.ALL_CATEGORIES) {
                subscriber.subscribeToCategory(category);
            }
            System.out.println(subscriber.getName() + " subscribed to all categories!");
        }
        else System.out.println("Invalid!");
    }

    private void setCategories(Subscriber subscriber) {
        List<String> newCategories = new ArrayList<>();
        Category.printAllCategories();

        System.out.println("Enter category IDs (comma-separated, e.g., 1,3,5):");
        String input = getStringInput("Categories: ");

        try {
            String[] ids = input.split(",");
            for (String id : ids) {
                int categoryId = Integer.parseInt(id.trim());
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



    private void listSubscribers() {
        if (subscribers.size() == 0) {
            System.out.println("There are no subscribers.");
            return;
        }else {
            for (int i=0; i<subscribers.size(); i++) {
                Subscriber subscriber = subscribers.get(i);
                System.out.println((i+1)+ "."+ subscriber.getName()+ " Email: " + subscriber.getEmail() + " Phone Number:" + subscriber.getPhoneNumber());
            }
        }
    }

    private void viewAllArticles() {
        // 1. Get all articles from agency
        List<Article> articles = newsAgency.getAllArticle();

        // 2. Check if empty
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }
        System.out.println("Articles:");

        // 4. Loop through articles with index
        for (int i = 0; i < articles.size(); i++) {
            Article a = articles.get(i);
            System.out.println((i + 1) + ". " + a.getTitle() + " (" + a.getCategory() + ")");
        }

    }

    private void countArticles() {
        // 1. Get all articles
        List<Article> articles = newsAgency.getAllArticle();

        // 2. Check if empty
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }

        // 3. Create counters (all = 0)
        int highCount = 0;
        int mediumCount = 0;
        int lowCount = 0;

        // 4. Loop through articles
        for (Article article : articles) {
            // 5. Check priority and increment appropriate counter
            String priority = article.getPriority();

            if (priority.equalsIgnoreCase("High")) {
                highCount++;
            } else if (priority.equalsIgnoreCase("Medium")) {
                mediumCount++;
            } else if (priority.equalsIgnoreCase("Low")) {
                lowCount++;
            }
        }

        // 6. Print report showing all counts
        System.out.println("========== ARTICLE COUNT BY PRIORITY ==========");
        System.out.println("High Priority:   " + highCount + " articles");
        System.out.println("Medium Priority: " + mediumCount + " articles");
        System.out.println("Low Priority:    " + lowCount + " articles");
        System.out.println("-----------------------------------------------");
        System.out.println("Total Articles:  " + articles.size() + " articles");
    }


    private void removeSubscriber() {
        // Check if subscriberList empty
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers available.");
            return;
        }

        // List subscribers
        listSubscribers();

        // Get subscriber number from user
        int subscriberNumber = getIntInput("Enter subscriber number to remove: ");

        // Validate index (must be 1 to size)
        if (subscriberNumber < 1 || subscriberNumber > subscribers.size()) {
            System.out.println("Invalid subscriber number. Must be between 1 and " + subscribers.size());
            return;
        }

        // Get the subscriber (convert to 0-based index)
        int index = subscriberNumber - 1;
        Subscriber subscriber = subscribers.get(index);

        // Confirm removal
        String name = subscriber.getName();

        // 1. Remove from subscriberList
        subscribers.remove(index);

        // 2. Unregister from agency
        newsAgency.removeObserver(subscriber);

        System.out.println("Subscriber '" + name + "' removed successfully.");
    }



}