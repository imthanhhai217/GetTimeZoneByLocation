package com.jaroidx.gettimezonebylocation.llttz;

import android.content.Context;

import com.jaroidx.gettimezonebylocation.llttz.stores.Location;
import com.jaroidx.gettimezonebylocation.llttz.stores.TimeZoneStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

/**
 * @author artemgapchenko
 * Created on 18.04.14.
 */
public class Converter implements IConverter {
    private TimeZoneStore tzStore;
    private static Converter instance = null;
    private static Context mContext;

    private Converter(Class clazz) {
        if (!TimeZoneStore.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Illegal store provided: " + clazz.getName());
        }
        try {
            tzStore = (TimeZoneStore) clazz.newInstance();
            loadData();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Converter getInstance(final Class clazz, Context context) {
        mContext = context;
        if (instance == null || !instance.getStoreClass().equals(clazz)) instance = new Converter(clazz);
        return instance;
    }

    public Class getStoreClass() {
        return tzStore.getClass();
    }

    @Override
    public TimeZone getTimeZone(final double lat, final double lon) {
        return tzStore.nearestTimeZone(new Location(new double[]{lat, lon}));
    }

    private void loadData() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mContext.getAssets().open("timezones.csv"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            String[] location;

            while ((line = br.readLine()) != null) {
                location = line.split(";");
                tzStore.insert(new Location(Double.valueOf(location[1]), Double.valueOf(location[2]), location[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}