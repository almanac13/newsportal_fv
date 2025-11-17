package visitor;

import model.Article;

public class SimplePrintVisitor implements ArticleVisitor {

    private int count = 0;

    @Override
    public void visit(Article article) {
        count++;
        System.out.println("Article " + count + ": " + article.getTitle() + " Priority: " + article.getPriority());

    }

    public int getCount() {
        return count;
    }
}
