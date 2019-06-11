package com.example.user.things;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2019-05-28.
 */

public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final Button inventoryButton = (Button) findViewById(R.id.inventoryButton);
        final Button shopButton = (Button) findViewById(R.id.shopButton);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inventoryButton = new Intent(FirstActivity.this, MainActivity.class);
                FirstActivity.this.startActivity(inventoryButton);
            }
        });
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://emart.ssg.com/"));
                startActivity(myIntent);
            }
        });
    }
}
