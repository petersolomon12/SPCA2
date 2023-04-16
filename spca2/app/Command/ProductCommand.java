package Command;

import models.Product;

public interface ProductCommand {

    Product execute() throws Exception;

}
