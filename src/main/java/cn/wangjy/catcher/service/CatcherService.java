package cn.wangjy.catcher.service;

import cn.wangjy.catcher.bean.AmazonComment;
import cn.wangjy.catcher.utils.GlobalVar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO ADD DESCRIPTION
 *
 * @author wangjieyao
 * @Date 2017/9/26 18:57
 */
public class CatcherService {
    private static final Logger LOG = LoggerFactory.getLogger(CatcherService.class);

    public void getAmazonComment(String url ){
        List<String> urls = getMoreUrl(url);
        for (String u : urls){
            Document doc = fetchUrl(u,GlobalVar.CONNECT_TIMEOUT);
            if (doc == null){
                LOG.info("Document is null,url:{}",url);
                return;
            }
            List<AmazonComment> comments = getComment(doc);
        }

    }


    public List<String> getMoreUrl(String url){
        Document doc = fetchUrl(url,GlobalVar.CONNECT_TIMEOUT);
        if (doc == null){
            LOG.info("Document is null,url:{}",url);
            return null;
        }
        int maxPageNum = getMaxPageNums(doc);
        List<String> urls = new ArrayList<>();
        url = url.substring(0,url.lastIndexOf("=")+1);
        for (int i=1;i<=maxPageNum;i++){
            System.out.println(url+i);
            urls.add(url+i);
        }
        return urls;
    }

    private int getMaxPageNums(Document document){
        Elements elements = document.select("li.page-button");
        int lastPageNum = Integer.parseInt(elements.last().text());
        return lastPageNum;
    }


    private List<AmazonComment> getComment(Document document){
        List<AmazonComment> list = new ArrayList<>();
        Elements elements = document.select("div.a-section.review div.a-section.celwidget");
        for (Element e :elements){
            String starts = e.children().select("div a.a-link-normal").first().attr("title");
            String comment = e.children().select("span.a-size-base.review-text").text();
            String author = e.children().select("a.a-size-base.a-link-normal.author").text();
            String date = e.children().select("span.a-size-base.a-color-secondary.review-date").text();
            AmazonComment c = new AmazonComment();
            c.setAuthor(author);
            c.setComment(comment);
            c.setDate(date);
            c.setStarts(starts);
            list.add(c);
            System.out.println(starts+"  ::  "+author+"  ::  "+date+"  ::  "+comment);
        }
        return list;

    }

    private Document fetchUrl(String url,int connectTimeout) {
        Document doc = null;
        int times = GlobalVar.RETRY_COUNT *3;
        while(doc ==null && times-- >0){
            try {
                doc = Jsoup.connect(url).timeout(connectTimeout).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36").get();
            } catch (IOException e) {
                LOG.info("Get document error.",e);
                if(times % 3 ==0 && times != 0 ) {
                    try {
                        Thread.sleep(GlobalVar.RETRY_PEROID);
                    } catch (InterruptedException e1) {
                        LOG.info("Get document error.e1:",e1);
                    }
                }
            }
        }
        return doc;
    }
}
