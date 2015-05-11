package com.yhd.think.leyi.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.yhd.think.leyi.R;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_conversation_list, new ConversationFragment())
                .commit();
    }

}
