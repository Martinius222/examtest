package no.kristiania.eksamenTest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface HttpController {
    void handle(String requestAction, String requestPath, Map<String, String> requestParameters, String body, OutputStream outputStream) throws IOException;
}
