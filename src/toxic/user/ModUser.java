import toxic.Post;
import toxic.SocialNetwork;
import toxic.user.User;

import java.util.List;

public class ModUser extends User {
    List<Post> listOfPosts;
    List<User> listOfUsers;
    List<Post> ReportedPosts;

    public ModUser(String name, String email) {
        super(name, email);
    }

    public void showReportedPosts() {
        int nr = 1;
        System.out.println("Reported post:");
        for (Post p : ReportedPosts) {
            System.out.println("#" + (nr++) + " " + p.getMsg());
        }
    }

    public void acceptPost(Post post) {
        ReportedPosts.remove(post);
    }

    public void deletePost(Post post) {
        listOfPosts.remove(post);
    }

    public void deleteUser(User user) {
        listOfUsers.remove(user);
    }

    public void banUser(User user) {
        user.setBanned(true);
    }

}