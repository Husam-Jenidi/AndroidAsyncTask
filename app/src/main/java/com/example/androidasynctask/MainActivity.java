package com.example.androidasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button asyncBtn,localBtn,randomBtn;
    private TextView  TextView1,TextView2,TextView3;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncBtn = findViewById(R.id.asyncBtn);
        localBtn = findViewById(R.id.localBtn);
        randomBtn = findViewById(R.id.randomStr);

        TextView1 = findViewById(R.id.textView1);
        TextView2 = findViewById(R.id.textView2);
        TextView3 = findViewById(R.id.textView3);


        asyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AsyncTask
                new MyAsyncTask().execute();
            }
        });
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the local task executed by the UI thread
                performLocalTask();
            }
        });
        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView3.setText(getRandomString(10));

            }
        });
    }

    private void performLocalTask() {
        // Perform a long computation on the UI thread
        for (int i = 0; i < 4; i++) {
            // Simulate a 1-second delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the progress in the text view
            int progress = (i + 1) * 25;
            TextView2.setText(progress + "%");
        }

        // Task completed
        TextView2.setText("Local task completed");
    }
    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Perform the long computation in the background
            for (int i = 0; i < 4; i++) {
                // Simulate a 1-second delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update the progress
                int progress = (i + 1) * 25;
                publishProgress(progress);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update the progress in the text view
            int progress = values[0];
            TextView2.setText(progress + "%");
        }

        @Override
        protected void onPostExecute(Void avoid) {
            // Task completed
            TextView2.setText("Async task completed");
        }
    }
}

