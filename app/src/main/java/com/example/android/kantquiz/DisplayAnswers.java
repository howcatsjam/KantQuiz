package com.example.android.kantquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayAnswers extends AppCompatActivity {

    private String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_answers);

        Intent intent = getIntent();
        results = intent.getStringExtra(MainActivity.EXPORT_ANSWERS);

        TextView textView = findViewById(R.id.test_summary);
        textView.setText(results);
    }

    public void shareResults(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, results);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.shareResults)));
    }


}
