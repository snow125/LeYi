package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Goods;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;
import com.yhd.think.leyi.view.LoadingFooter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    public final static int SUCCESS = 1;
    private User user = User.getInstance();
    private String name;
    private EditText name_et;
    private String password;
    private EditText password_et;
    private Button login_b;
    private ImageView back;
    private TextView register;

    private String loginUrl = Constants.BASE_URL + "login.action?userName=%s&pwd=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setViews();
    }

    private void setViews() {

        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSON();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(i, SUCCESS);
            }
        });
    }

    private void parseJSON() {
        name = name_et.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastTool.showToast(LoginActivity.this, "用户名不能为空");
            return;
        }else{
            name = TextTool.word2use(name);
        }

        password = password_et.getText().toString();
        if(TextUtils.isEmpty(password)){
            ToastTool.showToast(LoginActivity.this, "密码不能为空");
            return;
        }else{
            password = TextTool.word2use(password);
        }

        String url = String.format(loginUrl, name, password);

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
                            setResult(SUCCESS);
                            finish();
                        }else{
                            ToastTool.showToast(LoginActivity.this, "登录失败");
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(LoginActivity.this, "网络有问题~~");
            }
        });
        addRequestToQueen(jor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUCCESS){
            if(resultCode == SUCCESS){
                //finish();
            }
        }
    }

    private void findViews() {
        register = (TextView) findViewById(R.id.actionbar_register);
        back = (ImageView) findViewById(R.id.actionbar_back);
        login_b = (Button) findViewById(R.id.activity_login_login);
        name_et = (EditText) findViewById(R.id.activity_login_name);
        password_et = (EditText) findViewById(R.id.activity_login_password);
    }

}
