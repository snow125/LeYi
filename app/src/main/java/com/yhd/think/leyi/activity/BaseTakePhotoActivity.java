package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.tools.ToastTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public abstract class BaseTakePhotoActivity extends BaseActivity {

    private static final String PICTURE_PATH = "/leyi/image/";
    private static final String PHOTO_NAME = "IMG_";
    private static final int PHOTO_REQUEST_CAMERA = 0;
    private static final int PHOTO_REQUEST_GALLERY = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private String picPath = Environment.getExternalStorageDirectory() + PICTURE_PATH;
    private Calendar c = Calendar.getInstance();
    private long currentTime;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                crop(data.getData());
            }
        }else if(requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                File file = new File(Environment.getExternalStorageDirectory(),
                        imageName);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                if(file==null || bitmap==null){
                    return;
                }
                FileOutputStream b = null;
                String fileName = picPath + imageName;
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(b!=null){
                            b.flush();
                            b.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                crop(Uri.fromFile(file));
            }else{
                ToastTool.showToast(BaseTakePhotoActivity.this, "未找到存储卡，无法存储照片！");
            }
        }else if(requestCode == PHOTO_REQUEST_CUT){
            if(data !=null ){
                Bitmap bitmap = data.getParcelableExtra("data");
                dealBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void dealBitmap(Bitmap bitmap);

    /**
     * 剪切图片
     *
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        currentTime = c.getTimeInMillis();
        imageName = PHOTO_NAME + currentTime + ".jpg";
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        currentTime = c.getTimeInMillis();
        imageName = PHOTO_NAME + currentTime + ".jpg";
        /*File file = new File(picPath, imageName);
        file.mkdirs();*/
        /**
         * 此处不设定uri，则onActivityResult()中的data不为空
         */
        /*if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
        }*/

        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), imageName)));
        }

        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
