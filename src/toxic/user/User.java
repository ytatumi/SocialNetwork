package toxic.user;

import toxic.Post;

public abstract class User {
 String name;
 String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Post createPost(String msg) {
        return null;
    }
    public Post reportPost(Post post) {
        return null;
    }

    public String getName() {
        return name;
    }

}