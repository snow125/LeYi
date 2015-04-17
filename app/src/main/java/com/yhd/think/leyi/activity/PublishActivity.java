package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Image;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.fragment.LoopViewPagerFragment;
import com.yhd.think.leyi.tools.DensityTool;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.QiniuUploadUitls;
import com.yhd.think.leyi.tools.SharedPrefsUtil;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends BaseTakePhotoActivity {

    private static final String puclishURL = Constants.BASE_URL+"addItem.action?wutypeId=%s&userId=%s&wuName=%s&wuDesrc=%s&hopAddr=%s&hopeMoney=%s&hopeFuwu=%s&hopeWu=%s&wuPic=%s&ImageNum=%s";
    private EditText name_et;
    private EditText words_et;
    private EditText money_et;
    private EditText good_et;
    private EditText service_et;
    private String good;
    private String service;
    private String name;
    private String words;
    private String money;
    private TextView title_tv;
    private String title;
    private Button publish_b;
    private ImageView camera;
    private LinearLayout cameraAndpic_ll;
    private TextView publish_iv;
    private ImageView back;
    private DialogTool pictureDialog, exitDialog;
    private static boolean isSave = false;
    private int pictureNum;
    private User user = User.getInstance();
    private List<String> imageUrls = new ArrayList<String>();
    private static List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private int totalNum;
    private boolean uploadOK;
    private static List<Image> images = new ArrayList<Image>();
    private String type, school;
    private TextView type_tv, school_tv;
    private boolean isEixt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        findViews();
        initActivity();
        initDialog();
        setViews();
    }

    @Override
    protected void dealBitmap(Bitmap bitmap) {
        addImageView(bitmap);
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
        exitDialog = new DialogTool() {
            @Override
            public void positive() {
                isSave = true;
                PublishActivity.super.onBackPressed();
            }

            @Override
            public void negative() {
                isSave = false;
                PublishActivity.super.onBackPressed();
            }
        };
    }

    private void initActivity() {
        images.clear();
        Bundle b = getIntent().getExtras();
        title = b.getString("title");
    }

    private void setViews() {
        title_tv.setText(title);
        publish_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInfo();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pictureNum<3){
                    pictureDialog.showDialog(PublishActivity.this, "照片", "选择获取照片的方式" ,"相册" ,"相机");
                }else{
                    pictureNum = 3;
                    ToastTool.showToast(PublishActivity.this,"上传图片已满");
                }
            }
        });
        publish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInfo();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addImageView(Bitmap bitmap) {
        final View view = LayoutInflater.from(this).inflate(R.layout.publish_picture, null, false);
        ImageView picture = (ImageView) view.findViewById(R.id.pic_picture);
        ImageView delete = (ImageView) view.findViewById(R.id.pic_delete);
        picture.setImageBitmap(bitmap);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadOK) {
                    PhotoActivity.setInfo(images, LoopViewPagerFragment.TYPE_URL, false);
                    Intent i = new Intent(PublishActivity.this, PhotoActivity.class);
                    startActivity(i);
                } else {
                    ToastTool.showToast(PublishActivity.this, "图片正在上传");
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAndpic_ll.removeView(view);
                int num = sort(view);
                images.remove(num);
                imageUrls.remove(num);
                bitmaps.remove(num);
                pictureNum--;
            }
        });
        cameraAndpic_ll.addView(view);
        pictureNum++;
        uploadBitmap(bitmap);
    }

    private int sort(View view){
        int left = view.getLeft();
        if(left < DensityTool.dip2px(this, 100)){
            return 0;
        }else if(left < DensityTool.dip2px(this, 200)){
            return 1;
        }else{
            return 2;
        }
    }

    private void findViews() {
        publish_iv = (TextView) findViewById(R.id.actionbar_publish);
        back = (ImageView) findViewById(R.id.actionbar_back);
        cameraAndpic_ll = (LinearLayout) findViewById(R.id.camera_picture);
        camera = (ImageView) findViewById(R.id.activity_publish_camera);
        publish_b = (Button) findViewById(R.id.activity_publish_publish);
        name_et = (EditText) findViewById(R.id.activity_publish_name);
        words_et = (EditText) findViewById(R.id.activity_publish_words);
        money_et = (EditText) findViewById(R.id.activity_publish_request_money);
        title_tv = (TextView) findViewById(R.id.actionbar_text);
        good_et = (EditText) findViewById(R.id.activity_publish_request_good);
        service_et = (EditText) findViewById(R.id.activity_publish_request_service);
        type_tv = (TextView) findViewById(R.id.activity_publish_type);
        school_tv = (TextView) findViewById(R.id.activity_publish_school);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!isEixt && isSave){

            someToString();

            if(name!=null)
                SharedPrefsUtil.putValue("name", name);
            if(words!=null)
                SharedPrefsUtil.putValue("words", words);
            if(money!=null)
                SharedPrefsUtil.putValue("money", money);
            if(good!=null)
                SharedPrefsUtil.putValue("good", good);
            if(service!=null)
                SharedPrefsUtil.putValue("service", service);
            if(school!=null)
                SharedPrefsUtil.putValue("school", school);
            if(type!=null)
                SharedPrefsUtil.putValue("type", type);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isSave){
            if(bitmaps!=null && bitmaps.size()!=0){
                for (int i=0; i<bitmaps.size(); i++){
                    addImageView(bitmaps.get(i));
                }
            }

            name = SharedPrefsUtil.getValue("name", "");
            name_et.setText(name);

            words = SharedPrefsUtil.getValue("words", "");
            words_et.setText(words);

            money = SharedPrefsUtil.getValue("money", "");
            money_et.setText(money);

            good = SharedPrefsUtil.getValue("good", "");
            good_et.setText(good);

            service = SharedPrefsUtil.getValue("service", "");
            service_et.setText(service);

            school = SharedPrefsUtil.getValue("school", "");
            school_tv.setText(school);

            type = SharedPrefsUtil.getValue("type", "");
            type_tv.setText(type);
        }else{
            images.clear();
            bitmaps.clear();
        }
    }

    @Override
    public void onBackPressed() {
        if(exitDialog!=null){
            exitDialog.showDialog(this, "是否保存信息", "是否保存信息以供下次发布？", "保存", "算了");
        }
    }

    /**
     * 上传从相册选择的图片
     * @param result
     */
    private void uploadBitmap(final Bitmap result) {
        uploadOK = false;
        QiniuUploadUitls.getInstance().uploadImage(result,new QiniuUploadUitls.QiniuUploadUitlsListener() {

            @Override
            public void onSucess(String fileUrl) {
                uploadOK = true;
                totalNum = pictureNum==3?2:pictureNum;
                imageUrls.add(TextTool.file2url(fileUrl));
                images.add(new Image(imageUrls.get(totalNum-1)));
                bitmaps.add(result);
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int errorCode, String msg) {
                ToastTool.showToast(PublishActivity.this, "errorCode=" + errorCode + ",msg=" + msg);
            }
        });
    }

    private void someToString(){
        name = name_et.getText().toString();
        words = words_et.getText().toString();
        money = money_et.getText().toString();
        good = good_et.getText().toString();
        service = service_et.getText().toString();
    }

    private String urls2string(List<String> urls){
        StringBuffer faceAll = new StringBuffer();
        for (int i=0; i<urls.size(); i++){
            faceAll.append(urls.get(i));
            if(i!=urls.size()-1){
                faceAll.append("，");
            }
        }
        return faceAll.toString();
    }

    private void checkInfo(){

        someToString();

        if(TextUtils.isEmpty(name)){
            ToastTool.showToast(this,"名称不能为空");
            return;
        }
        name = TextTool.word2use(name);

        if(TextUtils.isEmpty(words)){
            ToastTool.showToast(this,"描述不能为空");
            return;
        }
        words = TextTool.word2use(words);

        if(TextUtils.isEmpty(money) && TextUtils.isEmpty(good) && TextUtils.isEmpty(service)){
            ToastTool.showToast(this,"不想换点什么？");
            return;
        }
        money = TextTool.word2use(money);
        good = TextTool.word2use(good);
        service = TextTool.word2use(service);
    }

    private void uploadInfo(){

        checkInfo();

        String url = String.format(puclishURL, 1/*type*/, user.getId(), name, words, "xidian"/*school*/, money, service, good, urls2string(imageUrls), pictureNum);

        Log.e("123", "url------>"+url);

        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            isEixt = true;
                            ToastTool.showToast(PublishActivity.this, "发布成功");
                        }else{
                            ToastTool.showToast(PublishActivity.this, "发布失败");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(PublishActivity.this, "网络有问题~~");
            }
        });

        addRequestToQueen(jor);

    }

}
