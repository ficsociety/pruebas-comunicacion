package apm.muei.bs;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnStartUnityListener, UnityFragment.OnUnityFragmentInteractionListener {

    private UnityPlayer unityPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // Create the UnityPlayer
        Log.d("BSUnity", "Recreating Unity Player");
        unityPlayer = new UnityPlayer(this);
        int glesMode = unityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor8888 = true;
        unityPlayer.init(glesMode, trueColor8888);

        setContentView(R.layout.activity_main);

        // Add the Unity view
//        FrameLayout layout = findViewById(R.id.fragment_container);
//        FrameLayout.LayoutParams lp =
//                new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT);
//        layout.addView(mUnityPlayer.getView(), 0, lp);
//        mUnityPlayer.requestFocus();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        if (frameLayout != null) {

            if (savedInstanceState != null) {
                return;
            }

            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();
        }
    }

    @Override
    public void onStartUnity() {
        CharSequence text = "Starting Unity";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();

        // Unity fragment
        UnityFragment newFragment = UnityFragment.getInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
//        Intent intent = new Intent(this, UnityPlayerActivity.class);
//        startActivity(intent);

    }

    @Override
    public void onBackToAndroid() {
        CharSequence text = "Back to Android";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();

        // Android fragment
        MainFragment newFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public UnityPlayer getUnityPlayer() {
        return unityPlayer;
    }

    @Override
    public void setUnityPlayer(UnityPlayer unityPlayer) { this.unityPlayer = unityPlayer; }

//    @Override
//    public void onDestroy() {
//        mUnityPlayer.quit();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mUnityPlayer.pause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mUnityPlayer.resume();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mUnityPlayer.start();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mUnityPlayer.stop();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mUnityPlayer.configurationChanged(newConfig);
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (unityPlayer != null) {
            unityPlayer.windowFocusChanged(hasFocus);
        }
    }

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        getSupportFragmentManager().popBackStack();
//    }
}
