package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS Task1( " +
            "id INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, " +
            "name VARCHAR NOT NULL, " +
            "lastName VARCHAR NOT NULL, " +
            "age INTEGER NOT NULL)";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS Task1";
    private static final String SET_VALUES = "INSERT INTO Task1(name, lastname, age) VALUES (?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM Task1 WHERE id = ?";
    private static final String SELECT_FROM = "SELECT * FROM Task1";
    private static final String CLEAN_USER_TABLE = "TRUNCATE TABLE Task1";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.executeUpdate(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.executeUpdate(DROP_USERS_TABLE);
            System.out.println("Таблица удалена!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SET_VALUES);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Юзер по id успешно удален!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            ResultSet resultSet = statement.executeQuery(SELECT_FROM);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.executeUpdate(CLEAN_USER_TABLE);
            System.out.println("Все пользователи удалены!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
