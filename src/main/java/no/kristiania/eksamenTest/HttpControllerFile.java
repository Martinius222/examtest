package no.kristiania.eksamenTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpControllerFile implements  HttpController {
    private HttpServer httpServer;

    public HttpControllerFile(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> requestParameters, String body, OutputStream outputStream) throws IOException {
        File file = new File(httpServer.fileLocation + requestPath);
        if (file.isDirectory()) {
            file = new File(file, "index.html");
        }
        if (file.exists()) {
            outputStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-length: " + file.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());
            new FileInputStream(file).transferTo(outputStream);

        } else {
            outputStream.write(("HTTP/1.1 404 OK\r\n" +
                    "Connection: close\r\n" +
                    "\r\n").getBytes());

        }
    }
}
