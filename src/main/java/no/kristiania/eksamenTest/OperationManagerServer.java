package no.kristiania.eksamenTest;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class OperationManagerServer {
    public static void main(String[] args) throws IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();


        Properties properties = new Properties();
        try(FileReader reader = new FileReader("task-manager.properties")){
            properties.load(reader);
        }
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

//MUCH TOO TAKE CARE OF HERE
        HttpServer server = new HttpServer(8080);
        server.setFileLocation("src/main/resources/"); //REMEMBER TO CHANGE /taskmanager after resources
        server.addController("/api/projectMembers", new HttpControllerOperationMember(new OperationMemberDao(dataSource)));
        // server.addController("/api/project", new ProjectHttpController(new ProjectDao(dataSource)));
        // server.addController("/api/status", new StatusHttpController(new StatusDao(dataSource)));
        server.addController("/api/tasks", new HttpControllerMissionObjective(new MissionObjectiveDao(dataSource)));

        server.start();
    }
}
