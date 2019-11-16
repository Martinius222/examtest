package no.kristiania.eksamenTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MissionObjectiveDao extends AbstractDao<MissionObjective> {
    public MissionObjectiveDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void insertMember(MissionObjective task, PreparedStatement statement) throws SQLException {
        statement.setString(1, task.getName());
        statement.setLong(2, task.getId());

    }

    @Override
    protected MissionObjective readObject(ResultSet resultSet) throws SQLException {

        MissionObjective task = new MissionObjective();

        task.setName(resultSet.getString(2));
        task.setId(resultSet.getInt(1));
        return task;
    }



    public List<MissionObjective> listAll() throws SQLException {
        return listAll("select * from tasks");
    }

    public long insert(MissionObjective task) throws SQLException {
        long id = insert(task, "insert into tasks (task_name,status_id) values (?,?)");
        task.setId((int) id);
        return id;
    }
    public MissionObjective retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from tasks where id = ?")) {
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
