package com.java.yuanjiarui.tools;
//public class DownLoad {
//    public  static String downLoadImage(String imageUrl,String title){
//        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(imageUrl));
//        request.setTitle(title);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,title+".jpg");
//        DownloadManager downloadManager=(DownloadManager) getSystemService()
//
//
//    }
//
//}
import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;

public class DownLoad {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;

    private Context context;

    public DownLoad(Context context) {
        this.context = context;
    }

    public void downloadImage(String imageUrl, String title) {
        // 检查并请求存储权限
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // 请求存储权限
//            ActivityCompat.requestPermissions((Activity) context,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_CODE_STORAGE_PERMISSION);
//            Log.d("download","noDownLoad");
//        } else {
//            // 已经获得了存储权限，执行下载操作
            performDownload(imageUrl, title);
     //   }
    }

    private void performDownload(String imageUrl, String title) {
        Log.d("download","downLoadStart");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setTitle(title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String subPath;
       // if(imageUrl.endsWith(".png")){
       //     subPath=title+".png";
       // }
       // else {
            subPath=title+".jpg";
        //}\
        Log.d("download",imageUrl);
        Log.d("download",Environment.DIRECTORY_PICTURES+"/"+subPath);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, subPath);
        //request.setDestinationInExternalPublicDir("D:", subPath);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }

}