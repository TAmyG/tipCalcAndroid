package com.distinct.tamyg.tipcalc;

import android.app.Application;

/**
 * Created by tamyg on 4/06/16.
 */
public class TipCalcApp extends Application{
    private final static String ABOUT_URL = "http://oktacore.com/";

    public  String getAboutUrl() {
        return ABOUT_URL;
    }
}
