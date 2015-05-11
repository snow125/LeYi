package com.yhd.think.leyi.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.BaseActivity;
import com.yhd.think.leyi.activity.GoodsDetailActivity;
import com.yhd.think.leyi.activity.SearchActivity;
import com.yhd.think.leyi.adapter.GoodsAdapter;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Goods;
import com.yhd.think.leyi.data.GoodsDetail;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;
import com.yhd.think.leyi.view.LoadingFooter;
import com.yhd.think.leyi.view.NetworkCircleImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author snow
 *
 */
public class GoodsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static User user = User.getInstance();
    private String getGoodURL = Constants.BASE_URL+"getAll.action?nowPage=%s&pageSize=" + Constants.PAGE_SIZE;
    private String getGoodByTypeURL = Constants.BASE_URL+"getByType.action?typeId=%s&nowPage=%s&pageSize=" + Constants.PAGE_SIZE;
    private String getGoodDetailURL = Constants.BASE_URL+"getItem.action?&wuId=%s";
    private String updateClickNumURL = Constants.BASE_URL+"updateClick.action?wuId=%s";
    private View rootView;
    private ListView mListView;
    private GoodsAdapter adapter;
    private static List<Goods> mGoods = new ArrayList<Goods>();
    private static NetworkCircleImageView menu;
    private View search;
    private SwipeRefreshLayout swipeLayout;
    private int type = Constants.TYPE_CLICK;
    private RelativeLayout type_iv, location_iv, sort_iv;
    //private LinearLayout mLinearLayout;
    public static PopupWindow sortPop;
    private PopupWindow addressPop, typePop;
    private ListView city_lv, type_lv;
    private String city;
    private int goodType = 7;
    private RelativeLayout time_rl, click_rl, distance_rl;
    private ImageView time, click, distance;
    private TextView location_tv;
    private int mPage;
    private int mPage_type;
    private LoadingFooter mFooter;
    private View emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_goods, container, false);
       //initData();
       initListView();
       //initPull();
       initSortPop();
       initAddressPop();
       initTypePop();
       initViews();
       loadNextPageByCom();
       return rootView;
    }

    private void initTypePop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_type, null);
        typePop = new PopupWindow(view, android.app.ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, false);
        typePop.setOutsideTouchable(true);
        typePop.setFocusable(true);
        type_lv = (ListView) view.findViewById(R.id.pop_addr_listview);
        final String[] typeDatas = new String[]{"书籍","手机/配件","电脑/配件","电器","服装","其他","查看全部商品"};
        type_lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,typeDatas));
        type_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goodType = position + 1;
                typePop.dismiss();
                if(goodType != 7){
                    mGoods.clear();
                    adapter.notifyDataSetChanged();
                    mPage_type = 0;
                    loadNextPageByType(goodType);
                }else{
                    mGoods.clear();
                    adapter.notifyDataSetChanged();
                    mPage = 0;
                    loadNextPageByCom();
                }
            }
        });



    }

    private void initAddressPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_address, null);
        addressPop = new PopupWindow(view, android.app.ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, false);
        addressPop.setOutsideTouchable(true);
        addressPop.setFocusable(true);
        location_tv = (TextView) view.findViewById(R.id.pop_addr_location);
        city_lv = (ListView) view.findViewById(R.id.pop_addr_listview);
        //final String[] cityDatas = new String[]{"西安","天津","北京","上海","广州","深圳","武汉"};
        city_lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Constants.collageData));
        city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = Constants.collageData[position];
                addressPop.dismiss();
            }
        });
        location_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initSortPop(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_sort, null);
        sortPop = new PopupWindow(view, android.app.ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT, false);
        sortPop.setOutsideTouchable(true);
        sortPop.setFocusable(false);
        time_rl = (RelativeLayout) view.findViewById(R.id.pop_sort_time_rl);
        time = (ImageView) view.findViewById(R.id.pop_sort_time);
        time_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode(1);
            }
        });
        click_rl = (RelativeLayout) view.findViewById(R.id.pop_sort_click_rl);
        click = (ImageView) view.findViewById(R.id.pop_sort_click);
        click_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode(0);
            }
        });
        distance_rl = (RelativeLayout) view.findViewById(R.id.pop_sort_distance_rl);
        distance = (ImageView) view.findViewById(R.id.pop_sort_distance);
        distance_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode(2);
            }
        });
    }

    private void setAllViewsGone(){
        time.setVisibility(View.GONE);
        click.setVisibility(View.GONE);
        distance.setVisibility(View.GONE);
    }

    private void selectMode(int position){
        setAllViewsGone();
        switch (position){
            case 0:
                click.setVisibility(View.VISIBLE);
                type = Constants.TYPE_CLICK;
                break;
            case 1:
                time.setVisibility(View.VISIBLE);
                type = Constants.TYPE_TIME;
                break;
            case 2:
                distance.setVisibility(View.VISIBLE);
                type = Constants.TYPE_DISTANCE;
                break;
        }
        adapter.setType(type);
        adapter.notifyDataSetChanged();
        sortPop.dismiss();
    }

    private void initViews() {
        menu = (NetworkCircleImageView) rootView.findViewById(R.id.actionbar_menu);
        menu.setImageUrl(user.getFaceUrl(), ImageCacheManager.getInstance().getImageLoader());
        menu.setErrorImageResId(R.drawable.photo_wrong);
        menu.setDefaultImageResId(R.drawable.content_loading_large);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalMenu.switchMenu();
            }
        });

        search = rootView.findViewById(R.id.actionbar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(i, SearchActivity.SEARCH_FINISH);
            }
        });
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        type_iv = (RelativeLayout) rootView.findViewById(R.id.fragment_goods_type);
        type_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!typePop.isShowing()){
                    typePop.showAsDropDown(rootView.findViewById(R.id.sort));
                }else{
                    typePop.dismiss();
                }
            }
        });
       // location_iv = (RelativeLayout) rootView.findViewById(R.id.fragment_goods_location);
        location_iv = (RelativeLayout) rootView.findViewById(R.id.fragment_goods_location);
        location_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addressPop.isShowing()){
                    addressPop.showAsDropDown(rootView.findViewById(R.id.sort));
                }else{
                    addressPop.dismiss();
                }
            }
        });
        sort_iv = (RelativeLayout) rootView.findViewById(R.id.fragment_goods_sort);
        sort_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sortPop.isShowing()){
                    sortPop.showAsDropDown(rootView.findViewById(R.id.sort));
                }else{
                    sortPop.dismiss();
                }
            }
        });
        updateInfo();
    }

    private void parseJSONByType(int type, final int page){
        String url = String.format(getGoodByTypeURL, type, page);
        parseJSON(url, page);
    }

    private void parseJSONByCom(final int page){
        String url = String.format(getGoodURL,page);
        parseJSON(url, page);
    }

    private void parseJSON(String url, final int page) {
        JsonObjectRequest jor = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if(goodType != 7){
                            mPage_type = page;
                        }else{
                            mPage = page;
                        }
                        JSONArray ja = null;
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            ja = jsonObject.optJSONArray("content");
                            for(int i=0;i<ja.length();i++){

                                Goods g = new Goods(
                                        ja.optJSONObject(i).optString("wuId"),
                                        ja.optJSONObject(i).optString("wuPic"),
                                        ja.optJSONObject(i).optString("wuName"),
                                        ja.optJSONObject(i).optString("hopeMoney"),
                                        ja.optJSONObject(i).optInt("clicknum"),
                                        string2date(ja.optJSONObject(i).optString("wuTime"))
                                );
                                mGoods.add(g);
                            }

                            mFooter.setState(LoadingFooter.State.Idle, 3000);
                            adapter.notifyDataSetChanged();
                        }else{
                            mFooter.setState(LoadingFooter.State.TheEnd);
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(getActivity(), "网络有问题~~");
                mFooter.setState(LoadingFooter.State.Idle, 3000);
            }
        });
        ((BaseActivity)getActivity()).addRequestToQueen(jor);
    }

    private Date string2date(String time){
        Date d = new Date(Integer.valueOf(time.substring(0, 4)), Integer.valueOf(time.substring(5,7)), Integer.valueOf(time.substring(8,10)));
        return d;
    }

    private void initListView() {
        mGoods.clear();

        mListView = (ListView) rootView.findViewById(R.id.fragment_goods_listview);
        mFooter = new LoadingFooter(getActivity());
        mListView.addFooterView(mFooter.getView());
        adapter = new GoodsAdapter(getActivity(), mGoods, type);
        mListView.setAdapter(adapter);
        emptyView = (View) rootView.findViewById(R.id.textView_empty);
        mListView.setEmptyView(emptyView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(GoodsFragment.sortPop!=null && GoodsFragment.sortPop.isShowing()){
                    GoodsFragment.sortPop.dismiss();
                }else{
                    int goodId = Integer.valueOf(mGoods.get(position).getId());
                    updateClickNum(goodId);
                    parseGoodDetail(goodId);
                }

            }
        });
        //adapter.notifyDataSetChanged();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (mFooter.getState() == LoadingFooter.State.Loading
                        || mFooter.getState() == LoadingFooter.State.TheEnd) {
                    return;
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != mListView.getHeaderViewsCount()
                        + mListView.getFooterViewsCount() && mListView.getCount() > 0) {
                    if(goodType != 7){
                        loadNextPageByType(goodType);
                    }else{
                        loadNextPageByCom();
                    }
                }
            }
        });
    }

    private void updateClickNum(int goodID){
        String url = String.format(updateClickNumURL, goodID);
        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            Log.e("123", "1");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ((BaseActivity)getActivity()).addRequestToQueen(jor);
    }

    private void parseGoodDetail(int goodID){
        String url = String.format(getGoodDetailURL, goodID);
        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jo = null;
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            jo = jsonObject.optJSONObject("content");
                            int num = jo.optInt("imageNum");

                            //Log.e("123", jo.optString("wuPic"));

                            GoodsDetail gd = new GoodsDetail(
                                    jo.optString("face"),
                                    jo.optString("userName"),
                                    //"hello world",
                                    jo.optString("memo"),
                                    jo.optString("tel"),
                                    jo.optString("hopeMoney"),
                                    jo.optString("hopeFuwu"),
                                    jo.optString("hopeWu"),
                                    jo.optString("wuDescr"),
                                    jo.optString("wuName"),
                                    num,
                                    string2array(num, jo.optString("wuPic"))
                            );

                            GoodsDetailActivity.goodDetail(gd);

                            Intent i = new Intent(getActivity(), GoodsDetailActivity.class);
                            //i.putExtra("name",mGoods.get(position).getName());
                            //i.putExtra("request",mGoods.get(position).getRequest());
                            startActivity(i);

                        }else{
                            ToastTool.showToast(getActivity(), "加载出错~~");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(getActivity(), "网络有问题~~");
            }
        });
        ((BaseActivity)getActivity()).addRequestToQueen(jor);
    }

    private String[] string2array(int num, String urlArray){
        String[] urls = new String[num];
        switch (num){
            case 1:
                urls[0] = urlArray;
                break;
            case 2:
                urls[0] = urlArray.substring(0,urlArray.lastIndexOf("；"));
                urls[1] = urlArray.substring(urlArray.lastIndexOf("；")+1);
                break;
            case 3:
                urls[0] = urlArray.substring(0,urlArray.indexOf("；"));
                urls[1] = urlArray.substring(urlArray.indexOf("；")+1,urlArray.lastIndexOf("；"));
                urls[2] = urlArray.substring(urlArray.lastIndexOf("；")+1);
                break;
        }
        return array2use(urls);
    }

    private String[] array2use(String[] urlArray){
        for (int i=0; i<urlArray.length; i++){
            urlArray[i] = TextTool.url2face(urlArray[i]);
        }
        return urlArray;
    }

    private void loadNextPageByType(int type){
        mFooter.setState(LoadingFooter.State.Loading);
        parseJSONByType(type, mPage_type+1);
    }

    private void loadNextPageByCom() {
        mFooter.setState(LoadingFooter.State.Loading);
        parseJSONByCom(mPage+1);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GoodsFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GoodsFragment");
    }

    public static void updateInfo(){
        if(user.isLogin()){
            if(menu!=null){
                menu.setImageUrl(user.getFaceUrl(), ImageCacheManager.getInstance().getImageLoader());
                menu.setErrorImageResId(R.drawable.anonymous_icon_yuan);
                menu.setDefaultImageResId(R.drawable.anonymous_icon_yuan);
            }
        }else{
            if(menu!=null){
                menu.setBackgroundResource(R.drawable.anonymous_icon_yuan);
                menu.setErrorImageResId(R.drawable.anonymous_icon_yuan);
                menu.setDefaultImageResId(R.drawable.anonymous_icon_yuan);
            }
        }
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }
}
