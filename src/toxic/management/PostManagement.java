package toxic.management;

import toxic.Post;

import java.util.ArrayList;
import java.util.List;

public class PostManagement {

    private static volatile PostManagement instance;
    List<Post> postList;

    private PostManagement() {
        postList  = new ArrayList<>();
    }


    public static PostManagement getInstance() {
        if (instance == null) {
            synchronized (PostManagement.class) {
                if (instance == null) {
                    instance = new PostManagement();
                }
            }
        }
        return instance;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void updatePostList(List<Post> postList) {
        this.postList = postList;
    }
}
