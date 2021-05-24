package orm;

import orm.dao.DbExecutorImpl;
import orm.entities.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();

        dbExecutorUser.createTable(User.class);

        User john = new User(1, "John", 32);
        dbExecutorUser.create(john);
        User loadUser = dbExecutorUser.load(john.getId(), User.class);
        System.out.println("Load " + loadUser + "\n");

        User julia = new User(2, "Julia", 27);
        dbExecutorUser.create(julia);
        User loadJulia = dbExecutorUser.load(2, User.class);
        System.out.println("Load " + loadJulia);

        dbExecutorUser.closeConnection();
    }
}
