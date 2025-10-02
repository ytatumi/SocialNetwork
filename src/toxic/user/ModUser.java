package toxic.user;

import toxic.Post;
import toxic.management.PostManagement;
import toxic.management.UserManagement;

import java.util.List;

public class ModUser extends User implements Moderation{

    public ModUser(String name, String email) {
        super(name, email);
    }

    @Override
    public void showReportedPosts() { //prints all reported posts
        int nr = 1;
        System.out.println("Reported post:");
        List<Post> postList = PostManagement.getInstance().getPostList();
        for (Post p : postList) {
            if(p.getReported()){
                System.out.println("#" + (nr++) + " " + p.getMsg());
            }
        }
    }

    @Override
    public void acceptPost(Post post) {
        if(post.getReported()){
            post.report(post.getUser());
            post.setAccepted();
        } else {
            System.out.println("The post is not reported");
        }
    }

    @Override
    public void deletePost(Post post) {
        List<Post> postList = PostManagement.getInstance().getPostList();
        postList.remove(post);
        PostManagement.getInstance().updatePostList(postList);
    }


    @Override
    public void banUser(User user) {
        List<User> userList = UserManagement.getInstance().getUserList();

        if(user instanceof RegularUser) {
            for (User u : userList) {
                if (u.equals(user)) {
                    u.setBanned(true);
                    break;
                }
            }
            System.out.println("Admin " + this.name + " has banned " + user.getName());
        } else {
            System.out.println("Admin can not be banned");
        }
        UserManagement.getInstance().updateUserList(userList);
    }
}
