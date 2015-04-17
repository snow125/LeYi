package com.yhd.think.leyi.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yhd.think.leyi.R;

public class CommentActivity extends BaseActivity {

    private EditText comment_et;
    private String comment;
    private Button submit;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        findViews();
        setViews();
    }

    private void setViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews() {
        comment_et = (EditText) findViewById(R.id.activity_comment_words);
        back = (ImageView) findViewById(R.id.actionbar_back);
        submit = (Button) findViewById(R.id.activity_comment_submit);
    }

}
