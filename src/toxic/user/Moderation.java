package toxic.user;

import toxic.Post;

public interface Moderation {

public void showReportedPosts();
public void acceptPost(Post post);
public void deletePost(Post post);

}