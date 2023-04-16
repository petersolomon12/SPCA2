package Visitor;

import models.Product;

public interface ProductVisitor {
    void visit(Product product);
}