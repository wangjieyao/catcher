package cn.wangjy.catcher.service;

import cn.wangjy.catcher.bean.AmazonComment;
import cn.wangjy.catcher.utils.GlobalVar;
import com.lamfire.utils.StringUtils;
import com.mob.jedis.RedisCli;
import com.mob.jedis.RedisFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO ADD DESCRIPTION
 *
 * @author wangjieyao
 * @Date 2017/9/26 18:57
 */
public class CatcherService {
    private static final Logger LOG = LoggerFactory.getLogger(CatcherService.class);
    private static RedisCli redisCli= RedisFactory.getInstance().getRedisCli();

    private static final String URL_FORMAT= "https://www.amazon.com/product-reviews/%s/ref=cm_cr_getr_d_paging_btm_%s?ie=UTF8&reviewerType=all_reviews&sortBy=recent&pageNumber=%s";
    private static final String URL_NOPAGE_FORMAT = "https://www.amazon.com/product-reviews/%s/ref=cm_cr_getr_d_paging_btm_1?ie=UTF8&reviewerType=all_reviews&sortBy=recent";


//    private static String url = "https://www.amazon.com/product-reviews/B00O4IPV9O?pageNumber=4"

    public void processComments(){
//        get
    }

    public void getAmazonComment(String url ){
        List<String> urls = getAllPageUrls(url);
        List<AmazonComment> comments = new ArrayList<>();
        for (String u : urls){
            Document doc = fetchUrl(u,GlobalVar.CONNECT_TIMEOUT);
            if (doc == null){
                LOG.info("Document is null,url:{}",url);
                return;
            }
            List<AmazonComment> data = getComment(doc);
            comments.addAll(data);
        }
        exportExcel("B0196DRB12",comments);
    }

    private void analyse(List<AmazonComment> comments){
        if (comments == null || comments.isEmpty()){
            return;
        }
        for (AmazonComment comment : comments){
            redisCli.sadd("CATCHER_IDS",comment.getId());
            //analyse starts
            String startKey = String.format("%s:%s","START",comment.getId());
            redisCli.zincrby(startKey,1.0,comment.getStarts());
            String colorKey = String.format("%s:%s","COLOR",comment.getId());
            redisCli.zincrby(colorKey,1.0,comment.getColor());
            String sizeKey=String.format("%s:%s","SIZE",comment.getId());
            redisCli.zincrby(sizeKey,1.0,comment.getSize());
        }
    }


    private Object exportExcel(String fileName,List<AmazonComment> list) {
        String tmp =  fileName +".xls";
        System.out.println(tmp);
        LOG.info("file path:{}", tmp);
        File fileTmp = new File(tmp);
        FileOutputStream fileOutStream = null;
        try {
            fileTmp.createNewFile();
            fileOutStream = new FileOutputStream(fileTmp);
            if (null != list && list.size() > 0) {
                ExportExcelService.exportExcel("amazon-comment-data", fileOutStream, list);
            }
        } catch (IOException e) {
            LOG.error("export excel create temp file exception. {}", e);
        } finally {
            try {
                if (null != fileOutStream)
                    fileOutStream.close();
            } catch (IOException e) {
                fileOutStream = null;
            }
        }
        return fileTmp;
    }


    public List<String> getAllPageUrls(String url){
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

    public List<String> getUrlsByIds(List<String> ids){
        if (ids == null || ids.isEmpty()){
            return null;
        }
        List<String> list = new ArrayList<>(ids.size());
        for (String id : ids){
            String url = String.format(URL_NOPAGE_FORMAT, id);
            list.add(url);
        }
        return list;
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
            String tmp = e.children().select("a.a-size-mini.a-link-normal.a-color-secondary").text();
            String verified = e.children().select("span.a-size-mini.a-color-state.a-text-bold").text();

            starts = starts.substring(0,starts.indexOf(" "));
            String size = tmp.substring(tmp.indexOf("Size:")+5,tmp.indexOf("|"));
            String color = tmp.substring(tmp.indexOf("Color:")+6,tmp.length());

            AmazonComment c = new AmazonComment();
            c.setAuthor(author);
            c.setComment(comment);
            c.setDate(date);
            c.setStarts(starts);
            c.setVerified(verified);
            c.setColor(color);
            c.setSize(size);
            list.add(c);
            System.out.println(c.toString());

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


    private List<String> getAllIds(String fileName){
        BufferedReader br = null;
        String line = null;
        StringBuffer sb = new StringBuffer();
        List<String> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            while (!StringUtils.isEmpty(line = br.readLine())){
                result.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            LOG.info("Get all ids error.",e);
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                LOG.info("",e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CatcherService service = new CatcherService();
        service.getAllIds("CATCHER.txt");
//        List<String> ids = new ArrayList<>();
//        ids.add("aa");
//        ids.add("bb");
//        List<String> list = service.getUrlsByIds(ids);
//        for (String s:list){
//            System.out.println(s);
//        }
//        RedisFactory factory = RedisFactory.getInstance();
//        RedisCli redisCli = factory.getRedisCli();
//        redisCli.zadd("key",1,"red");
    }
}
