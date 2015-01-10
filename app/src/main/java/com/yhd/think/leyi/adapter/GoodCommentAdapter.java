package com.yhd.think.leyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by snow on 2015/1/6.
 */
public class GoodCommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> comments;
    private LayoutInflater mInflater;

    public GoodCommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_good_comment_lv,null,false);
            vh = new ViewHolder();
            vh.picture = convertView.findViewById(R.id.comment_owner_picture);
            vh.name = (TextView) convertView.findViewById(R.id.comment_owner_name);
            vh.comment = (TextView) convertView.findViewById(R.id.comment_owner_comments);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.picture.setBackgroundResource(comments.get(position).getOwnerPic());
        vh.name.setText(comments.get(position).getOwnerName());
        vh.comment.setText(comments.get(position).getOwnerConmment());
        return convertView;
    }

    private class ViewHolder{
        View picture;
        TextView comment;
        TextView name;
    }

}
