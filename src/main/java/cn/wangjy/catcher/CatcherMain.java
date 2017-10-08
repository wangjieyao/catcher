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
<<<<<<< HEAD
=======
//        String url = "https://www.amazon.com/Nexlux-Wireless-Controlled-150leds-Waterproof/product-reviews/B072DXFKRH" +
//                "/ref=cm_cr_arp_d_paging_btm_2?ie=UTF8&reviewerType=avp_only_reviews&sortBy=recent&pageNumber=2";
        String url = "https://www.amazon.com/product-reviews/B06XB97V85/ref=cm_cr_getr_d_paging_btm_2?ie=UTF8&reviewerType=all_reviews&sortBy=recent&pageNumber=2";
>>>>>>> c6927df8cd7f016b530eb5f65601f35725760f4a

//        String url = "https://www.amazon.com/Nexlux-Wireless-Controlled-150leds-Waterproof/product-reviews/B072DXFKRH" +
//                "/ref=cm_cr_arp_d_paging_btm_2?ie=UTF8&reviewerType=avp_only_reviews&sortBy=recent&pageNumber=2";
//        String url = "https://www.amazon.com/product-reviews/B00O4IPV9O?pageNumber=4";
        String url = "https://www.amazon.com/product-reviews/B0196DRB12?pageNumber=1";
        CatcherService service = new CatcherService();
        service.getAmazonComment(url);
//        url = url.substring(0,url.lastIndexOf("=")+1);
//        System.out.println(url);
    }
}
