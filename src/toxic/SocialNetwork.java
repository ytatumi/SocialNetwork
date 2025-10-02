package toxic;

import toxic.management.PostManagement;
import toxic.management.UserManagement;
import toxic.user.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SocialNetwork {

    public SocialNetwork() {
    }

    public void run() {
        Scanner scn = new Scanner(System.in);
        User user = getCurrentUser(scn, UserManagement.getInstance().getUserList());
        selectMenu(scn, user);
    }

    public void displayMainMenu(User user) {
        displayMainMenuHeader();
        System.out.println("0: Create a User");
        System.out.println("1: View all posts");
        System.out.println("2: Create a post");
        System.out.println("3: Like/Unlike a post");
        System.out.println("4: Report a post");
        System.out.println("5: Switch User");
        if (user instanceof ModUser) {
            System.out.println("6: View all reported posts");
            System.out.println("7: Delete a reported post"); // Not implemented
            System.out.println("8: Accept a reported post"); // Not implemented
            System.out.println("9: Show all user");
            System.out.println("10: Ban user");
        }
        if (user instanceof AdminUser) {
            System.out.println("11: Delete User");
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
                case 0: //0: Create a User
                    createNewUser(scn, UserManagement.getInstance().getUserList());
                    break;
                case 1: //1: View all posts
                    showPosts();
                    break;
                case 2: // 2: Create a post
                    System.out.println("Please type the message. ");
                    String message = scn.nextLine();
                    Post post = currentUser.createPost(message);
                    break;
         
                case 3: // Like or Unlike a Post
                    likeUnlikeOption(PostManagement.getInstance().getPostList(), currentUser, scn);
                    break;
                case 4: // 4: Report a post
                    showPosts();
                    System.out.println("Please type the number of the post to report. ");
                    int msgToReport = Integer.parseInt(scn.nextLine());
                    System.out.println("Please type the comments for report. ");
                    String comment = scn.nextLine();
                    try {
                        Post postToReport = PostManagement.getInstance().getPostList().get(msgToReport - 1);
                        currentUser.reportPost(postToReport, comment);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid post number!");
                    }
                    break;
                case 5: // Change User
                    currentUser = changeUser(currentUser, UserManagement.getInstance().getUserList(), scn);
                    break;
                case 6:  // View all reported posts
                    if (currentUser instanceof ModUser) {
                        ((ModUser) currentUser).showReportedPosts();
                    }
                    break;
                case 7: // 6: Delete a reported post
                    if (currentUser instanceof ModUser) {
                        List<Post> reportedPostList = ((ModUser) currentUser).getReportedPostList();
                        if (reportedPostList.isEmpty()) {
                            System.out.println("No reported posts found!");
                        } else {
                            ((ModUser) currentUser).showReportedPosts();
                            System.out.println("Please type the number of the reported post to delete");
                            try {
                                int postId = Integer.parseInt(scn.nextLine());
                                Post deletedPost = reportedPostList.get(postId - 1);
                                ((ModUser) currentUser).deletePost(deletedPost);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid post number!");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid format for post number");
                            }
                        }
                    }
                    break;
                case 8: // 7: Accept a reported post
                    if (currentUser instanceof ModUser) {
                        List<Post> reportedPostList = ((ModUser) currentUser).getReportedPostList();
                        if (reportedPostList.isEmpty()) {
                            System.out.println("No reported posts found!");
                        } else {
                            ((ModUser) currentUser).showReportedPosts();
                            System.out.println("Please type the number of the reported post to accept");
                            try {
                                int postId = Integer.parseInt(scn.nextLine());
                                Post acceptedPost = reportedPostList.get(postId - 1);
                                ((ModUser) currentUser).acceptPost(acceptedPost);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Invalid post number!");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid format for post number");
                            }
                        }
                    }
                    break;
                case 9: // 8: Show all user
                    if (currentUser instanceof ModUser) {
                        displayAllUsers(scn, currentUser);
                    }
                    break;
                case 10: // 9: Ban user
                    if (currentUser instanceof ModUser) {
                        displayAllUsers(scn, currentUser);
                        User regUser = selectUserFromList(scn);
                        ((ModUser) currentUser).banUser(regUser);
                    }
                    break;
                case 11: // 10: Delete User
                    if (currentUser instanceof AdminUser) {
                        bannedUsersMenu(scn, (AdminUser) currentUser, UserManagement.getInstance().getUserList(), false);
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

    public void createNewUser(Scanner scn, List<User> listOfUsers) {
        Map<String, String> userInfo = getNewUserInfo(scn);
        String inputEmail = userInfo.get("email");
        String inputName = userInfo.get("name");
        User tmpUser = searchUser(inputEmail, listOfUsers);
        if (tmpUser != null) {
            System.out.println("That user email is already registered.");
            System.out.println("Would you like to try again?");
            System.out.println("1. Yes - Create new user");
            System.out.println("2. No - Return to main menu");

            int selected = -1;
            selected = Integer.parseInt(scn.nextLine());

            switch (selected){
                case 1:
                    createNewUser(scn, listOfUsers);
                    break;
                case 2:
                default:
                    return;
            }
        }
        while (tmpUser == null) {
            System.out.println("""
                   Please select the role for the new user:
                   1) Regular User 2) Moderator 3) Administrator""");
            int role = Integer.parseInt(scn.nextLine());
            switch (role) {
                case 1:
                    tmpUser = new RegularUser(inputName, inputEmail);
                    listOfUsers.add(tmpUser);
                    break;
                case 2:
                    tmpUser = new ModUser(inputName, inputEmail);
                    listOfUsers.add(tmpUser);
                    break;
                case 3:
                    tmpUser = new AdminUser(inputName, inputEmail);
                    listOfUsers.add(tmpUser);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        System.out.println("New user created:");
        System.out.println("Name: " + inputName);
        System.out.println("Email: " + inputEmail);
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

    public Map<String, String> getNewUserInfo(Scanner scn) {
        HashMap<String, String> userInfo = new HashMap<>();
        System.out.println("Provide the credentials of the new user");
        System.out.println("Name: ");
        String inputName = scn.nextLine().trim();
        System.out.println("Email: ");
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
        if (postList == null || postList.isEmpty()) {
            System.out.println("No posts found");
        } else {
            for (int i = 1; i <= postList.size(); i++) {
                System.out.printf("%d :  (%d)Likes (%d)Reports [ %s ] : %s \n", i, postList.get(i - 1).getLikes(), postList.get(i - 1).getNewReports().size(),
                        postList.get(i - 1).getUser().getName(), postList.get(i - 1).getMsg());
            }
        }
    }

    public void bannedUsersMenu(Scanner scanner, AdminUser adminUser, List<User> users, boolean isBannedUsers) {
        System.out.println("======= BANNED USERS ========");
        System.out.println("""
                Please select what would you like to do:
                1: Display all Users
                2: Show Banned Users
                3: Delete user""");

        switch (scanner.nextInt()) {
            case 1:
                displayAllUsers(scanner, (User) adminUser);
                bannedUsersMenu(scanner, adminUser, users, false);
                break;
            case 2:
                showBannedUsers(adminUser, users);

                bannedUsersMenu(scanner, adminUser, users, true);
                break;
            case 3:
                deleteUser(adminUser, users, scanner, isBannedUsers);
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice please try again");
                bannedUsersMenu(scanner, adminUser, users, isBannedUsers);
        }
    }

    public void showBannedUsers(Administration adminUser, List<User> users) {
        adminUser.showBannedUsers(users.stream().filter(User::getBanned).toList());
    }

    public void deleteUser(Administration adminUser, List<User> users, Scanner scanner, boolean banned) {
        System.out.println("======= DELETE USER ========");
        System.out.println("""
                Please select what would you like to do:
                1: Select user to delete""");
        int tmpDelete = scanner.nextInt();
        System.out.println("To confirm delete enter Yes");
        scanner.nextLine();
        if (scanner.nextLine().equals("Yes")) {
            if (banned) {
                adminUser.deleteUser(users.stream().filter(User::getBanned).toList().get(tmpDelete - 1));
            } else {
                adminUser.deleteUser(users.get(tmpDelete - 1));
            }
        }
    }

    public void printUsers(List<User> users) {
        StringBuilder sb;
        for (int i = 0; i < users.size(); i++) {
            sb = new StringBuilder();
            sb.append(i + 1).append(" name: ").append(users.get(i).getName())
                    .append(" email: ").append(users.get(i).getEmail());
            System.out.println(sb.toString());
        }
    }


    public void likeUnlikeOption(List<Post> posts, User user, Scanner scanner)
    {
        showPosts();
        System.out.println("======= Like Post ========");
        System.out.println("""
                Please enter the index of the post to
                like/unlike""");
        int tmpLike = scanner.nextInt();
        if(!posts.get(tmpLike - 1).getLikedByUser(user)){
            posts.get(tmpLike - 1).like(user);
        }
        else{
            posts.get(tmpLike - 1).unLike(user);
        }
        scanner.nextLine();
        showPosts();
    }

    public User changeUser(User currentUser, List<User> users, Scanner scanner){
        System.out.println("");
        System.out.println("======= CHANGE USER ========");
        System.out.println("Enter your email to login");
        String tmpChoice = scanner.nextLine();
        User user = searchUser(tmpChoice, users);
        if (user == null){
            System.out.println("Invalid user credentials");
            System.out.println("Would you like to remain as " + currentUser.getName() + " or try again?");
            System.out.println("1. Remain as " + currentUser.getName());
            System.out.println("2. Switch user");
            int selected = -1;
            selected = Integer.parseInt(scanner.nextLine());
            switch (selected){
                case 1:
                    user = currentUser;
                    break;
                case 2:
                default: changeUser(currentUser, users, scanner);
            }
        }
        String role;
        if (user instanceof AdminUser){
            role = "Administrator";
        } else if (user instanceof ModUser){
            role = "Moderator";
        } else {
            role = "Regular User";
        }
        System.out.println("");
        System.out.println("User " + user.getName() + " is logged in as " + role);
        return user;
    }
}