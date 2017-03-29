import java.util.ArrayList;

/**
 * Created by dongz on 2017/3/29.
 */
public class Scheduler {
    private int currentId;
    private int startId;
    private int amount;
    public static ArrayList<String> bandsUrlList;

    public Scheduler(int startId, int amount) {
        this.startId=startId;
        this.amount=amount;
        currentId=startId;
        createBandsUrlList();
    }

    private void createBandsUrlList(){
        for(int i=startId;i<startId+amount;i++) {
            bandsUrlList.add(MAWarrior.baseUrl + i);
        }
    }
}
