package com.yhd.think.leyi.fragment;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.yhd.think.leyi.tools.QiniuUploadUitls;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Think on 2015/5/19.
 */
public class BaseRecordFragment extends BaseFragment {

    protected MediaRecorder recorder;
    private File tempFile;

    protected void initRecorder(File path){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            tempFile = File.createTempFile("tempFile", ".mp3", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.setOutputFile(tempFile.getAbsolutePath());
    }

    protected void startRecorder(){
        try {
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                return;
            }
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getAmrDuration(File file) throws IOException {
        long duration = -1;
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0,
                0, 0 };
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();
            int pos = 6;
            int frameCount = 0;
            int packedPos = -1;

            byte[] datas = new byte[1];
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }

            duration += frameCount * 20;// ֡��*20
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return (int)(((duration/1000)+1)/10);
    }

    protected void stopRecorder(){
        if(recorder != null){
            recorder.stop();
            recorder.release();// �ͷ���Դ
            recorder = null;
        }
        try {
            Log.e("123", getAmrDuration(tempFile)+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        super.onDestroy();
    }

    public File getTempFile() {
        return tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }
}
