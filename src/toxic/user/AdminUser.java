package toxic.user;

import toxic.management.UserManagement;

import java.util.List;

public class AdminUser extends User implements Administration {

    public AdminUser(String name, String email) {
        super(name, email);
    }


    @Override
    public void deleteUser(User user) {
        List<User> userList = UserManagement.getInstance().getUserList();

        if(user instanceof RegularUser){
            userList.remove(user);
            System.out.println("Admin " + this.name + " has deleted " + user.getName());
        } else {
            System.out.println("Admin can not be deleted");
        }
        UserManagement.getInstance().updateUserList(userList);
    }

    @Override
    public void banUser(User user) {
        List<User> userList = UserManagement.getInstance().getUserList();

        if(user instanceof RegularUser) {
            for (User u : userList) {
                if (u.equals(user)) {
                    u.setBanned(true);
                    break;
                }
            }
            System.out.println("Admin " + this.name + " has banned " + user.getName());
        } else {
            System.out.println("Admin can not be banned");
        }
        UserManagement.getInstance().updateUserList(userList);
    }
}