package net.radio.streaming.ads;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import net.radio.streaming.BuildConfig;
import net.radio.streaming.utils.JsonParams;

import java.util.Random;

//import com.google.android.ads.nativetemplates.NativeTemplateStyle;
//import com.google.android.ads.nativetemplates.TemplateView;

//import android.graphics.drawable.ColorDrawable;
//import com.google.android.ads.nativetemplates.NativeTemplateStyle;
//import com.google.android.ads.nativetemplates.TemplateView;

public class AdmobLib {
    private static String TAG = "AdmobLibActivity";
    public static final boolean DEBUG = !BuildConfig.BUILD_TYPE.equals("release");

    private static AdmobLib INSTANCE = null;

    public static JsonParams jparam = new JsonParams();
    static Activity activity;
    ProgressDialog pDialog;

    private InterstitialAd mInterstitialAd;

    private UnifiedNativeAd nativeAd;

    public static AdmobLib getInstance(Activity activity ) {
        if (INSTANCE == null) {
            INSTANCE = new AdmobLib( activity );
        }
        return(INSTANCE);
    }

//    public AdmobLib( Activity activity, ProgressDialog pDialog ) {
//        this.pDialog = pDialog;
//        this.activity = activity;
//        init();
//    }
    public AdmobLib(Activity activity ) {
        AdmobLib.activity = activity;
        init();
    }
    private void init() {
        TAG = activity.getClass().getSimpleName();
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
//        MobileAds.initialize(activity, "ca-app-pub-3940256099942544~3347511713" );
//        MobileAds.initialize(activity, activity.getResources().getString(R.string.admob_id) );
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                if (DEBUG) Log.e(TAG, "The rewarded ok loaded yet.");
//                rewardedLoad();
            }
        });
    }

    public void banner(final int banner_id ) {
        AdView mAdView = AdmobLib.activity.findViewById(banner_id);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
//        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (DEBUG) Log.e(TAG, "1. The banner ok da load.");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                if (DEBUG) Log.e(TAG, "2. The banner ok open.");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }
    public void interstitial(final boolean show, ProgressDialog pDl ) {
        if(  ( JsonParams.DATA == null || !JsonParams.getParam("interstitial").equals("admob") || JsonParams.getParam("admobid.android.interstitial") == null ) ) {
            if( pDl != null ) pDl.dismiss();
            return;
        }

        if( pDl != null ) {
            pDialog = pDl;
        } else {
            pDialog = ProgressDialog.show(activity, "Loading...", "Please wait...", true, false);
        }
        if( mInterstitialAd == null ) { //init
            mInterstitialAd = new InterstitialAd(activity);
//            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            mInterstitialAd.setAdUnitId( JsonParams.getParam("admobid.android.interstitial") );
        }

//        if (DEBUG) Log.e(TAG, "1. The interstitial ok da load trước.");
        if ( !mInterstitialAd.isLoaded() ) { //load ads
            if (DEBUG) Log.e(TAG, "2. The interstitial ok da load trước.");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    if( show ) {
                        if (DEBUG) Log.e(TAG, "The interstitial ok loaded yet.");
                        interstitialShow();
                    }
                    if( pDialog != null && pDialog.isShowing() ) {
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    if( pDialog != null && pDialog.isShowing() ) {
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
//                    if( pDialog != null && pDialog.isShowing() ) {
//                        pDialog.dismiss();
//                    }
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    if( pDialog != null && pDialog.isShowing() ) {
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the interstitial ad is closed.
                    if( pDialog != null && pDialog.isShowing() ) {
                        pDialog.dismiss();
                    }
                    // Load the next interstitial.
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        } else if( show ) {
            if (DEBUG) Log.e(TAG, "The interstitial ok da load trước.");
            interstitialShow();

            if( pDialog != null && pDialog.isShowing() ) {
                pDialog.dismiss();
            }
        }
    }
    public void interstitialShow() {
        if (DEBUG) Log.e(TAG, "The interstitial done => load trước." + mInterstitialAd.isLoaded());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            // Load the next interstitial.
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            if (DEBUG) Log.e(TAG, "The interstitial done => load trước.");
        } else {
            if (DEBUG) Log.e(TAG, "The interstitial wasn't loaded yet.");
        }
    }

    public void adsInterstitialRandom( ) {
        int random = new Random().nextInt(3 ); //int n = rand.nextInt(20); // Gives n such that 0 <= n < 20
        if (DEBUG) Log.e(TAG, "The interstitial rand." + random);
        if ( random == 1) {
            if (DEBUG) Log.e(TAG, "The interstitial rand." + random);
            this.interstitialShow( );
        }
    }

    public void nativeads(final int native_id) {
        if(  ( JsonParams.DATA == null || !JsonParams.getParam("native").equals("admob") || JsonParams.getParam("admobid.android.native") == null ) ) {
            AdmobLib.activity.findViewById( native_id ).setVisibility(View.GONE);
            return;
        }

//        MobileAds.initialize( activity, activity.getResources().getString( R.string.admob_id ) );
//        MobileAds.initialize( this.activity, "ca-app-pub-3940256099942544~3347511713" );
//        AdLoader adLoader = new AdLoader.Builder(this.activity, "ca-app-pub-3940256099942544/2247696110")
        AdLoader adLoader = new AdLoader.Builder(activity, JsonParams.getParam("admobid.android.native") )
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor( new ColorDrawable(Integer.decode("#FFFFFF")) ).build();
//
                        TemplateView template = AdmobLib.activity.findViewById( native_id );
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                        AdmobLib.activity.findViewById( native_id ).setVisibility(View.VISIBLE);
                    }
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                        AdmobLib.activity.findViewById( native_id ).setVisibility(View.GONE);
                    }
                    @Override
                    public void onAdClicked() {
                        // Log the click event or other custom behavior.
                    }
                })
//                .withNativeAdOptions(new NativeAdOptions().Builder().setAdChoicesPlacement(NativeAdOptions.ADCHOICES_BOTTOM_LEFT)
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())

                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

}
