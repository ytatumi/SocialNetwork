package toxic;

// Reportable interface
// + report()
// Post implements Reportable, Likable

// + user, msg, timestamp, likers, reported [reportList (reported, moderated)]

import toxic.common.Likeable;
import toxic.common.Reportable;
import toxic.user.*;

import java.util.ArrayList;
import java.util.List;

public class Post implements Likeable, Reportable {

    // + user,
    // msg,
    // timestamp,
    // likers,
    // reported

    private User user;
    private String msg;
    private List<User> likers;
    private boolean reported;
    private List<Report> reports;

    public Post(User user, String msg){
        this.user = user;
        this.msg = msg;
        this.likers = new ArrayList<>();
        reports = new ArrayList<>();
    }

    @Override
    public void unLike(User user) {
        if(this.likers.contains(user)){
            this.likers.remove(user);
        }
    }

    @Override
    public void like(User user) {
        if(!this.likers.contains(user)){
            this.likers.add(user);
        }
    }

    @Override
    public void report(User user, String msg) {
        this.reported = true;
        this.reports.add(new Report(user, msg));
    }

    public List<User> getLikes(){ return this.likers; }
    public User getUser(){ return this.user; }
    public String getMsg(){ return this.msg; }
    public boolean getReported(){ return this.reported; }
    public void setReported(){ this.reported = !this.reported; }
    public List<Report> getReports(){ return this.reports; }

    // [reportList (reported, moderated)]

    //List<Reported> list = (reported(msg, User), moderated(null eller placeHolder))

    //list.add(list.get(list.size - 1).reported);

}