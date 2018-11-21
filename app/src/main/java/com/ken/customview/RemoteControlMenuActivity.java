package com.ken.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RemoteControlMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control_menu);

        RemoteControlMenu menu = findViewById(R.id.menu);
        menu.setListener(new RemoteControlMenu.MenuListener() {
            @Override
            public void onCenterCliched() {
                Toast.makeText(RemoteControlMenuActivity.this, "onCenterCliched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpCliched() {
                Toast.makeText(RemoteControlMenuActivity.this, "onUpCliched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCliched() {
                Toast.makeText(RemoteControlMenuActivity.this, "onRightCliched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownCliched() {
                Toast.makeText(RemoteControlMenuActivity.this, "onDownCliched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftCliched() {
                Toast.makeText(RemoteControlMenuActivity.this, "onLeftCliched", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
