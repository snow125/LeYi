package com.yhd.think.leyi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.fragment.GoodsFragment;
import com.yhd.think.leyi.fragment.PublishFragment;
import com.yhd.think.leyi.fragment.RankFragment;
import com.yhd.think.leyi.fragment.ServeceFragment;
import com.yhd.think.leyi.fragment.SettingFragment;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.view.NetworkCircleImageView;

public class MineActivity extends BaseActivity {

    private User user = User.getInstance();
    private NetworkCircleImageView face_iv;
    private TextView word_tv;
    private TextView name_tv;
    private TextView sell_list;
    private TextView save_list;
    private TextView publishGood_list;
    private TextView publishService_list;
    private Button exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        findViews();
        setViews();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViews() {
        Bundle b = getIntent().getExtras();
        String face;
        String name;
        String word;
        boolean isMine = b.getBoolean("ismine");
        if(isMine == true){
            face = user.getFaceUrl();
            name = user.getName();
            word = user.getWords();
        }else{
            face = b.getString("face");
            name = b.getString("name");
            word = b.getString("word");
        }

        //face_iv.setBackground(new BitmapDrawable(user.getFace()));
        face_iv.setImageUrl(face, ImageCacheManager.getInstance().getImageLoader());
        word_tv.setText(word);
        name_tv.setText(name);
        face_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(MineActivity.this, LoginActivity.class);
                startActivityForResult(i, LoginActivity.SUCCESS);*/
            }
        });
        sell_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MineActivity.this, ListActivity.class);
                i.putExtra("title","已交易");
                startActivity(i);
            }
        });
        save_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MineActivity.this, ListActivity.class);
                i.putExtra("title","收藏");
                startActivity(i);
            }
        });
        publishGood_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MineActivity.this, ListActivity.class);
                i.putExtra("title","正发布商品");
                startActivity(i);
            }
        });
        publishService_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MineActivity.this, ListActivity.class);
                i.putExtra("title", "正发布服务");
                startActivity(i);
            }
        });
        if(!isMine){
            save_list.setVisibility(View.GONE);
            exitLogin.setVisibility(View.GONE);
        }else{
            save_list.setVisibility(View.VISIBLE);
            exitLogin.setVisibility(View.VISIBLE);
        }
        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isLogin()){
                    user.setLogin(false);
                    /**
                     * 更新登录显示
                     */
                    SettingFragment.updateInfo();
                    GoodsFragment.updateInfo();
                    ServeceFragment.updateInfo();
                    PublishFragment.updateInfo();
                    RankFragment.updateInfo();
                    finish();
                }
            }
        });
    }

    private void findViews() {
        name_tv = (TextView) findViewById(R.id.fragment_mine_name);
        exitLogin = (Button) findViewById(R.id.activity_mine_exit);
        face_iv = (NetworkCircleImageView) findViewById(R.id.fragment_mine_face);
        word_tv = (TextView) findViewById(R.id.fragment_mine_word);
        sell_list = (TextView) findViewById(R.id.fragment_mine_sell);
        save_list = (TextView) findViewById(R.id.fragment_mine_shop);
        publishGood_list = (TextView) findViewById(R.id.fragment_mine_good);
        publishService_list = (TextView) findViewById(R.id.fragment_mine_servece);
    }

}
