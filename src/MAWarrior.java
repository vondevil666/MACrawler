/**
 * Created by dongz on 2017/3/29.
 */
public class MAWarrior {
    private int startId=1;      //抓数据的起始乐队id
//    private int processAmount;      //本次抓取乐队个数
    Downloader downloader;
    Depositer depositer;
    PageProcessor pageProcessor;
    Scheduler scheduler;

    public MAWarrior(){
        scheduler = new Scheduler(startId, 10);
    }

    public static void main(String[] args) {

    }
}
