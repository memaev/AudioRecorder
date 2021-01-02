package mik.maev.audiorecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private ImageView icon;
    private Animation topAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        icon = findViewById(R.id.icon);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);

        icon.setAnimation(topAnim);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Load().execute();
    }

    private class Load extends AsyncTask<Void, Integer, Void> {
        int progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = 0;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(progress < 100){
                progress++;
                publishProgress(progress);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent (SplashScreen.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
}