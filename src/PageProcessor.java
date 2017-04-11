import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by dongz on 2017/3/29.
 */
public class PageProcessor {

    public PageProcessor(StringBuffer targetText) {
        pageProcess(targetText);
    }

    private void pageProcess(StringBuffer targetText){
//        String targetText=target.toString();
        Document doc = Jsoup.parse(targetText.toString());
        Element head = headProcess(doc.head());
        Element body = doc.body();
        Elements allTags=body.getAllElements();
        for (Element e : allTags) {
            try {
//                if (e.html().charAt(0) == '<') continue;
                if(elementFilter(e)==false)continue;
            } catch (Exception e1){};
            e.prepend("=");
            e.append("=");
        }
        new PipeLine(doc.outerHtml());
    }

    //region 筛选元素标签
    /**
     * 该函数筛选检查不需进行翻译的标签。不需做翻译的元素返回false，需要翻译的返回true
     * @param e 接收待检查的元素
     * @return  不需翻译者返回false，需翻译返回true
     */
    private boolean elementFilter(Element e) {
        if(e.html().startsWith("<"))return false;
        else if(!e.hasText())return false;
        return true;
    }
    //endregion

    //region 处理头文件，修改头文件中的js、css绝对路径
    /**
     * 处理头文件js元素中的css、js路径。改相对路径为绝对路径
     * @param head 传入页面doc的head元素
     * @return     返回修改过的head元素
     */
    private Element headProcess(Element head) {
        head.select("link[href]").first().attr("href","http://www.metal-archives.com/min/index.php?g=css");
        head.select("script[src]").first().attr("src","http://www.metal-archives.com/min/index.php?g=js");
        return head;
    }
    //endregion
}
