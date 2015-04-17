package com.yhd.think.leyi.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yhd.think.leyi.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    public static final int SEARCH_FINISH = 123;

    private ImageView back;
    private TextView search_tv;
    private EditText search_et;
    private ListView searchRecord;
    private ArrayAdapter<String> adapter;
    private boolean isCreate = true;

    private static List<String> recordData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViews();
        setLstener();
    }

    private void setLstener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCreate){
                    recordData.add(search_et.getText().toString());
                    adapter.notifyDataSetChanged();
                }
                setResult(SEARCH_FINISH);
                finish();
            }
        });
        searchRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search_et.setText(recordData.get(position));
                isCreate = false;
            }
        });
    }

    private void findViews() {
        back = (ImageView) findViewById(R.id.actionbar_back);
        search_tv = (TextView) findViewById(R.id.activity_search_search);
        search_et = (EditText) findViewById(R.id.activity_search_editext);
        searchRecord = (ListView) findViewById(R.id.activity_search_listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordData);
        TextView footerView = new TextView(this);
        footerView.setText("清除全部记录");
        footerView.setGravity(Gravity.CENTER);
        footerView.setTextSize(20);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordData.clear();
                adapter.notifyDataSetChanged();
            }
        });
        searchRecord.addFooterView(footerView);
        searchRecord.setAdapter(adapter);
    }

}
