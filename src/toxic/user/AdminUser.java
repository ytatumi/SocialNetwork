package toxic.user;

import toxic.user.User;

public class AdminUser extends User implements Administration {

    public AdminUser(String name, String email) {
        super(name, email);
    }


    @Override
    public void deleteUser(User user) {
        //need a list of regular users so it can be found the yrrent user and delete it.
    }

    @Override
    public void banUser(User user) {
        //
    }
}