import java.util.ArrayList;

/**
 * Created by dongz on 2017/3/29.
 */
public class Scheduler {
    public static int currentLatestId;  //记录当前已处理的最后一个链接乐队id，当前批次链接处理完之后可以从此处调用显示。
    private int amount;
    public static ArrayList<String> bandsUrlList;

    public Scheduler(int startId, int amount) {
        this.currentLatestId=startId;
        this.amount=amount;
        bandsUrlList = new ArrayList<String>();
        createBandsUrlList();
    }

    private void createBandsUrlList(){
        for(int i=1;i<=amount;i++) {
            bandsUrlList.add(MAWarrior.baseUrl + i);
            currentLatestId++;
        }
    }
}
