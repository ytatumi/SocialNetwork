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

    public void init() {
        User regularUser1 = new RegularUser("Test User", "test.google.com");
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
            displayMainMenu(user);
            System.out.println("Please type the number what you would like to do.");
            selected = Integer.parseInt(scn.nextLine());
            switch (selected) {
                case 1:
                    showPosts();
                    break;
                case 2:
                    System.out.println("Please type the message");
                    String message = scn.nextLine();
                    Post post =user.createPost(message);
                    listOfPosts.add(post);
                case 3:
                    if (user instanceof AdminUser || user instanceof ModUser) {
                        user.showReportedPosts();
                    }
                    break;
                case 99:
                    break;
            }
        }
    }

    public void displayMainMenu(User user) {
        displayMainMenuHeader();
        System.out.println("1: View all posts");
        System.out.println("2: Create a post");
        if (user instanceof AdminUser || user instanceof ModUser){
            System.out.println("3: View all reported posts");
        }
        displayMainMenuFooter();
    }

    public void displayMainMenuHeader() {
        System.out.println("");
        System.out.println("====== MENU  ======");
    }

    public void displayMainMenuFooter() {
        System.out.println("99:Exit Program");
    }

    public void showPosts() {
        System.out.println("======= ALL POSTS ========");
        for (Post post : listOfPosts) {
            System.out.printf(" (%d)Likes  [ %s ] : %s \n", post.getLikes().size(), post.getUser().getName(), post.getMsg());
        }
    }

    /*
    public void showReportedPosts() {
        System.out.println("======= ALL POSTS ========");
        for (Post post : listOfPosts) {
            if (post.reported){
                System.out.printf(" (%d)Likes  [ %s ] : %s \n", post.getLikes().size(), post.getUser().getName(),
                        post.getMsg());
            }
        }
    }
    */
}



