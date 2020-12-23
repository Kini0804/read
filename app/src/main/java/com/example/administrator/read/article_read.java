package com.example.administrator.read;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.example.administrator.read.internet.internet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class article_read extends AppCompatActivity {
    String SAMPLE_FILE ;
    int book_id;
    Handler handler;
    ArrayList<String> chapter_context=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new Handler();
        Intent intent=getIntent();
        book_id=intent.getIntExtra("book_id",0);
        String book_name=intent.getStringExtra("book_name");
        SAMPLE_FILE=book_name+".txt";
        setcontext();

    }

    private void setcontext() {
        final String urlStr="http://122.114.237.201/read/chapters";
        new Thread(){
            @Override
            public void run() {
                String result= internet.getbookinfo(urlStr,book_id);

                if (result.equals(""))
                {
                    Looper.prepare();
                    Toast.makeText(article_read.this,"请求失败！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else{
                    try {
                        JSONObject result_json=new JSONObject(result);
                        JSONArray chapters=result_json.getJSONArray("chapters");
                        for (int i = 0; i < chapters.length(); i++) {
                            JSONObject object=chapters.getJSONObject(i);
                            chapter_context.add(object.getString("chapterContext"));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                copyAssetAndWrite(SAMPLE_FILE);
                                File dataFile=new File(getCacheDir(),SAMPLE_FILE);
                                String path=dataFile.getAbsolutePath();
                                TxtConfig.saveIsOnVerticalPageMode(article_read.this,false);
                                HwTxtPlayActivity.loadTxtFile(article_read.this,path);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                }

            }
        }.start();
    }

    private boolean copyAssetAndWrite(String fileName){
        try {
            File cacheDir=this.getCacheDir();
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            File outFile =new File(cacheDir,fileName);
            if (!outFile.exists()){
                boolean res=outFile.createNewFile();
                if (!res){
                    return false;
                }
            }else {
                if (outFile.length()>10){//表示已经写入一次

                }
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,"gbk"));
            for (int i = 0; i < chapter_context.size(); i++) {
                writer.write(chapter_context.get(i));
            }
            writer.close();
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
