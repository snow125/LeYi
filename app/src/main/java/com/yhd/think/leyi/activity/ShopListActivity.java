package com.yhd.think.leyi.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.yhd.think.leyi.R;

public class ShopListActivity extends BaseActivity {

    private ImageView back;
    private ListView shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
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
    }

    private void findViews() {
        back = (ImageView) findViewById(R.id.actionbar_back);
        shopList = (ListView) findViewById(R.id.activity_shop_listview);
    }

}
