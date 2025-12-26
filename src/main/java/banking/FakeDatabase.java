package banking;

import java.util.ArrayList;
import java.util.List;

public class FakeDatabase {

    public static List<User> users = new ArrayList<>();

    static {
        Account acc1 = new Account(1000);
        Account acc2 = new Account(2000);

        users.add(new Admin("admin", "adminpass", acc1));
        users.add(new Client("client1", "clientpass", acc2));
    }
}
