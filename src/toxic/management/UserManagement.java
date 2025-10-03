package toxic.management;

import toxic.user.AdminUser;
import toxic.user.ModUser;
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
        User regularUser = new RegularUser("Regular User", "regular@toxic.com");
        User modUser = new ModUser("Mod User", "moderator@toxic.com");
        User adminUser = new AdminUser("Admin User", "admin@toxic.com");
        this.userList.add(regularUser);
        this.userList.add(modUser);
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
