package no.kristiania.eksamenTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpServerTest {

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.start();
    }

    @Test
    void shouldGet200StatusCode() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        assertEquals(200, client.execute("GET").getStatusCode());

    }

    @Test
    void shouldReturnStatusCode401() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
        assertEquals(401, client.execute("GET").getStatusCode());
    }

    @Test
    void shouldGetRequestedStatusCode() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
        HttpClientResponse response = client.execute("GET");
        assertEquals(401, response.getStatusCode());
    }

    @Test
    void shouldReturnResponseHeader() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),
                "/echo?status=302&location=http://example.com");
        HttpClientResponse response = client.execute("GET");
        assertEquals(302, response.getStatusCode());
        assertEquals("http://example.com", response.getHeader("location"));
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?body=helloWorld!");
        assertEquals("helloWorld!", client.execute("GET").getBody());
    }



    @Test
    void shouldReturnFileFromDisk() throws IOException {
        String text = "Hello Kristiania";
        Files.writeString(Paths.get("target/myfile.txt"), text);
        server.setFileLocation("target");
        HttpClient client = new HttpClient("localhost", server.getPort(), "/myfile.txt");
        HttpClientResponse response = client.execute("GET");
        assertEquals(text, response.getBody());
    }

    @Test
    void shouldRun2echorequest() throws IOException {
        String text = "Hello Kristiania";
        Files.writeString(Paths.get("target/myfile.txt"), text);
        server.setFileLocation("target");
        HttpClient client = new HttpClient("localhost", server.getPort(), "/myfile.txt");
        HttpClientResponse response = client.execute("GET");
        assertEquals(text, response.getBody());
        HttpClientResponse response2 = client.execute("GET");
        assertEquals(text, response2.getBody());

    }@Test
    void shouldPostParameters() throws IOException {
        String formbody="content-type=text/html&body=foobar";
        HttpClient client = new HttpClient("localhost",server.getPort(),"/echo?"+formbody);
        client.setRequestHeader("content-type","application/x-www-urlencoded");
        client.setBody(formbody);
        HttpClientResponse response= client.execute("POST");
        System.out.println();
        assertThat(response.getBody()).isEqualTo("foobar");
    }

}
