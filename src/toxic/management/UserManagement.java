package toxic.management;

import toxic.user.AdminUser;
import toxic.user.RegularUser;
import toxic.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserManagement {

    private static volatile UserManagement instance;
    List<User> userList = new ArrayList<>();

    private UserManagement() {
        generateDefaultUser();
    }

    private void generateDefaultUser() {
        User regularUser1 = new RegularUser("Test User", "test.google.com");
        User adminUser = new AdminUser("Admin", "admin.google.com");
        this.userList.add(regularUser1);
        this.userList.add(adminUser);
    }

    public static UserManagement getInstance() {
        if (instance == null) {
            synchronized (UserManagement.class) {
                if (instance == null) {
                    instance = new UserManagement();
                }
            }
        }
        return instance;
    }

    public List<User> getUserList() {
        return userList;
    }
    public void updateUserList(List<User> userList) {
        this.userList = userList;
    }
}
