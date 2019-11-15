package no.kristiania.eksamenTest;

import java.io.IOException;
import java.io.InputStream;

public class HttpClientResponse extends HttpMessage {

    public HttpClientResponse(InputStream inputStream) throws IOException {
        super(inputStream);

    }

    public int getStatusCode() {
        return Integer.parseInt(startLine.split(" ")[1]);

    }

    public String getBody() {
        return body;
    }
}

