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

public class SuggestionActivity extends BaseActivity {

    private ImageView back;
    private EditText words;
    private EditText tel;
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
        back = (ImageView) findViewById(R.id.actionbar_back);
        words = (EditText) findViewById(R.id.activity_suggestion_words);
        tel = (EditText) findViewById(R.id.activity_suggestion_tel);
        submit = (Button) findViewById(R.id.activity_suggestion_submit);
    }

}