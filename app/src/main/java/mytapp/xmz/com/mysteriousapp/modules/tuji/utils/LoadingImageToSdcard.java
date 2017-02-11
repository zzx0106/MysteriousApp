package mytapp.xmz.com.mysteriousapp.modules.tuji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2001/1/3 0003.
 */
public class LoadingImageToSdcard {
    private OnBackMessage backMessage;
    private Context context;

    public LoadingImageToSdcard(Context context,OnBackMessage backMessage) {
        this.context = context;
        this.backMessage = backMessage;
    }

    public interface OnBackMessage{
        void backMessage(String message);
    }

    public boolean isWiFi(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.getType()==manager.TYPE_WIFI){
            return true;
        }
        return false;
    }

    public void loading(final String url, final String path){


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL loadUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) loadUrl.openConnection();

                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    boolean isOk = saveFile(bitmap,path,url);
                    String ss = null;
                    if(isOk){
                        ss = "下载成功";
                    }else {
                        ss = "下载失败";
                    }

                    backMessage.backMessage(ss);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private boolean saveFile(Bitmap bitmap, String path, String url){
        try {
            int index = url.lastIndexOf("/");

            String picName = url.substring(index,url.length());

            File file = new File(path+picName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

            bos.flush();
            bos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
