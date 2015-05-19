package com.yhd.think.leyi.fragment;



import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.MainActivity;
import com.yhd.think.leyi.activity.PhotoActivity;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Image;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DensityTool;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.QiniuUploadUitls;
import com.yhd.think.leyi.tools.TextTool;
import com.yhd.think.leyi.tools.ToastTool;
import com.yhd.think.leyi.view.CircleImageView;
import com.yhd.think.leyi.view.NetworkCircleImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author snow
 *
 */
public class PublishFragment extends BaseTakePhotoFragment {

    private View rootView;
    private static NetworkCircleImageView menu;
    private static final String puclishURL = Constants.BASE_URL+"addItem.action?wutypeId=%s&userId=%s&wuDesrc=%s&hopAddr=%s&hopeMoney=%s&wuPic=%s&ImageNum=%s";
    private EditText words_et;
    private EditText request_et;
    private String words;
    private String description;
    private TextView title_tv;
    private String title;
    private Button publish_b;
    private ImageView camera;
    private LinearLayout cameraAndpic_ll;
    private TextView publish_iv;
    private ImageView back;
    private DialogTool pictureDialog, exitDialog;
    private static boolean isSave = false;
    private int pictureNum;
    private static User user = User.getInstance();
    private List<String> imageUrls = new ArrayList<String>();
    private static List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private int totalNum;
    private boolean uploadOK;
    private static List<Image> images = new ArrayList<Image>();
    //    private String type, school;
//    private TextView type_tv, school_tv;
    private boolean isEixt;
    private CircleImageView record;
    private String path;
    private File recordFile;
    private boolean isRecord;
    private boolean mp3UploadOK;
    private String mpsURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_publish, container, false);
        findViews();
        init();
        initDialog();
        setViews();
        return rootView;
    }

    private void init() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    private void initDialog() {
        pictureDialog = new DialogTool() {
            @Override
            public void positive() {
                gallery();
            }

            @Override
            public void negative() {
                camera();
            }
        };
        exitDialog = new DialogTool() {
            @Override
            public void positive() {
                isSave = true;
                getActivity().onBackPressed();
            }

            @Override
            public void negative() {
                isSave = false;
                getActivity().onBackPressed();
            }
        };
    }

    private void setViews() {
        //title_tv.setText(title);
        publish_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInfo();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pictureNum<3){
                    pictureDialog.showDialog(getActivity(), "照片", "选择获取照片的方式" ,"相册" ,"相机");
                }else{
                    pictureNum = 3;
                    ToastTool.showToast(getActivity(),"上传图片已满");
                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRecord){
                    //Log.e("123", "false");
                    initRecorder(new File(path));
                    startRecorder();
                    isRecord = true;
                }else{
                    Log.e("123", "true");
                    isRecord = false;
                    stopRecorder();
                    uploadFile(getTempFile());
//                    try {
//                        ToastTool.showToast(getActivity(),""+getAmrDuration(getTempFile()));
//                        Log.e("123", getTempFile().getAbsolutePath());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
    }

    private void uploadFile(File file) {
        mp3UploadOK = false;
        QiniuUploadUitls.getInstance().uploadImage(file.getPath(),new QiniuUploadUitls.QiniuUploadUitlsListener() {

            @Override
            public void onSucess(String fileUrl) {
                mp3UploadOK = true;
                mpsURL = TextTool.file2url(fileUrl);
                Log.e("123", mpsURL);
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int errorCode, String msg) {

            }
        });
    }

    private int sort(View view){
        int left = view.getLeft();
        if(left < DensityTool.dip2px(getActivity(), 100)){
            return 0;
        }else if(left < DensityTool.dip2px(getActivity(), 200)){
            return 1;
        }else{
            return 2;
        }
    }

    private void findViews() {
        //publish_iv = (TextView) rootView.findViewById(R.id.actionbar_publish);
        //back = (ImageView) rootView.findViewById(R.id.actionbar_back);
        cameraAndpic_ll = (LinearLayout) rootView.findViewById(R.id.camera_picture);
        camera = (ImageView) rootView.findViewById(R.id.activity_publish_camera);
        publish_b = (Button) rootView.findViewById(R.id.activity_publish_publish);
        words_et = (EditText) rootView.findViewById(R.id.activity_publish_description);
        //title_tv = (TextView) rootView.findViewById(R.id.actionbar_text);
        request_et = (EditText) rootView.findViewById(R.id.activity_publish_request_good);
        menu = (NetworkCircleImageView) rootView.findViewById(R.id.actionbar_menu);
        record = (CircleImageView) rootView.findViewById(R.id.record);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        if(!isEixt && isSave){
//
//            someToString();
//
//            if(words!=null)
//                SharedPrefsUtil.putValue("words", words);
//            if(description!=null)
//                SharedPrefsUtil.putValue("money", description);
//        }
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(isSave){
//            if(bitmaps!=null && bitmaps.size()!=0){
//                for (int i=0; i<bitmaps.size(); i++){
//                    addImageView(bitmaps.get(i));
//                }
//            }
//
//            words = SharedPrefsUtil.getValue("words", "");
//            words_et.setText(words);
//
//            description = SharedPrefsUtil.getValue("money", "");
//            request_et.setText(description);
//
//        }else{
//            images.clear();
//            bitmaps.clear();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        if(exitDialog!=null){
//            exitDialog.showDialog(getActivity(), "是否保存信息", "是否保存信息以供下次发布？", "保存", "算了");
//        }
//    }

    /**
     * 上传从相册选择的图片
     * @param result
     */
    private void uploadBitmap(final Bitmap result) {
        uploadOK = false;
        QiniuUploadUitls.getInstance().uploadImage(result,new QiniuUploadUitls.QiniuUploadUitlsListener() {

            @Override
            public void onSucess(String fileUrl) {
                uploadOK = true;
                totalNum = pictureNum==3?2:pictureNum;
                imageUrls.add(TextTool.file2url(fileUrl));
                images.add(new Image(imageUrls.get(totalNum-1)));
                bitmaps.add(result);
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int errorCode, String msg) {
                ToastTool.showToast(getActivity(), "errorCode=" + errorCode + ",msg=" + msg);
            }
        });
    }

    private void addImageView(Bitmap bitmap) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.publish_picture, null, false);
        ImageView picture = (ImageView) view.findViewById(R.id.pic_picture);
        ImageView delete = (ImageView) view.findViewById(R.id.pic_delete);
        picture.setImageBitmap(bitmap);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadOK) {
                    PhotoActivity.setInfo(images, LoopViewPagerFragment.TYPE_URL, false);
                    Intent i = new Intent(getActivity(), PhotoActivity.class);
                    startActivity(i);
                } else {
                    ToastTool.showToast(getActivity(), "图片正在上传");
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAndpic_ll.removeView(view);
                int num = sort(view);
                images.remove(num);
                imageUrls.remove(num);
                bitmaps.remove(num);
                pictureNum--;
            }
        });
        cameraAndpic_ll.addView(view);
        pictureNum++;
        uploadBitmap(bitmap);
    }

    private void someToString(){
        words = words_et.getText().toString();
    }

    private String urls2string(List<String> urls){
        StringBuffer faceAll = new StringBuffer();
        for (int i=0; i<urls.size(); i++){
            faceAll.append(urls.get(i));
            if(i!=urls.size()-1){
                faceAll.append("，");
            }
        }
        return faceAll.toString();
    }

    private void checkInfo(){

        someToString();

        if(TextUtils.isEmpty(words)){
            ToastTool.showToast(getActivity(),"描述不能为空");
            return;
        }
        words = TextTool.word2use(words);

        if(TextUtils.isEmpty(description)){
            ToastTool.showToast(getActivity(),"名称不能为空");
            return;
        }
        description = TextTool.word2use(description);
    }

    private void uploadInfo(){

        checkInfo();

        if(!uploadOK || !mp3UploadOK){
            ToastTool.showToast(getActivity(),"正在上传，请稍等……");
            return;
        }

        String url = String.format(puclishURL, 1/*type*/, user.getId(), words, "xidian"/*school*/, description,urls2string(imageUrls), pictureNum);

        Log.e("123", "url------>" + url);

        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if(jsonObject.optInt("status",-1) == Constants.JSON_OK){
                            isEixt = true;
                            ToastTool.showToast(getActivity(), "发布成功");
                        }else{
                            ToastTool.showToast(getActivity(), "发布失败");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastTool.showToast(getActivity(), "网络有问题~~");
            }
        });

        ((MainActivity)getActivity()).addRequestToQueen(jor);

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PublishFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PublishFragment");
    }

    public static void updateInfo(){
        if(user.isLogin()){
            if(menu!=null){
                menu.setImageUrl(user.getFaceUrl(), ImageCacheManager.getInstance().getImageLoader());
                menu.setErrorImageResId(R.drawable.anonymous_icon);
                menu.setDefaultImageResId(R.drawable.anonymous_icon);
            }
        }else{
            if(menu!=null){
                menu.setImageUrl(null, ImageCacheManager.getInstance().getImageLoader());
                menu.setErrorImageResId(R.drawable.anonymous_icon);
                menu.setDefaultImageResId(R.drawable.anonymous_icon);
            }
        }
    }

    @Override
    protected void dealBitmap(Bitmap bitmap) {
        addImageView(bitmap);
    }

}
