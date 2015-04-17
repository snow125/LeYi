package com.yhd.think.leyi.data;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author snow
 */
public class User {

    private static Context context;
    private String name;
    private String words;
    private Bitmap face;
    private String faceUrl;
    private boolean isLogin;
    private String city;
    private String tel;
    private String password;
    private int id;

    private static User user;

    public static void init(Context _context){
        context = _context;
    }

    private User(){
        isLogin = loadBoolean(Constants.SP_LOGIN);
        if(isLogin()){
            name = loadString(Constants.SP_NAME);
            words = loadString(Constants.SP_WORD);
            faceUrl = loadString(Constants.SP_FACEURL);
            face = getFace();
            id = loadInt(Constants.SP_ID);
            tel = loadString(Constants.SP_TEL);
            password = loadString(Constants.SP_PASSWORD);
        }
    }

    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        save(Constants.SP_ID, id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        save(Constants.SP_PASSWORD, password);
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        save(Constants.SP_TEL, tel);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
        save(Constants.SP_LOGIN, isLogin);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        save(Constants.SP_NAME, name);
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
        save(Constants.SP_WORD, words);
    }

    public Bitmap getFace() {
        return face;
    }

    public void setFace(Bitmap face) {
        this.face = face;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
        save(Constants.SP_FACEURL, faceUrl);
    }

    private void save(String tag,String data){
        context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit().putString(tag, data).commit();
    }
    private void save(String tag,boolean data){
        context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit().putBoolean(tag, data).commit();
    }

    private void save(String tag,int data){
        context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit().putInt(tag, data).commit();
    }

    private void save(String tag,long data){
        context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit().putLong(tag, data).commit();
    }

    private String loadString(String tag){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getString(tag,"");
    }

    private String loadString(String tag,String defValue){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getString(tag,defValue);
    }

    private boolean loadBoolean(String tag){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getBoolean(tag,false);
    }
    private boolean loadBoolean(String tag,boolean defValue){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getBoolean(tag,defValue);
    }

    private int loadInt(String tag){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getInt(tag,0);
    }

    private int loadInt(String tag,int defValue){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getInt(tag,defValue);
    }

    private long loadLong(String tag){
        return context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).getLong(tag,0L);
    }

}
