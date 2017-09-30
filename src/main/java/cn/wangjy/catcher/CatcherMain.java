package cn.wangjy.catcher;

import cn.wangjy.catcher.service.CatcherService;

/**
 * TODO ADD DESCRIPTION
 *
 * @author wangjieyao
 * @Date 2017/9/27 19:29
 */
public class CatcherMain {
    public static void main(String[] args) {
//        String url = "https://www.amazon.com/Nexlux-Wireless-Controlled-150leds-Waterproof/product-reviews/B072DXFKRH" +
//                "/ref=cm_cr_arp_d_paging_btm_2?ie=UTF8&reviewerType=avp_only_reviews&sortBy=recent&pageNumber=2";
        String url = "https://www.amazon.com/product-reviews/B06XB97V85/ref=cm_cr_getr_d_paging_btm_2?ie=UTF8&reviewerType=all_reviews&sortBy=recent&pageNumber=2";

        CatcherService service = new CatcherService();
        service.getAmazonComment(url);
//        url = url.substring(0,url.lastIndexOf("=")+1);
//        System.out.println(url);
    }
}
