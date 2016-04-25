package me.gitai.kanban.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.Random;

import me.gitai.kanban.Constant;
import me.gitai.kanban.data.QQMessage;

/**
 * Created by i@gitai.me on 16-4-23.
 */
public class MainActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);


        findPreference("about_app_version").setSummary(
                String.format("%s %s(%d)", BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
    }
}
