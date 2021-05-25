package orm;

import orm.dao.DbExecutorImpl;
import orm.entities.Account;
import orm.entities.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
        dbExecutorUser.createTable(User.class);

        User john = new User(1, "John", 32);
        dbExecutorUser.create(john);
        User loadJohn = dbExecutorUser.load(john.getId(), User.class);
        System.out.println("Load " + loadJohn + "\n");

        User julia = new User(2, "Julia", 27);
        dbExecutorUser.create(julia);
        User loadJulia = dbExecutorUser.load(2, User.class);
        System.out.println("Load " + loadJulia + "\n");

        john.setAge(33);
        dbExecutorUser.update(john);
        loadJohn = dbExecutorUser.load(john.getId(), User.class);
        System.out.println("Load " + loadJohn + "\n");

        dbExecutorUser.closeConnection();



        DbExecutorImpl<Account> dbExecutorAccount = new DbExecutorImpl<>();
        dbExecutorAccount.createTable(Account.class);

        Account account = new Account();
        account.setNo(5L);
        account.setType("premium");
        account.setRest(42);

        dbExecutorAccount.create(account);

        Account loadAccount = dbExecutorAccount.load(account.getNo(), Account.class);
        System.out.println("Load " + loadAccount + "\n");

        account.setType("ordinary");
        dbExecutorAccount.update(account);
        loadAccount = dbExecutorAccount.load(account.getNo(), Account.class);
        System.out.println("Load " + loadAccount);

        dbExecutorAccount.closeConnection();
    }
}
