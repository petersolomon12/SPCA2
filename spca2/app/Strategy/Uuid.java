package Strategy;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http;

import java.util.UUID;

public interface Uuid {
    UUID getUuid(Http.Request request);

    UUID getProductUuid(Http.Request request);
}

