package Strategy;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http;

import java.util.UUID;

public class JsonUuid implements Uuid {
    private static JsonUuid instance;

    private JsonUuid() {}

    //Added Singleton to create one instance
    public static synchronized JsonUuid getInstance() {
        if (instance == null) {
            instance = new JsonUuid();
        }
        return instance;
    }

    @Override
    public UUID getUuid(Http.Request request) {
        JsonNode body = request.body().asJson();
        String id = body.get("uuid").asText();
        return UUID.fromString(id);
    }

    @Override
    public UUID getProductUuid(Http.Request request) {
        JsonNode body = request.body().asJson();
        String id = body.get("productUuid").asText();
        return UUID.fromString(id);
    }
}