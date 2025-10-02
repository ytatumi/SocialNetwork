package toxic;

import toxic.common.Reportable;
import toxic.user.User;
import java.time.LocalDateTime;

public class Report<T extends Reportable> {

    private User reportCreator;
    private User moderator;
    private T target;
    LocalDateTime timeStamp;
    private boolean isModerated;


    public Report(User reportCreator, T target){
        this.reportCreator = reportCreator;
        this.target = target;
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