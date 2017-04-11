import java.util.ArrayList;

/**
 * Created by dongz on 2017/3/29.
 * Main函数调用前即生成此类，指定起始id和本次待抓取数据的个数。
 * 在此类中生成所有待抓取对象的url
 */
public class Scheduler {
    public static int currentLatestId;  //记录当前待处理的第一个链接乐队id，当前批次链接处理完之后可以从此处调用显示。
    private int amount;
    public static ArrayList<String> bandsUrlList;

    public Scheduler(int startId, int amount) {
        this.currentLatestId=startId;
        this.amount=amount;
        bandsUrlList = new ArrayList<String>();
        createBandsUrlList();
    }

    private void createBandsUrlList(){
        for(int i=0;i<amount;i++) {
            bandsUrlList.add(MAWarrior.baseUrl + currentLatestId);
            currentLatestId++;
        }
    }
}
