/**
 * Created by dongz on 2017/3/29.
 */
public class MAWarrior {
    public static String baseUrl = "http://www.metal-archives.com/bands//";
    //    private int processAmount;      //本次抓取乐队个数
    Downloader downloader;
    PipeLine depositer;
    PageProcessor pageProcessor;
    Scheduler scheduler;
    private int startId = 1;      //抓数据的起始乐队id

    public MAWarrior() {
        scheduler = new Scheduler(startId, 2);
    }

    public static void main(String[] args) {
        new MAWarrior().startFire();
    }

    private void startFire() {
        System.out.println("start the fire");
        while (!Scheduler.bandsUrlList.isEmpty()) {
            //TODO  此处需用多线程处理downloader工作
            new Downloader(Scheduler.bandsUrlList.remove(0));    //url栈pop出一个元素，进入download流程
        }
        System.out.println("Fire Extinguished.");
    }

}
