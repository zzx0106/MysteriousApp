package mytapp.xmz.com.mysteriousapp.baseshare;

import android.content.Context;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/11/17.
 */
public class BaseShareUtil {
    public static void showShare(Context context,
                                 String title,
                                 String titleUrl,
                                 String message,
                                 String imageUrl,
                                 String url,
                                 String comment,
                                 String site,
                                 String siteUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        if(title!=null) {
            oks.setTitle(title);
        }
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        if(titleUrl!=null) {
            oks.setTitleUrl(titleUrl);
        }
        // text是分享文本，所有平台都需要这个字段
        if(message!=null){
            oks.setText(message);
        }
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        String sharimage ="http://www.easyicon.net/api/resizeApi.php?id=1204591&size=96";
        if(imageUrl!=null) {
            sharimage = imageUrl;
            oks.setImageUrl(sharimage);
        }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        if(url!=null) {
            oks.setUrl(imageUrl);
        }
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        if(comment!=null) {
            oks.setComment(comment);
        }
        // site是分享此内容的网站名称，仅在QQ空间使用
        if(site!=null) {
            oks.setSite(site);
        }
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        if(siteUrl!=null) {
            oks.setSiteUrl(siteUrl);
        }
// 启动分享GUI
        oks.show(context);
    }
}
