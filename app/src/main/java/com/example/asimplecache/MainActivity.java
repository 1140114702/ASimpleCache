package com.example.asimplecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.main_image);

        long startTime = timeFormat("2017-3-8 0:00:00");
        long endTime = timeFormat("2017-3-9 00:00:00");
        long currentTime = System.currentTimeMillis();
        ACache cache = ACache.get(this);
        Bitmap image = cache.getAsBitmap("coverImg");

        if (currentTime > startTime && currentTime < endTime) {
            //在这个规定的时间内
            if (image == null) {
                cache.put("coverImg", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_home));
                imageView.setImageResource(R.mipmap.ic_home);
            } else {
                imageView.setImageBitmap(image);
            }
        } else {
            //不在这个规定的时间内
            imageView.setImageResource(R.mipmap.ic_launcher);
            if (cache.getAsBitmap("coverImg") != null) {
                cache.remove("coverImg");
            }else {
                Toast.makeText(this, "图片缓存已删除", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //时间转换成时间戳
    public long timeFormat(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return date.getTime();
        } else {
            return 0;
        }
    }
}
