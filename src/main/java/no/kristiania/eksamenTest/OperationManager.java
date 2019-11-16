package no.kristiania.eksamenTest;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class OperationManager {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("Enter an operation member: ");
        String inputName = new Scanner(System.in).nextLine();

        System.out.println("Enter the role of the operation member (For example: Sith, Stormtrooper or any other) " + inputName + ": ");
        String inputRole = new Scanner(System.in).nextLine();  //previously inputMail

        Properties properties = new Properties();
        properties.load(new FileReader("task-manager.properties"));

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        OperationMemberDao memberDao = new OperationMemberDao(dataSource);

        OperationMember member = new OperationMember(); //ProjectMember member = new ProjectMember();
        member.setName(inputName); //member.setName(inputName);
        member.setRole(inputRole);  //member.setMail(inputMail);
        memberDao.insert(member); //memberDao.insert(member);
        System.out.println(memberDao.listAll()); //System.out.println(memberDao.listAll());
    }

}
