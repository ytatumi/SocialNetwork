package toxic;

import toxic.management.PostManagement;
import toxic.management.UserManagement;
import toxic.user.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SocialNetwork {

    public SocialNetwork() {}

    public void run() {
        Scanner scn = new Scanner(System.in);
        User user = getCurrentUser(scn, UserManagement.getInstance().getUserList());
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
                case 0:
                    getCurrentUser(scn, UserManagement.getInstance().getUserList());
                    break;
                case 1:
                    showPosts();
                    break;
                case 2:
                    System.out.println("Please type the message. ");
                    String message = scn.nextLine();
                    Post post = user.createPost(message);
                    break;
                case 3:
                    showPosts();
                    /* System.out.println("Please choose the id for message to report. ");
                    int id = Integer.parseInt(scn.nextLine()); */
                    System.out.println("Please copy the message to report. ");
                    String msgToReport = scn.nextLine().trim();
                    Post postToReport =searchPost(msgToReport, PostManagement.getInstance().getPostList());
                    user.reportPost(postToReport);
                case 4:
                    if (user instanceof ModUser || user instanceof AdminUser) {
                        //((ModUser) user).showReportedPosts();
                        //showReportedPosts();
                        ((ModUser)user).showReportedPosts();
                    }
                    break;
                case 5:
                    if (user instanceof AdminUser) {
                        displayUserManagement(scn, user);
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
        User currentUser = searchUser(inputEmail, listOfUsers);
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

    public User searchUser(String inputEmail, List<User> listOfUsers) {
        for (User user : listOfUsers) {
            if (user.getEmail().equals(inputEmail)) {
                return user;
            }
        }
        return null;
    }

    public Post searchPost(String inputMsg, List<Post> listOfPosts) {
        for (Post post : listOfPosts) {
            if (post.getMsg().equals(inputMsg)) {
                return post;
            }
        }
        return null;
    }

    public void displayMainMenu(User user) {
        displayMainMenuHeader();
        System.out.println("0: Create a User");
        System.out.println("1: View all posts");
        System.out.println("2: Create a post");
        System.out.println("3: Report a post");
        if (user instanceof AdminUser || user instanceof ModUser) {
            System.out.println("4: View all reported posts");
            System.out.println("5: Show all user");

        }
        displayMainMenuFooter();
    }

    public void displayUserManagement(Scanner sc, User user) {
        List<User> list = UserManagement.getInstance().getUserList();
        for(int i = 1; i <= list.size(); i++) {
            System.out.println(i + ": " + list.get(i - 1).getName());
        }
        System.out.println("Please select user:");
        String regUsStr = sc.nextLine();
        int index = (int) Integer.parseInt(regUsStr);
        User regUser = list.get(index-1);

        System.out.println("""
                Please select what would you like to do:
                1: Delete selected user
                2: Ban selected user""");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                ((AdminUser)user).deleteUser(regUser);
                break;
            case "2":
                ((AdminUser)user).banUser(regUser);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
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
        List<Post> postList = PostManagement.getInstance().getPostList();
        if(postList == null){
            System.out.println("No posts found");
        } else {
            for (Post post : postList) {
                System.out.printf(" (%d)Likes (%d)Reports [ %s ] : %s \n", post.getLikes(), post.getReports().size(),
                        post.getUser().getName(), post.getMsg());
            }
        }
    }

    public void bannedUsersMenu(Scanner scanner, Administration adminUser, List<User> users){
        System.out.println("======= BANNED USERS ========");
        System.out.println("""
                Please select what would you like to do:
                1: Show Banned Users
                2: Delete user""");

        switch(scanner.nextInt()){
            case 1:
                showBannedUsers(adminUser, users);
                break;
            case 2:
                deleteUser(adminUser, users, scanner);
                break;
            default:
                System.out.println("Invalid choice please try again");
                bannedUsersMenu(scanner, adminUser, users);
        }
    }

    public void showBannedUsers(Administration adminUser, List<User> users){
        adminUser.showBannedUsers(users.stream().filter(User::getBanned).toList());
    }

    public void deleteUser(Administration adminUser, List<User> users, Scanner scanner){
        System.out.println("======= DELETE USER ========");
        System.out.println("""
                Please select what would you like to do:
                1: Select user to delete""");
        int tmpDelete = scanner.nextInt();
        System.out.println("To confirm delete enter Yes");
        if(scanner.next().equals("Yes")){
            adminUser.deleteUser(users.stream().filter(User::getBanned).toList().get(tmpDelete - 1));
        }
    }

    public void changeUser(User user, List<User> users, Scanner scanner){
        System.out.println("======= CHANGE USER ========");
        System.out.println("""
                Please select what would you like to do:
                1: Select user to change to""");
        int tmpChoice = scanner.nextInt();
        user = users.get(tmpChoice - 1);
    }

    public void printUsers(List<User> users){
        StringBuilder sb;
        for (int i = 0; i < users.size(); i++) {
               sb = new StringBuilder();
                    sb.append(i + 1).append(" name: ").append(users.get(i).getName())
                    .append(" email: ").append(users.get(i).getEmail());
            System.out.println(sb.toString());
        }
    }

    // Todo: add option to like post.
    public void likeOption(List<Post> posts, User user)
    {
        showPosts();
    }
}