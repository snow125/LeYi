package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.User;

public class LoginActivity extends BaseActivity {

    public final static int SUCCESS = 1;
    private User user = User.getInstance();
    private String name;
    private EditText name_et;
    private String password;
    private EditText password_et;
    private Button login_b;
    private ImageView back;
    private ImageView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setViews();
    }

    private void setViews() {
        name = name_et.getText().toString();
        password = password_et.getText().toString();
        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(SUCCESS);
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUCCESS){
            if(resultCode == SUCCESS){
                finish();
            }
        }
    }

    private void findViews() {
        register = (ImageView) findViewById(R.id.actionbar_register);
        back = (ImageView) findViewById(R.id.actionbar_back);
        login_b = (Button) findViewById(R.id.activity_login_login);
        name_et = (EditText) findViewById(R.id.activity_login_name);
        password_et = (EditText) findViewById(R.id.activity_login_password);
    }

}
