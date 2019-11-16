package no.kristiania.eksamenTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OperationMemberDao extends AbstractDao<OperationMember> {

    public OperationMemberDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void insertMember(OperationMember member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getRole());

    }

    @Override
    protected OperationMember readObject(ResultSet resultSet) throws SQLException {
        OperationMember member = new OperationMember();

        member.setId(resultSet.getInt(1));
        member.setName(resultSet.getString(2));
        member.setRole(resultSet.getString(3));
        return member;
    }


    public long insert(OperationMember projectMember) throws SQLException {
        long id = insert(projectMember, "insert into operationmembers (name,role) values (?,?)");
        projectMember.setId(id);
        return id;
    }

    public List<OperationMember> listAll() throws SQLException {
        return listAll("select * from operationmembers");
    }

    public OperationMember retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from operationmembers where id = ?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return (readObject(resultSet));
                    } else {
                        return null;
                    }
                }
            }
        }
    }


}
