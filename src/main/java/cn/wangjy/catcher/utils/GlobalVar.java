package cn.wangjy.catcher.utils;




/**
 * Created by wangjieyao on 2016/6/3.
 */
public class GlobalVar {
//    public static Map<String, String> properties = null;
//    public static Map<String,PlatConfig> platConfigMap = new HashMap<String, PlatConfig>(); //平台配置信息
//    public static Map<String,String> tag = new HashMap<String, String>();
//    static {
//        properties = PropertiesUtils.loadAsMap("config.properties", GlobalVar.class);
//    }

    /*
    获取网页补偿机制配置
     */
    public static final int  CONNECT_TIMEOUT = 60000;
//    public static final int  READ_TIMEOUT = Integer.valueOf(properties.get("detect.read.timeout.millis"));
    public static final int  RETRY_COUNT = 3;
    public static final long RETRY_PEROID = 3000;

    /*
    标记文件路径
     */
//    public static final String TAG_PATH = properties.get("detect.tag.path");
//    public static final long MONITOR_PERIOD = Long.valueOf(properties.get("monitor.period.millis"));
//
//    /*
//    邮件发送周期
//     */
//    public static final long MAIL_PERIOD = Long .valueOf(properties.get("mail.period.millis"));



}
