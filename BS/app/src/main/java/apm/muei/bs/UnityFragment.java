package apm.muei.bs;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.unity3d.player.UnityPlayer;

public class UnityFragment extends Fragment {

    private OnUnityFragmentInteractionListener mListener;
    protected UnityPlayer mUnityPlayer;
    public static UnityFragment instance; // Para poder acceder desde Unity
    private FrameLayout frameLayout;

    public static UnityFragment getInstance() {
        if (instance == null) {
            instance = new UnityFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_unity, container, false);

        Log.d("BSUnity", "Setting Unity Player view");
        ViewGroup layout = rootView.findViewById(R.id.unityFrameLayout);

        if (layout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            layout.addView(mUnityPlayer.getView(), 0, lp);
        }

        mUnityPlayer.requestFocus();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUnityFragmentInteractionListener) {
            OnUnityFragmentInteractionListener listener = (OnUnityFragmentInteractionListener) context;
            mListener = listener;
            Log.d("BSUnity", "Attaching Unity Player");
            mUnityPlayer = listener.getUnityPlayer();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUnityFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        // If Unity view is not set, set it
        // This can happen when blocking and unblocking mobile while in this screen
        FrameLayout layout = getView().findViewById(R.id.unityFrameLayout);
        if (layout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            layout.addView(mUnityPlayer.getView(), 0, lp);
        }

        getActivity().setTitle("Unity");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("BSUnity", "Starting fragment");
        mUnityPlayer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mUnityPlayer.stop();
        // We need to remove the view from the FrameLayout, or else app will crash next time we open this screen
        // Not putting the Unity view in the FrameLayout after the first creation DOES NOT WORK,
        // the app doesn't crash, but Unity isn't displayed
        Log.d("BSUnity", "Remove Unity Player view");
        ViewGroup unityView = ((ViewGroup) mUnityPlayer.getView().getParent());
        unityView.removeView(mUnityPlayer.getView());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onBackToAndroid();
        }
    }

    public interface OnUnityFragmentInteractionListener {
        void onBackToAndroid();
        UnityPlayer getUnityPlayer();
        void setUnityPlayer(UnityPlayer unityPlayer);
    }

    public void onAndroidUnityCommunication() {
        String gameobjectName = "AssociatedText";
        String method = "ShowText";
        String arg = "You can relax now, everything is working fine";
        UnityPlayer.UnitySendMessage(gameobjectName, method, arg);
    }

}
