package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.adapter.GoodCommentAdapter;
import com.yhd.think.leyi.data.Comment;
import com.yhd.think.leyi.data.GoodsDetail;
import com.yhd.think.leyi.data.Image;
import com.yhd.think.leyi.fragment.LoopViewPagerFragment;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.MessageDialog;
import com.yhd.think.leyi.tools.TelDialog;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.view.ListViewInScroll;
import com.yhd.think.leyi.view.NetworkCircleImageView;
import com.yhd.think.leyi.view.SuspendScrollView;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailActivity extends BaseActivity implements SuspendScrollView.OnScrollListener {

    private static final String MESSAGE = "#乐易#";

    private List<Image> images = new ArrayList<Image>();
    private ImageView back;
    private ImageView save;
    private boolean hasSave;
    private TextView goodName;
    private TextView goodDecription;
    private TextView goodRequest;
    private ImageView goodShare;
    private static GoodsDetail good;
    private NetworkCircleImageView ownerPic;
    private TextView ownerName;
    private TextView ownerWords;
    private ImageView ownerTel_iv;
    private String ownerTel;
    private ImageView ownerMessage;
    private TextView watchComment;
    private ListViewInScroll comments;
    private boolean isSeeComment = true;
    private GoodCommentAdapter mGoodCommentAdapter;
    private List<Comment> ownerComments = new ArrayList<Comment>();
    private ImageView wantComment;
    private int indicateNum;
    private String[] imageUrls;
    private SuspendScrollView mScrollView;
    private LinearLayout topInfoView;
    private LinearLayout infoView;
    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private RelativeLayout toUser_tl;
    private LoopViewPagerFragment mPagerFragment;
    private MessageDialog messageDialog;
    private TelDialog telDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        findViews();
        initScrollView();
        initDialog();
        initData();
        initComments();
        initUmeng();
        setViews();
        initViewFragment();
    }

    private void initViewFragment() {
        LoopViewPagerFragment.setInfo(images, LoopViewPagerFragment.TYPE_URL, false);
        mPagerFragment = new LoopViewPagerFragment();
        mPagerFragment.setUse(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loop_view_pager, mPagerFragment)
                .commit();
    }

    private void initDialog() {
        messageDialog = new MessageDialog(this, ownerTel, MESSAGE);
        telDialog = new TelDialog(this, ownerTel);
    }

    private void initScrollView() {

        mScrollView = (SuspendScrollView) findViewById(R.id.activity_detail_scrollview);
        topInfoView = (LinearLayout) findViewById(R.id.top_info_view);
        infoView = (LinearLayout) findViewById(R.id.info_view);
        mScrollView.setOnScrollListener(this);
        findViewById(R.id.activity_detail_parent).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(mScrollView.getScrollY());
                infoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, topInfoView.getHeight()));
            }
        });
    }

    private void initComments() {
        ownerComments.clear();
        Comment mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
    }

    public static void goodDetail(GoodsDetail _good){
        good = _good;
    }

    private void initData(){
        ownerTel = good.getOwnerTel();
        imageUrls = good.getImageUrls();
        indicateNum = good.getImageNum();
        setImages(imageUrls);
    }

    private void setImages(String[] imageUrls){
        for (int i=0;i<imageUrls.length;i++){
            images.add(new Image(imageUrls[i]));
        }
    }

    private void findViews(){
        toUser_tl = (RelativeLayout) findViewById(R.id.activity_detail_user);

        goodName = (TextView) findViewById(R.id.goods_name);
        goodDecription = (TextView) findViewById(R.id.goods_words);
        goodShare = (ImageView) findViewById(R.id.goods_share);
        goodRequest = (TextView) findViewById(R.id.goods_request);
        ownerPic = (NetworkCircleImageView) findViewById(R.id.owner_picture);
        ownerName = (TextView) findViewById(R.id.owner_name);
        ownerWords = (TextView) findViewById(R.id.owner_words);
        ownerTel_iv = (ImageView) findViewById(R.id.onwer_tel);
        ownerMessage = (ImageView) findViewById(R.id.onwer_message);
        watchComment = (TextView) findViewById(R.id.activity_detail_watch_comments);
        comments = (ListViewInScroll) findViewById(R.id.activity_detail_listview_comment);
        wantComment = (ImageView) findViewById(R.id.goods_want_comment);

        save = (ImageView) findViewById(R.id.activity_detail_save);
        back = (ImageView) findViewById(R.id.activity_detail_back);
    }

    private void initUmeng() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }

    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx967daebe835fbeac";
        String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    private void addQQQZonePlatform() {
        String appId = "100424468";
        String appKey = "c7394704798a158208a74ab60104f0ba";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();

    }
    private void setViews(){
        ownerPic.setImageUrl(TextTool.url2face(good.getOwnerImageUrl()), ImageCacheManager.getInstance().getImageLoader());
        ownerPic.setErrorImageResId(R.drawable.photo_wrong);
        ownerPic.setDefaultImageResId(R.drawable.content_loading_large);
        goodName.setText(good.getName());
        goodRequest.setText(good.getMoney());
        goodDecription.setText(good.getDescription());
        ownerName.setText(good.getOwnerName());
        ownerWords.setText(good.getOwnerSign());
        goodShare .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA);
                mController.openShare(GoodsDetailActivity.this,false);
            }
        });
        ownerTel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telDialog.show();
            }
        });
        ownerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.show();
            }
        });
        watchComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSeeComment){
                    comments.setVisibility(View.VISIBLE);
                    watchComment.setText("隐藏评论");
                }else{
                    comments.setVisibility(View.GONE);
                    watchComment.setText("查看评论");
                }
                isSeeComment = !isSeeComment;
            }
        });
        wantComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GoodsDetailActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSave = !hasSave;
                if(hasSave){
                    save.setBackgroundResource(R.drawable.collected);
                }else{
                    save.setBackgroundResource(R.drawable.detail_save);
                }
            }
        });

        mGoodCommentAdapter = new GoodCommentAdapter(this,ownerComments);
        comments.setAdapter(mGoodCommentAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toUser_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GoodsDetailActivity.this, MineActivity.class);
                i.putExtra("face",good.getOwnerImageId());
                i.putExtra("name",good.getOwnerName());
                i.putExtra("word",good.getOwnerSign());
                i.putExtra("ismine",false);
                startActivity(i);
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, infoView.getTop());
        topInfoView.layout(0, mBuyLayout2ParentTop, topInfoView.getWidth(), mBuyLayout2ParentTop + topInfoView.getHeight());
    }

}
