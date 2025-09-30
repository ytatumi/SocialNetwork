package toxic.user;

import toxic.Post;

public interface Moderation {

    void showReportedPosts();
    void acceptPost(Post post);
    void deletePost(Post post);
    void banUser(User user);
}