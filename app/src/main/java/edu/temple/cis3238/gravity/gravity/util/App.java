package edu.temple.cis3238.gravity.gravity.util;

import android.app.Application;
import android.content.Context;

/**
 * This class allows access to the application context from any class
 *
 * @author Brett Crawford
 * @version 1.0a last modified 4/17/2015
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
