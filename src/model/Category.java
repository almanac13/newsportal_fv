package model;

import java.util.List;

public class Category {
    public static final String FINANCE = "Finance";
    public static final String TECH = "Technology";
    public static final String SCIENCE = "Science";
    public static final String SPORTS = "Sports";
    public static final String POLICTICS = "Politics";
    public static final String HEALTH = "Health";
    public static final String WEATHER = "Weather";

    public static final String[] ALL_CATEGORIES = {
            FINANCE, TECH, SCIENCE, SPORTS, POLICTICS,
            HEALTH, WEATHER};

    public static void printAllCategories() {
        for (int i = 0; i < ALL_CATEGORIES.length; i++) {
            System.out.println((i+1) + "." + ALL_CATEGORIES[i]);
        }
    }


}
