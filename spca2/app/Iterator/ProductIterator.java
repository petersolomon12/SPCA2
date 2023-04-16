package Iterator;

import models.Product;

import java.util.Iterator;
import java.util.List;

public interface ProductIterator {

    boolean hasNext();
    Product next();
}
