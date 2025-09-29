package toxic.user;

import toxic.Post;

public abstract class User {
 String name;
 String email;
 boolean banned;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        banned = false;
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

    public String getEmail() {
        return email;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    public boolean getBanned(){
        return banned;
    }
}