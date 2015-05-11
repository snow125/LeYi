package com.yhd.think.leyi.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.yhd.think.leyi.R;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_conversation, new ConversationFragment())
                .commit();
    }

}
