package toxic.user;

import toxic.management.UserManagement;

import java.util.List;

public class AdminUser extends ModUser implements Administration {

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
    public void showBannedUsers(List<User> users){
        System.out.println();
        System.out.println("=====Banned Users=====");
        StringBuilder sb;
        for(int i = 0; i < users.size(); i++){
            sb = new StringBuilder();
            if(users.get(i).getBanned()){
                sb.append(i + 1).append(" name: ").append(users.get(i).getName())
                        .append(" email: ").append(users.get(i).getEmail());
                System.out.println(sb.toString());
            }
        }
    }

}