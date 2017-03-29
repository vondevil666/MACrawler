import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;

/**
 * Created by dongz on 2017/3/28.
 */
public class Downloader {
    private String url;

    public Downloader(String url){
        this.url=url;
        try{
            download();
        }catch(Exception e){
            e.printStackTrace();
        };
    }

    public void download() throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder(url).build();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

//        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
//        String tmp = "";
//        StringBuffer finalText = new StringBuffer();
//        while ((tmp = br.readLine()) != null) {
//            finalText.append(tmp).append("\n");
//        }
        System.out.println("current band id was "+url+" : "+response.getStatusLine());
        client.close();
    }

}
