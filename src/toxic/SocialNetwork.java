package toxic;

import java.util.ArrayList;
import java.util.Scanner;

public class SocialNetwork {
    ArrayList<Post> listOfPosts;

    public SocialNetwork() {
        this.listOfPosts = new ArrayList<>();
    }

    public void run() {
        Scanner scn = new Scanner(System.in);
        selectMenu(scn, user);
    }

    public void selectMenu(Scanner scn, User user) {
        int selected = -1;
        while (selected != 99) {
            if (user instanceof RegularUser) {
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
                        user.showReportedPosts();
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
        for (post: listOfPosts) {
            System.out.printf(" (%d)Likes  [ %s ] : %s ", post.getLikes().size(), post.getUser(), post.getMsg());
        }
    }
}



