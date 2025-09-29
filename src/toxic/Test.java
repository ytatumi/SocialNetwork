package toxic;

import toxic.user.RegularUser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Test {

    SocialNetwork sc = new SocialNetwork();

    public void runTests() {
        System.out.println(addTestData());
        System.out.println(checkPost());
    }


    private boolean addTestData(){
        sc.listOfUsers.add(new RegularUser("namn1", "namn1@someDomain.test"));
        sc.listOfUsers.add(new RegularUser("namn2", "namn2@someDomain.test"));
        sc.listOfUsers.add(new RegularUser("namn3", "namn3@someDomain.test"));
        sc.listOfUsers.add(new RegularUser("namn4", "namn4@someDomain.test"));
        return sc.listOfUsers.size() == 5;
    }

    private boolean checkPost(){
        sc.listOfPosts.add(new Post(sc.listOfUsers.get(1), "testpost1"));
        sc.listOfPosts.add(new Post(sc.listOfUsers.get(2), "testpost2"));
        sc.listOfPosts.add(new Post(sc.listOfUsers.get(3), "testpost3"));
        sc.listOfPosts.add(new Post(sc.listOfUsers.get(4), "testpost4"));
        sc.showPosts();
        return sc.listOfPosts.size() == 5;
    }
}
