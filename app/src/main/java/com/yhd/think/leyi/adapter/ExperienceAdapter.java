package com.yhd.think.leyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Experience;
import com.yhd.think.leyi.data.Goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by snow on 2015/1/26.
 */
public class ExperienceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Experience> mExperiences = new ArrayList<Experience>();
    private int type;

    public ExperienceAdapter(Context context, List<Experience> mExperiences, int type) {
        this.context = context;
        this.mExperiences = mExperiences;
        this.mInflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public int getCount() {
        return mExperiences.size();
    }

    @Override
    public Object getItem(int position) {
        return mExperiences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_rankfragment_sort, parent, false);
            vh = new ViewHolder();
            vh.sellGood = (ImageView) convertView.findViewById(R.id.sell_good);
            vh.shopGood = (ImageView) convertView.findViewById(R.id.shop_good);
            vh.info = (TextView) convertView.findViewById(R.id.experience_info);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.sellGood.setBackgroundResource(mExperiences.get(position).getSellGoodID());
        vh.shopGood.setBackgroundResource(mExperiences.get(position).getShopGoodID());
        switch (type){
            case Constants.TYPE_TIME:
                Date date = mExperiences.get(position).getTime();
                vh.info.setText("发布时间：" + date.getYear()+"年"+date.getMonth() + "月" + date.getDay()+"日");
                break;
            case Constants.TYPE_PRETTY:
                vh.info.setText("点赞量：" + mExperiences.get(position).getPretty());
                break;
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(mExperiences, new Comparator<Experience>() {
            @Override
            public int compare(Experience lhs, Experience rhs) {
                switch (type) {
                    case Constants.TYPE_TIME:
                        return lhs.getTime().after(rhs.getTime()) ? -1 : 1;
                    case Constants.TYPE_PRETTY:
                        return (int) (-lhs.getPretty() + rhs.getPretty());
                    default:
                        return 0;
                }
            }
        });
        super.notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView sellGood;
        ImageView shopGood;
        TextView info;
    }

}
