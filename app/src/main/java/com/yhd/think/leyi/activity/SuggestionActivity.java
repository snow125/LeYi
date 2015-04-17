package com.yhd.think.leyi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;

import org.json.JSONObject;

public class SuggestionActivity extends BaseActivity {

    private String suggestionURL = Constants.BASE_URL+"advise.action?userId=%s&context=%s";
    private User user = User.getInstance();
    private ImageView back;
    private EditText words_et;
    private String words;
    //private EditText tel;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        findViews();
        setListeners();
    }

    private void setListeners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSuggestion();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadSuggestion(){
        words = words_et.getText().toString();
        if(TextUtils.isEmpty(words)){
            ToastTool.showToast(SuggestionActivity.this, "意见用户名不能为空");
            return;
        }else{
            words = TextTool.word2use(words);
        }
        String url = String.format(suggestionURL, user.getId(), words);

        JsonObjectRequest jor = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jo = null;
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            ToastTool.showToast(SuggestionActivity.this, "提交成功");
                            finish();
                        }else{
                            ToastTool.showToast(SuggestionActivity.this, "提交失败");
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(SuggestionActivity.this, "网络有问题~~");
            }
        });
        addRequestToQueen(jor);
    }

    private void findViews() {
        back = (ImageView) findViewById(R.id.actionbar_back);
        words_et = (EditText) findViewById(R.id.activity_suggestion_words);
        //tel = (EditText) findViewById(R.id.activity_suggestion_tel);
        submit = (Button) findViewById(R.id.activity_suggestion_submit);
    }

}