package com.yhd.think.leyi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.tools.BitmapTool;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.ToastTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class PublishActivity extends BaseActivity {

    private static final String PHOTO_NAME = "IMG_";
    private static final int PHOTO_REQUEST_CAMERA = 0;
    private static final int PHOTO_REQUEST_GALLERY = 1;
    private EditText name_et;
    private EditText words_et;
    private EditText request_et;
    private String name;
    private String words;
    private String request;
    private TextView title_tv;
    private String title;
    private boolean isSell;
    private boolean isBook;
    private Button publish_b;
    private ImageView camera;
    private LinearLayout cameraAndpic_ll;
    private ImageView publish_iv;
    private ImageView back;
    private DialogTool pictureDialog;
    private String picPath = Environment.getExternalStorageDirectory().getPath() + Constants.PICTURE_PATH;
    private Calendar c = Calendar.getInstance();
    private long currentTime;
    private String imageName;
    private ImageView goodPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        findViews();
        initActivity();
        initDialog();
        setViews();
    }

    private void initDialog() {
        pictureDialog = new DialogTool() {
            @Override
            public void positive() {
                gallery();
            }

            @Override
            public void negative() {
                camera();
            }
        };
    }

    private void initActivity() {
        Bundle b = getIntent().getExtras();
        title = b.getString("title");
        isSell = b.getBoolean("isSell");
        isBook = b.getBoolean("isBook");
        if(isSell){
            cameraAndpic_ll.setVisibility(View.VISIBLE);
        }else{
            cameraAndpic_ll.setVisibility(View.GONE);
        }
    }

    private void setViews() {
        name = name_et.getText().toString();
        words = words_et.getText().toString();
        request = request_et.getText().toString();
        title_tv.setText(title);
        publish_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDialog.showDialog(PublishActivity.this, "照片", "选择获取照片的方式" ,"相册" ,"相机");
            }
        });
        publish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        publish_iv = (ImageView) findViewById(R.id.actionbar_publish);
        back = (ImageView) findViewById(R.id.actionbar_back);
        cameraAndpic_ll = (LinearLayout) findViewById(R.id.camera_picture);
        camera = (ImageView) findViewById(R.id.activity_publish_camera);
        publish_b = (Button) findViewById(R.id.activity_publish_publish);
        name_et = (EditText) findViewById(R.id.activity_publish_name);
        words_et = (EditText) findViewById(R.id.activity_publish_words);
        request_et = (EditText) findViewById(R.id.activity_publish_request);
        title_tv = (TextView) findViewById(R.id.actionbar_text);
        goodPic = (ImageView) findViewById(R.id.activity_publish_good_picture);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Bitmap bitmap = BitmapTool.getBitmapFromUri(PublishActivity.this, data.getData());
                goodPic.setBackground(new BitmapDrawable(bitmap));
            }
        }else if(requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                File file = new File(picPath, imageName);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                goodPic.setBackground(new BitmapDrawable(bitmap));
                FileOutputStream b = null;
                String fileName = picPath + imageName;
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                ToastTool.showToast(PublishActivity.this, "未找到存储卡，无法存储照片！");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        /**
         * 此处不设定uri，则onActivityResult()中的data不为空
         */
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(picPath, imageName)));
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
