package com.yhd.think.leyi.activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.User;

public class RegisterActivity extends BaseActivity {

    private User user = User.getInstance();
    private ImageView back;
    private EditText name_et;
    private String name;
    private EditText password_et;
    private String password;
    private EditText repeatPass_et;
    private String repeatPass;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setViews();
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
                setResult(LoginActivity.SUCCESS);
                finish();
            }
        });
    }

    private void findViews() {
        register = (Button) findViewById(R.id.activity_register_register);
        name_et = (EditText) findViewById(R.id.activity_register_name);
        password_et = (EditText) findViewById(R.id.activity_register_password);
        repeatPass_et = (EditText) findViewById(R.id.activity_register_repeat_password);
        back = (ImageView) findViewById(R.id.actionbar_back);
    }

}
