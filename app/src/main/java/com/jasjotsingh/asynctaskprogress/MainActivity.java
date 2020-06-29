package com.jasjotsingh.asynctaskprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.michelelacorte.elasticprogressbar.ElasticDownloadView;
import it.michelelacorte.elasticprogressbar.ProgressDownloadView;

public class MainActivity extends AppCompatActivity {
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ElasticDownloadView elasticDownloadView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.asyncTask);
        imageView = findViewById(R.id.image);
        elasticDownloadView = findViewById(R.id.elastic_download_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("https://www.tutorialspoint.com/images/tp-logo-diamond.png");
            }
        });

    }
    private class AsyncTaskExample extends AsyncTask<String,String,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            elasticDownloadView.startIntro();

        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                elasticDownloadView.success();
                imageView.setImageBitmap(bitmap);
            }else {
                elasticDownloadView.fail();
            }
        }
    }
}