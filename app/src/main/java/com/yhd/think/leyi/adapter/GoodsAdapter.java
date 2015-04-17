package com.yhd.think.leyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Goods;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.TextTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by snow on 2015/1/2.
 */
public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Goods> mGoods = new ArrayList<Goods>();
    private int type;

    public GoodsAdapter(Context context, List<Goods> mGoods, int type) {
        this.context = context;
        this.mGoods = mGoods;
        this.mInflater = LayoutInflater.from(context);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return mGoods.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_goodsfragment_lv, null, false);
            vh = new ViewHolder();
            vh.picture = (NetworkImageView)convertView.findViewById(R.id.goods_picture);
            vh.name = (TextView) convertView.findViewById(R.id.goods_name);
            vh.request = (TextView) convertView.findViewById(R.id.goods_request);
            vh.info = (TextView) convertView.findViewById(R.id.time_click_distance);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.picture.setImageUrl(TextTool.url2face(mGoods.get(position).getImageUrl()), ImageCacheManager.getInstance().getImageLoader());
        vh.picture.setErrorImageResId(R.drawable.photo_wrong);
        vh.picture.setDefaultImageResId(R.drawable.content_loading_large);
        vh.name.setText(mGoods.get(position).getName());
        vh.request.setText(mGoods.get(position).getRequest());
        switch (type){
            case Constants.TYPE_CLICK:
                vh.info.setText("浏览量："+mGoods.get(position).getClickNum());
                break;
            case Constants.TYPE_TIME:
                Date date = mGoods.get(position).getTime();
                vh.info.setText("发布时间：" + date.getYear()+"年"+date.getMonth() + "月" + date.getDay()+"日");
                break;
            case Constants.TYPE_PRETTY:
                vh.info.setText("点赞量："+mGoods.get(position).getPretty());
                break;
            case Constants.TYPE_NULL:
                vh.info.setVisibility(View.GONE);
                break;
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(mGoods, new Comparator<Goods>() {
            @Override
            public int compare(Goods lhs, Goods rhs) {
                switch (type){
                    case Constants.TYPE_CLICK:
                        return (int)(-lhs.getClickNum()+rhs.getClickNum());
                    case Constants.TYPE_TIME:
                        return lhs.getTime().after(rhs.getTime())?-1:1;
                    case Constants.TYPE_PRETTY:
                        return (int)(-lhs.getPretty()+rhs.getPretty());
                    default:
                        return 0;
                }
            }
        });
        super.notifyDataSetChanged();
    }

    private class ViewHolder{
        NetworkImageView picture;
        TextView name;
        TextView request;
        TextView info;
    }

}
