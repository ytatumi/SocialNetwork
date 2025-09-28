package toxic;

import toxic.user.AdminUser;
import toxic.user.ModUser;
import toxic.user.RegularUser;
import toxic.user.User;

import java.util.*;

public class SocialNetwork {
    List<Post> listOfPosts;
    List<User> listOfUsers;


    public SocialNetwork() {
        this.listOfPosts = new ArrayList<>();
        this.listOfUsers = new ArrayList<>();
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
        User user = getCurrentUser(scn, listOfUsers);
        selectMenu(scn, user);
        // selectMenu(scn, listOfUsers.get(0));
    }

    public void selectMenu(Scanner scn, User user) {
        int selected = -1;
        while (selected != 99) {
            displayMainMenu(user);
            System.out.println("Please type the number what you would like to do. ");
            selected = Integer.parseInt(scn.nextLine());
            switch (selected) {
                case 1:
                    showPosts();
                    break;
                case 2:
                    System.out.println("Please type the message. ");
                    String message = scn.nextLine();
                    Post post = user.createPost(message);
                    listOfPosts.add(post);
                case 3:
                    if (user instanceof ModUser) {
                        ((ModUser) user).showReportedPosts();
                    }
                    if (user instanceof AdminUser) {
                        ((AdminUser) user).showReportedPosts();
                    }
                    break;
                case 99:
                    break;
            }
        }
    }

    public User getCurrentUser(Scanner scn, List<User> listOfUsers) {
        Map<String, String> userInfo = getUserInfo(scn);
        String inputEmail = userInfo.get("email");
        String inputName = userInfo.get("name");
        User currentUser = searchUser(scn, inputEmail, listOfUsers);
        while (currentUser == null) {
            System.out.println("Please type number of your role. 1) Regular User 2) Moderator 3)Administrator");
            int role = Integer.parseInt(scn.nextLine());
            switch (role) {
                case 1:
                    currentUser = new RegularUser(inputName, inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                case 2:
                    currentUser = new ModUser(inputName,inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                case 3:
                    currentUser = new AdminUser(inputName, inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                default:
                    break;
            }
        }
        return currentUser;
    }

    public Map<String, String> getUserInfo(Scanner scn){
        HashMap<String, String> userInfo = new HashMap<>();
        System.out.println("Please type your name: ");
        String inputName = scn.nextLine().trim();
        System.out.println("Please type your email address :");
        String inputEmail = scn.nextLine().trim();
        userInfo.put("name", inputName);
        userInfo.put("email", inputEmail);
        return userInfo;
    }

    public User searchUser(Scanner scn, String inputEmail, List<User> listOfUsers) {
        for (User user : listOfUsers) {
            if (user.getEmail().equals(inputEmail)) {
                return user;
            }
        }
        return null;
    }
    /*
    public User createUser(Scanner scn, String name, String email){
        User newUser = null;
        while(newUser==null){
            System.out.println("Please type number of your role. 1) Regular User 2) Moderator 3)Administrator");
            int role =Integer.parseInt(scn.nextLine());
            switch(role){
                case 1:
                    newUser = new RegularUser(inputName, inputEmail);
                    break;
                /*
                case 2:
                    ModUser newUser = new ModUser(inputName,inputEmail);
                case 3:
                    AdminUser newUser = new AdminUser(inputName, inputEmail); */
        /*
                default:
                    break;
            }
            return newUser;

        }

    }  */

    public void displayMainMenu(User user) {
        displayMainMenuHeader();
        System.out.println("1: View all posts");
        System.out.println("2: Create a post");
        if (user instanceof AdminUser || user instanceof ModUser) {
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



