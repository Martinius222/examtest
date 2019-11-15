package no.kristiania.eksamenTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private HttpController defaultController;
    private ServerSocket serverSocket;



    public String fileLocation;
    private Map<String, HttpController> controllers = new HashMap<>();


    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        defaultController = new HttpControllerFile(this);

        controllers.put("/echo", new HttpControllerEcho());
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(8081);
        httpServer.setFileLocation("src/main/resources/");
        httpServer.start();

    }

    public void start() {

        new Thread(this::run).start();

    }

    private void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                HttpServerRequest request = new HttpServerRequest(socket.getInputStream());

                String requestLine = request.getStartLine();
                logger.debug("Handling request:{}",requestLine);
                Map<String, String> headers = request.headers;
                String body = request.body;

                String requestAction= requestLine.split(" ")[0];
                String requestTarget = requestLine.split(" ")[1];
                int questionPos = requestTarget.indexOf('?');

                String query = questionPos != -1 ? requestTarget.substring(questionPos + 1) : null;
                String requestPath = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;

                Map<String, String> requestParameters = parseRequestParameters(query);
                controllers.getOrDefault(requestPath, defaultController)
                        .handle(requestAction,requestPath, requestParameters,body, socket.getOutputStream());



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> requestParameters = new HashMap<>();

        if (query != null) {

            for (String parameter : query.split("&")) {
                int equalPos = parameter.indexOf('=');
                String parameterValue = parameter.substring(equalPos + 1);
                String parameterName = parameter.substring(0, equalPos);
                requestParameters.put(parameterName, parameterValue);

            }
        }
        return requestParameters;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    public String getFileLocation() {
        return fileLocation;
    }

    public void addController(String path, HttpController controller) {
        controllers.put(path,controller);
    }

}