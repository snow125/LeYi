package com.yhd.think.leyi.tools;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by snow on 2015/2/1.
 */
public class TextTool {

    public static String url2face(String fileUrl){
        return fileUrl.replace(" ", "%20");
    }

    public static String file2url(String fileUrl){
        String url = fileUrl.replace("leyiapp.qiniudn.com","7vikyu.com1.z0.glb.clouddn.com");
        url = url.replace(" ", "%20");
        return url;
    }

    public static String word2use(String inStr){
        return space2add(urlEncode(inStr));
    }

    public static String space2add(String inStr){
        char[] inChar = inStr.toCharArray();
        char[] midChar = new char[inChar.length];
        boolean once = false;
        int i,j;
        for(i=0,j=0;i<inChar.length;i++){
            midChar[j] = inChar[i];
            if(inChar[i]==' '){
                if(!once){
                    midChar[j] = '+';
                    once = true;
                    j++;
                }
            }else{
                once = false;
                j++;
            }
        }
        char[] outChar = new char[inChar.length-(i-j)];
        for (i=0;i<outChar.length;i++){
            outChar[i] = midChar[i];
        }
        return new String(outChar);
    }

    public static String urlEncode(String url){
        String result = null;
        try {
            result = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String urlDecode(String string){
        String result = null;
        try {
            result = URLDecoder.decode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
