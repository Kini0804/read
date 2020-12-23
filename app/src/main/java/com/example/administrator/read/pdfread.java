package com.example.administrator.read;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class pdfread extends AppCompatActivity implements OnPageChangeListener {
    String SAMPLE_FILE ;
    String article_url;
    PDFView pdfView;
    Integer pageNumber = 0;
    Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfpage);
        handler=new Handler();
        Intent intent=getIntent();
        article_url=intent.getStringExtra("url");
        String book_name=intent.getStringExtra("article_name");
        SAMPLE_FILE=book_name+".pdf";
        pdfView=(PDFView) findViewById(R.id.pdfView);
        setcontext();
    }
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
    private void setcontext() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL(article_url);
                    System.out.println(article_url);
                    HttpURLConnection connect=(HttpURLConnection)url.openConnection();
                    final InputStream input=connect.getInputStream();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pdfView.fromStream(input)
                                    .defaultPage(pageNumber)
                                    .onPageChange(pdfread.this)
                                    .enableAnnotationRendering(true)
                                    .scrollHandle(new DefaultScrollHandle(pdfread.this))
                                    .spacing(10) // in dp
                                    .load();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
