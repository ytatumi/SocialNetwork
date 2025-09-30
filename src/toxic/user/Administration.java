package toxic.user;

import java.util.List;

interface Administration {

    void deleteUser(User user);
    void showBannedUsers(List<User> users);

}