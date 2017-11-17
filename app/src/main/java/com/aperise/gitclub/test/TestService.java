package com.aperise.gitclub.test;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.aperise.gitclub.provider.GitclubContent;
import com.aperise.gitclub.provider.GitclubProvider;
import com.aperise.gitclub.settings.Settings;
import com.aperise.gitclub.utils.SLog;
import com.aperise.gitclub.widget.TextView;

public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler handler = new Handler();
    ContentObserver observer = new ContentObserver(handler) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d("TestService", "service----------------------------------------------------------");
            Log.d("TestService", "ContentObserver onChange() uri=" + uri);

            String email = Settings.getString(TestService.this, "email");
            SLog.d(TestService.this, "get value email =" + email);

            float height = Settings.getFloat(TestService.this, "height", -1);
            SLog.d(TestService.this, "get value height =" + height);

            int age = Settings.getInt(TestService.this, "age", -1);
            SLog.d(TestService.this, "get value age =" + age);

            long money = Settings.getLong(TestService.this, "money", -1);
            SLog.d(TestService.this, "get value money =" + money);
            Log.d("TestService", "service----------------------------------------------------------");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Uri uri = Settings.getUriFor("email");
        Log.d("TestService", "url=" + uri);
        getContentResolver().registerContentObserver(uri, false, observer);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("TestService", "onDestroy()");
        getContentResolver().unregisterContentObserver(observer);
        super.onDestroy();
    }
}
