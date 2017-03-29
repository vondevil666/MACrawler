import java.util.ArrayList;

/**
 * Created by dongz on 2017/3/29.
 */
public class MAWarrior {
    public static String baseUrl = "http://www.metal-archives.com/bands//";
    //    private int processAmount;      //本次抓取乐队个数
    Downloader downloader;
    Depositer depositer;
    PageProcessor pageProcessor;
    Scheduler scheduler;
    private int startId = 1;      //抓数据的起始乐队id


    public MAWarrior() {
        scheduler = new Scheduler(startId, 10);
    }

    public static void main(String[] args) {
        new MAWarrior().startFire();
    }

    private void startFire() {
        System.out.println("start fire");
        while(!Scheduler.bandsUrlList.isEmpty()){
            new Downloader(Scheduler.bandsUrlList.remove(0));
        }
    }

    private void pop(){
        Scheduler.bandsUrlList.remove(0);
        if((int)(Math.random()+0.5)==1?true:false){
            Scheduler.bandsUrlList.add("Plus");
        }
    }

}
