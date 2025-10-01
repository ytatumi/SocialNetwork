package toxic.user;

import java.util.List;

public interface Administration {

    void deleteUser(User user);
    void showBannedUsers(List<User> users);

}