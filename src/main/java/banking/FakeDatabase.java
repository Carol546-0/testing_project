package banking;

import java.util.ArrayList;
import java.util.List;

public class FakeDatabase {
    public static List<User> users = new ArrayList<>();

    static {
        users.add(new Admin("admin", "adminpass", new Account(0)));
        users.add(new Client("client1", "pass1", new Account(1000)));
        users.add(new Client("client2", "pass2", new Account(1000)));
    }

    public static User findUser(String username, String accNum) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getAccount().getAccountNumber().equals(accNum)) {
                return u;
            }
        }
        return null;
    }
}