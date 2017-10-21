package com.aperise.gitclub.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aperise.gitclub.GitApplication;
import com.aperise.gitclub.di.components.ApplicationComponent;
import com.aperise.gitclub.utils.SLog;

/**
 * Created by le on 5/16/17.
 */

public class BaseFragment extends Fragment {
    protected ApplicationComponent getApplicationComponent() {
        return ((GitApplication) getActivity().getApplication()).getApplicationComponent();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        SLog.d(this, "onAttachFragment()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SLog.d(this, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SLog.d(this, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SLog.d(this, "onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SLog.d(this, "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SLog.d(this, "onActivityCreated()");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        SLog.d(this, "onViewStateRestored()");
    }

    @Override
    public void onStart() {
        super.onStart();
        SLog.d(this, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        SLog.d(this, "onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SLog.d(this, "onSaveInstanceState()");
    }

    @Override
    public void onPause() {
        super.onPause();
        SLog.d(this, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        SLog.d(this, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SLog.d(this, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SLog.d(this, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SLog.d(this, "onDetach()");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
