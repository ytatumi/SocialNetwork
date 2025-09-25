package toxic.common;//Likeable interface

// + like(), unlike()

import toxic.user.User;

public interface Likeable {

    void like(User user);

    void unLike(User user);

}