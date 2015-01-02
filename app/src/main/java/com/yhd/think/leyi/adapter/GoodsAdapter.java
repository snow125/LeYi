package com.yhd.think.leyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snow on 2015/1/2.
 */
public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Goods> mGoods = new ArrayList<Goods>();

    public GoodsAdapter(Context context, List<Goods> mGoods) {
        this.context = context;
        this.mGoods = mGoods;
        this.mInflater = LayoutInflater.from(context);
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
            vh.picture = convertView.findViewById(R.id.goods_picture);
            vh.name = (TextView) convertView.findViewById(R.id.goods_name);
            vh.request = (TextView) convertView.findViewById(R.id.goods_request);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.picture.setBackgroundResource(mGoods.get(position).getBitmapResouceId());
        vh.name.setText(mGoods.get(position).getName());
        vh.request.setText(mGoods.get(position).getRequest());
        return convertView;
    }

    private class ViewHolder{
        View picture;
        TextView name;
        TextView request;
    }

}
