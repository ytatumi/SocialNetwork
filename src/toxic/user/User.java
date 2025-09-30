package toxic.user;

import toxic.Post;
import toxic.management.PostManagement;

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
        Post newPost = new Post(this, msg);
        PostManagement.getInstance().addPost(newPost);
        return newPost;
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