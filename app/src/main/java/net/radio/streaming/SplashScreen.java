package net.radio.streaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import net.radio.streaming.utils.JsonParams;
import net.radio.streaming.utils.RemoteJSONSource;

//Yeah, I know this is weird code.
public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    public static final boolean DEBUG = !BuildConfig.BUILD_TYPE.equals("release");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if( JsonParams.DATA == null ) {
            try {
                new RemoteJSONSource(this).execute("");
            } catch ( Exception e ) {
                if (DEBUG) Log.e(TAG, "Error ads..." + e.toString() );
                Intent intent = new Intent(SplashScreen.this, ActivityMain.class);
                // Đặt các flags để xóa activity hiện tại khỏi stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(SplashScreen.this, ActivityMain.class);
            // Đặt các flags để xóa activity hiện tại khỏi stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.e(TAG, " start   " + JsonParams.getParamInt("openapp"));
//        if( JsonParams.getParamInt("openapp") == 1 ) {
////            AdmobLib.getInstance(this).fetchAd();
//            AdmobLib.getInstance(this).showAdIfAvailable();
//        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        FacebookAudienceAdsLibs.getInstance( this ).onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}