import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by dongz on 2017/4/11.
 * 通过translate.google.cn翻译文本，需要先从网站获取tk值，才能通过ajax访问服务器获得翻译结果
 * 首次访问translate.google.cn能够获得TKK值，TKK与待翻译字符串共同算出tk值
 */
public class TKProcessor {
    private String tk = "";
    private long[] tkk = null;
    private static String url = "https://translate.google.cn";
    public String targetString = "Progressive/Death/Doom Metal (early), Melodic Heavy Metal/Rock (later)";

    public static void main(String[] args) {
        TKProcessor tkp = new TKProcessor();
        tkp.visitTG();
    }

    public String getTKValue(String targetString) {
        this.targetString = targetString;
        TKProcessor tkp = new TKProcessor();
        tk = tkp.visitTG();
        return getJason(combineURL());
    }

    private String combineURL() {
        StringBuffer sb = new StringBuffer();
        for (char c : targetString.toCharArray()) {
            if (c != ' ') sb.append(c);
            else sb.append("%20");
        }
        System.out.println(sb.toString());
        String s = "https://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=bh&otf=1&ssel=0&tsel=0&kc=1&tk=" + tk + "&q=" + sb.toString();
        System.out.println(s);
        return s;
    }

    private String getJason(String targetUrl) {
        StringBuffer sb1 = new StringBuffer();
        try {

            CloseableHttpClient client = HttpClients.createDefault();
            URI uri = new URIBuilder(targetUrl).build();
            HttpGet get = new HttpGet(uri);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站失败1"+response.getStatusLine().getStatusCode());
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String tmp = "";
                while ((tmp = br.readLine()) != null) {
                    sb.append(tmp);
                }
                int flag = 0;
                for (char c : sb.toString().toCharArray()) {
                    if (c == '\"') {
                        flag++;
                        continue;
                    }
                    if (flag == 1) sb1.append(c);
                    else if (flag == 2) break;
                }
                System.out.println(sb1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb1.toString();
    }

    //访问translate.google.cn，获得TKK值
    private String visitTG() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URI uri = new URIBuilder(url).build();
            HttpGet get = new HttpGet(uri);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("访问网站失败");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String tmp = "";
                while ((tmp = br.readLine()) != null) {
                    if (tmp.contains("TKK")) {
                        tkk = tkkFinder(tmp);
//                        System.out.println(tmp);  //打印含tkk的原始语句
                    }
                }
                tk = getTK();
                System.out.println(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    private String getTK() {
        int[] g = new int[targetString.length()];
        int d = 0;
        for (int f = 0; f < targetString.length(); f++) {
            int c = targetString.charAt(f);
            if (128 > c) g[d++] = c;
            else {
                if (2048 > c) g[d++] = c >> 6 | 192;
                else {
                    if (55296 == (c & 64512) && (f + 1 < targetString.length()) && 56320 == (targetString.charAt(f + 1) & 64512)) {
                        c = 65536 + ((c & 1023) << 10) + (targetString.charAt(++f) & 1023);
                        g[d++] = c >> 18 | 240;
                        g[d++] = c >> 12 & 63 | 128;
                    } else {
                        g[d++] = c >> 12 | 224;
                    }
                    g[d++] = c >> 6 & 63 | 128;
                }
                g[d++] = c & 12 & 63 | 128;
            }
        }
        long a = tkk[2];
        for (d = 0; d < g.length; d++) {
            a += g[d];
            a = functionB(a, "+-a^+6");
        }
        a = functionB(a, "+-3^+b+-f");
//        a ^= (tkk[0] + tkk[1]);
        int b=(int)((tkk[0] + tkk[1]) & 0xff_fff_fffL);
        a^=b;
        System.out.println("before  "+a);
        if(a<0) {
            a = ((int) a & 2147483647) + 2147483648L;
        }
        System.out.println("after  "+a);
        a %= 1000000;
        return tk = a + "." + (a ^ tkk[2]);
    }

    int ttt=0;
    private long functionB(long a, String b) {
//        System.out.println(a + " | " + b);
        ttt++;

        for (int d = 0; d < b.length() - 2; d += 3) {
            long c = b.charAt(d + 2);
            if (97 <= c) {
                c = c - 87;
            } else c = c - 48;
            if (b.charAt(d + 1) == '+') {
//                System.out.println("c="+c+"   a="+a);
                c = (int)a >>> c;
//                System.out.println("after process c="+c);
            } else {
                c = (int) a << c;
            }
            if (b.charAt(d) == '+') {
                a = (a + c);
                //以下算法是当a在2^31以内时原样返回a，在2^31之外则返回2^32-a,再取负
//                a=a& 4294967295L;//原公式
                if (a < (-1* (Math.pow(2,31)))) {
                    a = (long)Math.pow(2,32) + a;
                }
                else if (a-(Math.pow(2,31)) > 0) {
                    a = a - (long)Math.pow(2, 32);
                }
            } else {
                a = a ^ c;
            }
        }
        return a;
    }


    //region tkkFinder(),获得tkk数组，含3个长整型数
    private long[] tkkFinder(String html) {
        Pattern p = Pattern.compile("var a\\\\x3d(-?[\\d]*?);var b\\\\x3d(-?[\\d]*?);return ([\\d]*?)\\+");
        Matcher m = p.matcher(html);
        long[] t = new long[3];
        if (m.find()) {
            t[0] = Long.parseLong(m.group(1));
            t[1] = Long.parseLong(m.group(2));
            t[2] = Long.parseLong(m.group(3));
//            t[0] = 4145352448L;
//            t[1] = 72982085L;
//            t[2] = 414481;
//            System.out.println(t[0]+"   "+t[1]+"   "+t[2]);
        } else System.out.println("从translate.google.cn获取tkk失败");
        return t;
    }
    //endregion
}
