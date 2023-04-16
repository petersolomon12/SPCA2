package Command;

import models.Product;

public interface Command {
    void execute() throws Exception;
}