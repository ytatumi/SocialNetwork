package toxic;

import toxic.management.PostManagement;
import toxic.management.UserManagement;
import toxic.user.AdminUser;
import toxic.user.ModUser;
import toxic.user.RegularUser;
import toxic.user.User;

import java.util.*;

public class SocialNetwork {

    public SocialNetwork() {
    }

    public void run() {
        Scanner scn = new Scanner(System.in);
        User user = getCurrentUser(scn, UserManagement.getInstance().getUserList());
        selectMenu(scn, user);
        // selectMenu(scn, listOfUsers.get(0));
    }

    public void displayMainMenu(User user) {
        displayMainMenuHeader();
        System.out.println("0: Create a User");
        System.out.println("1: View all posts");
        System.out.println("2: Create a post");
        System.out.println("3: Like a post"); //Not implemented
        System.out.println("4: Report a post");
        if (user instanceof ModUser) {
            System.out.println("5: View all reported posts");
            System.out.println("6: Show all user");
            System.out.println("7: Ban user");
        }
        if (user instanceof AdminUser) {
            System.out.println("8: Delete User");
        }
        displayMainMenuFooter();
    }

    public void selectMenu(Scanner scn, User user) {
        User currentUser = user;
        int selected = -1;
        while (selected != 99) {
            displayMainMenu(currentUser);
            System.out.println("Please type the number what you would like to do. ");
            selected = Integer.parseInt(scn.nextLine());
            switch (selected) {
                case 0:
                    currentUser = getCurrentUser(scn, UserManagement.getInstance().getUserList());
                    break;
                case 1:
                    showPosts();
                    break;
                case 2:
                    System.out.println("Please type the message. ");
                    String message = scn.nextLine();
                    Post post = currentUser.createPost(message);
                    break;
                case 3:
                    // like
                    break;
                case 4:
                    showPosts();
                    //System.out.println("Please copy the message to report. ");
                    //String msgToReport = scn.nextLine().trim();
                    System.out.println("Please type the number of the message to report. ");
                    int msgToReport = Integer.parseInt(scn.nextLine());
                    try {
                        Post postToReport = PostManagement.getInstance().getPostList().get(msgToReport - 1);
                        currentUser.reportPost(postToReport);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid post number!");
                    }

                    break;
                case 5:
                    if (currentUser instanceof ModUser) {
                        //((ModUser) user).showReportedPosts();
                        //showReportedPosts();
                        ((ModUser) currentUser).showReportedPosts();
                    }
                    break;
                case 6:
                    if (currentUser instanceof ModUser) {
                        displayAllUsers(scn, currentUser);
                    }
                    break;
                case 7:
                    if (currentUser instanceof ModUser) {
                        displayAllUsers(scn, currentUser);
                        User regUser = selectUserFromList(scn);
                        ((ModUser) currentUser).banUser(regUser);
                    }
                    break;
                case 8:
                    if (currentUser instanceof AdminUser) {
                        //displayUserManagement(scn, user);
                        displayAllUsers(scn, currentUser);
                        User regUser = selectUserFromList(scn);
                        ((AdminUser) currentUser).deleteUser(regUser);
                        break;
                    }
                    break;
                case 99:
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public User getCurrentUser(Scanner scn, List<User> listOfUsers) {
        Map<String, String> userInfo = getUserInfo(scn);
        String inputEmail = userInfo.get("email");
        String inputName = userInfo.get("name");
        User currentUser = searchUser(inputEmail, listOfUsers);
        if (currentUser != null) {
            System.out.printf("User is already registered as role: %s \n", currentUser.getClass().getSimpleName());
        }
        while (currentUser == null) {
            System.out.println("Please type number of your role. 1) Regular User 2) Moderator 3)Administrator");
            int role = Integer.parseInt(scn.nextLine());
            switch (role) {
                case 1:
                    currentUser = new RegularUser(inputName, inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                case 2:
                    currentUser = new ModUser(inputName, inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                case 3:
                    currentUser = new AdminUser(inputName, inputEmail);
                    listOfUsers.add(currentUser);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        return currentUser;
    }

    public Map<String, String> getUserInfo(Scanner scn) {
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

    public Post searchPostByMessage(String inputMsg, List<Post> listOfPosts) {
        for (Post post : listOfPosts) {
            if (post.getMsg().equals(inputMsg)) {
                return post;
            }
        }
        return null;
    }

    public void displayAllUsers(Scanner sc, User user) {
        List<User> list = UserManagement.getInstance().getUserList();
        for (int i = 1; i <= list.size(); i++) {
            System.out.println(i + ": " + list.get(i - 1).getName());
        }
    }

    public User selectUserFromList(Scanner sc) {
        List<User> list = UserManagement.getInstance().getUserList();
        System.out.println("Please select user:");
        String regUsStr = sc.nextLine();
        int index = Integer.parseInt(regUsStr);
        return list.get(index - 1);
    }


    public void displayUserManagement(Scanner sc, User user) {
        List<User> list = UserManagement.getInstance().getUserList();
        for (int i = 1; i <= list.size(); i++) {
            System.out.println(i + ": " + list.get(i - 1).getName());
        }
        System.out.println("Please select user:");
        String regUsStr = sc.nextLine();
        int index = (int) Integer.parseInt(regUsStr);
        User regUser = list.get(index - 1);

        System.out.println("""
                Please select what would you like to do:
                1: Delete selected user
                2: Ban selected user""");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                ((AdminUser) user).deleteUser(regUser);
                break;
            case "2":
                ((AdminUser) user).banUser(regUser);
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
        if (postList == null ||  postList.isEmpty()) {
            System.out.println("No posts found");
        } else {
            for (int i = 1; i <= postList.size(); i++) {
                System.out.printf("%d :  (%d)Likes (%d)Reports [ %s ] : %s \n", i, postList.get(i - 1).getLikes(), postList.get(i - 1).getReports().size(),
                        postList.get(i - 1).getUser().getName(), postList.get(i - 1).getMsg());
            }
        }
    }
}