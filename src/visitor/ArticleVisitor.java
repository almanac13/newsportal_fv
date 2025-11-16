package visitor;

import model.Article;

public interface ArticleVisitor {
    void visit(Article article);
}
