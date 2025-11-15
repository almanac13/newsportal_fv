package ui;

import agency.NewsAgency;
import factory.NotificationStrategyFactory;
import model.Article;
import model.Category;
import model.Subscriber;
import observer.Observer;
import strategy.NotificationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleMenu {
    private NewsAgency newsAgency = new NewsAgency();
    private Scanner scanner = new Scanner(System.in);
    private List<Subscriber> subscribers = new ArrayList<>();


    // Method: start()
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

        System.out.println("1.Email 2.SMS 3.Push");
        int choice = getIntInput("Enter your choice: ");
        String strategyType = "";
        if (choice == 1) {
            strategyType ="email";
        }
        else if (choice == 2) {
            strategyType ="sms";
        }
        else if (choice == 3) {
            strategyType ="push";
        }

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

        Article article = new Article(title, content, category, priority);
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
        System.out.println("1. Email 2.SMS 3.Push");

        int choice = getIntInput("Select notification method (1-3): ");
        String strategyType;
        switch (choice) {
            case 1:
                strategyType = "email";
                break;
            case 2:
                strategyType = "sms";
                break;
            case 3:
                strategyType = "push";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        try {
            NotificationStrategy newStrategy = NotificationStrategyFactory.createNotificationStrategy(strategyType);
            subscriber.setNotificationStrategy(newStrategy);
            System.out.println("Notification method updated successfully for " + subscriber.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void manageCategories() {
        // TODO: Implement
        System.out.println("Manage Categories - Coming soon!");
    }

    private void listSubscribers() {
        if (subscribers.size() == 0) {
            System.out.println("There are no subscribers.");
            return;
        }else {
            for (int i=0; i<subscribers.size(); i++) {
                Subscriber subscriber = subscribers.get(i);
                System.out.println((i+1)+ "."+ subscriber.getName()+ "Email: " + subscriber.getEmail() + "Phone Number:" + subscriber.getPhoneNumber());
            }
        }
    }

    private void viewAllArticles() {
        // TODO: Implement
        System.out.println("View All Articles - Coming soon!");
    }

    private void countArticles() {
        // TODO: Implement
        System.out.println("Count Articles - Coming soon!");
    }

    private void removeSubscriber() {
        // TODO: Implement
        System.out.println("Remove Subscriber - Coming soon!");
    }
}