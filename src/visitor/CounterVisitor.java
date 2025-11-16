package visitor;

import model.Article;

public class CounterVisitor implements ArticleVisitor {
    private int highConternCount = 0;
    private int mediumContentCount = 0;
    private int lowConternCount = 0;



    @Override
    public void visit(Article article) {
        String priority = article.getPriority();
        if (priority.equals("high")) {
            highConternCount++;
        }else if (priority.equals("medium")) {
            mediumContentCount++;
        }else if (priority.equals("low")) {
            lowConternCount++;
        }

    }

    public void printResult() {
        System.out.println("ARTICLE COUNT:");
        System.out.println("HIGH:   " + highConternCount);
        System.out.println("MEDIUM: " + mediumContentCount);
        System.out.println("LOW:    " + lowConternCount);
        System.out.println("TOTAL:  " + (highConternCount + mediumContentCount + lowConternCount));
    }
}
