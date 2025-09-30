package toxic.user;

import toxic.Report;
import toxic.common.Reportable;
import java.util.List;

public class RegularUser extends User implements Reportable {
    boolean reported;
    private List<Report> reports;
    public RegularUser(String name, String email){
        super(name, email);
    }


    @Override
    public void report(User user, String msg) {
        reported = true;
        reports.add(new Report(this, "<<USER>>"));
    }
}