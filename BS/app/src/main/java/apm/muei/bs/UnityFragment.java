package apm.muei.bs;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.unity3d.player.UnityPlayer;

public class UnityFragment extends Fragment {

    private OnUnityFragmentInteractionListener mListener;
    private UnityPlayer unityPlayer;
    public static UnityFragment instance; // Para poder acceder desde Unity

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

        FrameLayout layout = rootView.findViewById(R.id.unityFrameLayout);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        layout.addView(unityPlayer.getView(), 0, lp);

        unityPlayer.requestFocus();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUnityFragmentInteractionListener) {
            OnUnityFragmentInteractionListener listener = (OnUnityFragmentInteractionListener) context;
            mListener = listener;
            unityPlayer = listener.getUnityPlayer();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUnityFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        unityPlayer.resume();

        getActivity().setTitle("Unity");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        unityPlayer.quit();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        unityPlayer.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        unityPlayer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        unityPlayer.stop();
        // We need to remove the view from the FrameLayout, or else app will crash next time we open this screen
        // Not putting the Unity view in the FrameLayout after the first creation DOES NOT WORK,
        // the app doesn't crash, but Unity isn't displayed
        ((ViewGroup) unityPlayer.getView().getParent()).removeView(unityPlayer.getView());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        unityPlayer.configurationChanged(newConfig);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onBackToAndroid();
        }
    }

    public interface OnUnityFragmentInteractionListener {
        void onBackToAndroid();
        UnityPlayer getUnityPlayer();
    }

    public void onAndroidUnityCommunication() {
        String gameobjectName = "AssociatedText";
        String method = "ShowText";
        String arg = "You can relax now, everything is working fine";
        UnityPlayer.UnitySendMessage(gameobjectName, method, arg);
    }
}
