package toxic.user;

import toxic.Post;
import toxic.SocialNetwork;
import toxic.user.User;

import java.util.List;

public class ModUser extends User implements Moderation{
    List<Post> listOfPosts;
    List<User> listOfUsers;
    List<Post> ReportedPosts;

    public ModUser(String name, String email) {
        super(name, email);
    }

    @Override
    public void showReportedPosts() { //prints all reported posts
        int nr = 1;
        System.out.println("Reported post:");
        for (Post p : ReportedPosts) {
            System.out.println("#" + (nr++) + " " + p.getMsg());
        }
    }

    @Override
    public void acceptPost(Post post) {
        ReportedPosts.remove(post);
    }

    @Override
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
