import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by dongz on 2017/3/29.
 */
public class PipeLine {

    public PipeLine(String htmlString) {
        createHtmlFile(htmlString);
    }

    private void createHtmlFile(String htmlString){
        try {
            File file = new File("C:\\Users\\dongz\\Desktop\\band.html");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(htmlString);
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Write into file FAILED!");
        }
    }
}
