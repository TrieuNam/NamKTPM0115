package com.example.admin.cocaro2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
      int id_amthanh;
    android.media.SoundPool amthanh =new android.media.SoundPool(1,android.media.AudioManager.STREAM_MUSIC,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        id_amthanh = amthanh.load(this,R.raw.multimedia,1);
    }

    public void StartGame_Online(View view) {
        amthanh.play(id_amthanh,1.0f,1.0f,1,0,1.0f);
        Intent intent = new Intent(getApplicationContext(), OnlineLoginActivity.class);
        startActivity(intent);
    }

    public void ShowAboutNote(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    public void EndGame(View view) {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }
}
