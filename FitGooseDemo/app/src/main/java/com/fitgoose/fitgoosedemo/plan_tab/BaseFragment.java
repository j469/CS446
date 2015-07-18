package com.fitgoose.fitgoosedemo.plan_tab;

/**
 * Created by yyf on 6/15/2015.
 */
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Rex St. John (on behalf of AirPair.com) on 3/4/14.
 */
public class BaseFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    /**
     * Default empty constructor
     */
    public BaseFragment(){
        //
    }

    /**
     * This interface must be implemented by activities that contain this
     * mFragment to allow an interaction in this mFragment to be communicated
     * to the mActivity and potentially other fragments contained in that
     * mActivity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
        public void onFragmentInteraction(String id);
        public void onFragmentInteraction(int actionId);
    }
}
