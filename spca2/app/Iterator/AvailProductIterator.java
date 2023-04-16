package Iterator;

import models.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AvailProductIterator implements ProductIterator{

    private final Iterator<Product> productIterator;

    public AvailProductIterator(List<Product> products) {
        List<Product> activeProducts = new ArrayList<>();

        for (Product product : products){
            if (product.getStockLevel() > 0){
                activeProducts.add(product);
            }
        }

        this.productIterator = activeProducts.iterator();
    }

    public boolean hasNext() {
        return productIterator.hasNext();
    }

    public Product next() {
        return productIterator.next();
    }

    public List<Product> getActiveProducts() {
        List<Product> activeProducts = new ArrayList<>();

        while (hasNext()) {
            activeProducts.add(next());
        }

        return activeProducts;
    }
}
