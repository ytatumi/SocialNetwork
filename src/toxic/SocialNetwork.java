package toxic;
import toxic.user.RegularUser;
import toxic.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocialNetwork {
    List<Post> listOfPosts;
    List<User> listOfUsers;



    public SocialNetwork() {
        this.listOfPosts = new ArrayList<>();
        this.listOfUsers = new ArrayList<User>();
        init();
    }

    public void init(){
        User regularUser1 = new RegularUser("Test User",  "test.google.com");
        listOfUsers.add(regularUser1);
        Post testPost = new Post(regularUser1, "test message");
        listOfPosts.add(testPost);
    }

    public void run() {
        Scanner scn = new Scanner(System.in);
        selectMenu(scn, listOfUsers.get(0));
    }

    public void selectMenu(Scanner scn, User user) {
        int selected = -1;
        while (selected != 99) {
            if (user instanceof User) {
            //if (user instanceof RegularUser) {
                displayMainMenu();
                System.out.println("Please type the number what you would like to do.");
                selected = scn.nextInt();
                switch (selected) {
                    case 1:
                        showPosts();
                        break;
                    case 99:
                        break;
                }
            } else {
                displayAdminMainMenu();
                System.out.println("Please type the number what you would like to do.");
                selected = scn.nextInt();
                switch (selected) {
                    case 1:
                        showPosts();
                        break;
                    case 2:
                       //user.showReportedPosts();
                        break;
                    case 99:
                        break;
                }
            }


        }

    }

    public void displayMainMenu() {
        System.out.println("");
        System.out.println("====== MENU ======");
        System.out.println("1: View all posts");
        System.out.println("99:Exit Program");
    }

    public void displayAdminMainMenu() {
        System.out.println("");
        System.out.println("====== MENU  ======");
        System.out.println("1: View all posts");
        System.out.println("2: View all reported posts");
        System.out.println("99:Exit Program");
    }

    public void showPosts() {
        System.out.println("======= ALL POSTS ========");
        for (Post post: listOfPosts) {
            System.out.printf(" (%d)Likes  [ %s ] : %s \n", post.getLikes().size(), post.getUser().getName(), post.getMsg());
        }
    }
}



