package Strategy;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http;

import java.util.UUID;

public class JsonUuid implements Uuid {

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
