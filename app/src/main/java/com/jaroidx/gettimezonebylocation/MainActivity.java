package com.jaroidx.gettimezonebylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jaroidx.gettimezonebylocation.llttz.Converter;
import com.jaroidx.gettimezonebylocation.llttz.IConverter;
import com.jaroidx.gettimezonebylocation.llttz.stores.TimeZoneListStore;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IConverter iconv = Converter.getInstance(TimeZoneListStore.class,this);
        TimeZone tz = iconv.getTimeZone(59.612751993769336, 95.16262643653218);
        Log.d("TAG", "onCreate: "+tz.getID());
    }
}