package toxic;

import toxic.user.User;

import java.time.LocalDateTime;

public class Report {

    private User reportCreator;
    private User moderator;
    private String msg;
    LocalDateTime timeStamp;
    private boolean isModerated;


    public Report(User reportCreator, String msg){
        this.reportCreator = reportCreator;
        this.msg = msg;
        this.timeStamp = LocalDateTime.now();
        this.isModerated = false;
    }

    public User getReportCreator(){ return this.reportCreator; }
    public User getModerator(){ return this.moderator; }
    public void setModerator(User user){
        this.moderator = user;
        this.isModerated = true;
    }
    public boolean getIsModerated(){ return this.isModerated; }
    public LocalDateTime getTimeStamp(){ return this.timeStamp; }
}
