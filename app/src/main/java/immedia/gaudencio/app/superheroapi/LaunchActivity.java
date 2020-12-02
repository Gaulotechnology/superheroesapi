package immedia.gaudencio.app.superheroapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import immedia.gaudencio.app.superheroapi.R;

public class LaunchActivity extends AppCompatActivity {
    // Splash screen timer
    ImageView imageView;
    private static int SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        imageView=(ImageView)findViewById(R.id.imageView1);
        Animation an2= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        imageView.startAnimation(an2);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }}