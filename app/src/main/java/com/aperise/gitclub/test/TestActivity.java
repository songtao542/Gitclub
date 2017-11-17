package com.aperise.gitclub.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aperise.gitclub.R;
import com.aperise.gitclub.data.AccessTokenAccessor;
import com.aperise.gitclub.data.UserAccessor;
import com.aperise.gitclub.data.UserTokenStore;
import com.aperise.gitclub.model.User;
import com.aperise.gitclub.settings.Settings;
import com.aperise.gitclub.utils.SLog;

import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private Random random;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mUserTokenStore = new UserTokenStore(new AccessTokenAccessor(this), new UserAccessor(this));

        random = new Random(System.currentTimeMillis());
        i = random.nextInt(100);
    }

    public void insert(View view) {
        User user = new User();
        user.email = "songtao54" + i + "@gmail.com";
        user.name = "songtao54" + i;
//        saveUser(user);


        Settings.putString(this, "email", user.email);
        Settings.putFloat(this, "height", Math.abs(random.nextInt(200) - i));
        Settings.putInt(this, "age", Math.abs(i - random.nextInt(50)));
        Settings.putLong(this, "money", i + random.nextInt(10000));

        i++;
    }

    private UserTokenStore mUserTokenStore;

    private void saveUser(User user) {
        SLog.d(TestActivity.this, "saveUser to database user=" + user);
        mUserTokenStore.insertOrUpdateUserByEmail(user);
        long id = mUserTokenStore.queryUserKeyByEmail(user.email);
        mUserTokenStore.updateTokenUserKeyByEmail(user.email, id);
        mUserTokenStore.storeUser(user);
    }

    public void getValue(View view) {

        Log.d("TestService", "activity----------------------------------------------------------");
        String email = Settings.getString(this, "email");
        SLog.d(TestActivity.this, "get value email =" + email);

        float height = Settings.getFloat(this, "height", -1);
        SLog.d(TestActivity.this, "get value height =" + height);

        int age = Settings.getInt(this, "age", -1);
        SLog.d(TestActivity.this, "get value age =" + age);

        long money = Settings.getLong(this, "money", -1);
        SLog.d(TestActivity.this, "get value money =" + money);
        Log.d("TestService", "activity----------------------------------------------------------");
    }
}
