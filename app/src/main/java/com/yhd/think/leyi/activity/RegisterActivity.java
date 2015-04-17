package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.QiniuUploadUitls;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;
import com.yhd.think.leyi.view.CircleImageView;
import com.yhd.think.leyi.view.NetworkCircleImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseTakePhotoActivity {

    private String checkNameUrl = Constants.BASE_URL + "valid.action?userName=%s";
    private String registerUrl = Constants.BASE_URL + "register.action?userName=%s&pwd=%s&tel=%s&face=%s&memo=%s&sex=%s&address=%s&school=%s";
    private User user = User.getInstance();
    private ImageView back;
    private EditText name_et;
    private String name;
    private EditText password_et;
    private String password;
    private EditText repeatPass_et;
    private CircleImageView head;
    private String repeatPass;
    private Button register;
    private boolean uploadOK;
    private boolean once = true;
    private String face;
    private DialogTool pictureDialog;
    private RadioGroup sec_rg;
    private RadioButton male_r;
    private RadioButton female_r;
    private int sex;
    private EditText tel_et;
    private String tel;
    private EditText words_et;
    private String word;
    private TextView addr_tv;
    private String addr;
    private TextView school_tv;
    private String school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setViews();
        initDialog();
    }

    @Override
    protected void dealBitmap(Bitmap bitmap) {
        head.setImageBitmap(bitmap);
        uploadBitmap(bitmap);
    }

    private void setViews() {
        name = name_et.getText().toString();
        password = password_et.getText().toString();
        repeatPass = repeatPass_et.getText().toString();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadOK){
                    checkName();
                }else{
                    ToastTool.showToast(RegisterActivity.this, "等待图片上传~");
                }
            }
        });
        //head.setErrorImageResId(R.drawable.anonymous_icon);
        //head.setDefaultImageResId(R.drawable.anonymous_icon);
        head.setBackgroundResource(R.drawable.anonymous_icon);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(once){
                    pictureDialog.showDialog(RegisterActivity.this, "照片", "选择获取照片的方式" ,"相册" ,"相机");
                }
            }
        });
        addr_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addr = "西安";
            }
        });
        school_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school = "西电";
            }
        });
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

    private void findViews() {
        register = (Button) findViewById(R.id.activity_register_register);
        name_et = (EditText) findViewById(R.id.activity_register_name);
        password_et = (EditText) findViewById(R.id.activity_register_password);
        repeatPass_et = (EditText) findViewById(R.id.activity_register_repeat_password);
        back = (ImageView) findViewById(R.id.actionbar_back);
        head = (CircleImageView) findViewById(R.id.activity_register_head);
        sec_rg = (RadioGroup) findViewById(R.id.activity_register_choose);
        male_r = (RadioButton) findViewById(R.id.activity_register_man);
        female_r = (RadioButton) findViewById(R.id.activity_register_woman);
        tel_et = (EditText) findViewById(R.id.activity_register_tel);
        words_et = (EditText) findViewById(R.id.activity_register_word);
        addr_tv = (TextView) findViewById(R.id.activity_register_add);
        school_tv = (TextView) findViewById(R.id.activity_register_school);
    }

    /**
     * 上传从相册选择的图片
     * @param result
     */
    private void uploadBitmap(Bitmap result) {
        once = false;
        QiniuUploadUitls.getInstance().uploadImage(result,new QiniuUploadUitls.QiniuUploadUitlsListener() {

            @Override
            public void onSucess(String fileUrl) {
                uploadOK = true;
                face = TextTool.file2url(fileUrl);
                Log.e("123", "face---->"+face);
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int errorCode, String msg) {
                ToastTool.showToast(RegisterActivity.this, "errorCode=" + errorCode + ",msg=" + msg);
            }
        });
    }

    public void checkName(){
        name = name_et.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastTool.showToast(RegisterActivity.this, "用户名不能为空");
            return;
        }else{
            name = TextTool.word2use(name);
        }

        String url = String.format(checkNameUrl, name, password);

        JsonObjectRequest jor = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jo = null;
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            register();
                        }else{
                            ToastTool.showToast(RegisterActivity.this, "用户名已注册");
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(RegisterActivity.this, "网络有问题~~");
            }
        });
        addRequestToQueen(jor);
    }

    private void register(){

        if(male_r.isChecked()){
            sex = 0;
        }else if(female_r.isChecked()){
            sex = 1;
        }else{
            ToastTool.showToast(RegisterActivity.this, "性别啊。。。。");
            return;
        }

        if(!checkPassword()){
            ToastTool.showToast(RegisterActivity.this, "密码不一样。。。");
            return;
        }

        tel = tel_et.getText().toString();

        if(TextUtils.isEmpty(tel)){
            ToastTool.showToast(RegisterActivity.this, "请输入电话号码~");
            return;
        }

        if(!isNumeric(tel)){
            ToastTool.showToast(RegisterActivity.this, "请输入电话号码~");
            return;
        }

        word = words_et.getText().toString();

        if(TextUtils.isEmpty(word)){
            ToastTool.showToast(RegisterActivity.this, "请输入宣言~");
            return;
        }else{
            word = TextTool.word2use(word);
        }

        if(addr == null){
            ToastTool.showToast(RegisterActivity.this, "请选择地址~");
            return;
        }else{
            addr = TextTool.word2use(addr);
        }

        if(school == null){
            ToastTool.showToast(RegisterActivity.this, "请选择地址~");
            return;
        }else{
            school = TextTool.word2use(school);
        }

        String url = String.format(registerUrl,name,password,tel,face,word,sex,addr,school);

        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jo = null;
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            jo = jsonObject.optJSONObject("content");

                            String face = TextTool.url2face(jo.optString("face"));

                            user.setFaceUrl(face);
                            String word = jo.optString("memo");
                            if(word == null){
                                word = "你的换客宣言呢？！";
                            }
                            user.setWords(word);
                            user.setTel(jo.optString("tel"));
                            user.setPassword(jo.optString("pwd"));
                            user.setName(TextTool.urlDecode(jo.optString("userName")));
                            user.setId(jo.optInt("userId"));
                            user.setLogin(true);
                            setResult(LoginActivity.SUCCESS);
                            finish();
                        }else{
                            ToastTool.showToast(RegisterActivity.this, "注册失败");
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(RegisterActivity.this, "网络有问题~~");
            }
        });
        addRequestToQueen(jor);
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    private boolean checkPassword() {
        password = password_et.getText().toString();
        repeatPass = repeatPass_et.getText().toString();
        if(password.equals(repeatPass)){
            return true;
        }else{
            return false;
        }
    }

}
