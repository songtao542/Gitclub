package org.gitclub.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gitclub.R;
import org.gitclub.di.ActivityScope;
import org.gitclub.di.components.ApplicationComponent;
import org.gitclub.model.Event;
import org.gitclub.model.Repository;
import org.gitclub.presenter.ProfilePresenter;
import org.gitclub.ui.adapter.ProfileOverviewAdapter;
import org.gitclub.ui.view.ProfileView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment implements ProfileView {

    @ActivityScope
    @dagger.Component(dependencies = {ApplicationComponent.class})
    public interface Component {
        void inject(ProfileFragment fragment);
    }

    @Inject
    ProfilePresenter mProfilePresenter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ProfileOverviewAdapter mProfileOverviewAdapter;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment .
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        DaggerProfileFragment_Component.builder().applicationComponent(getApplicationComponent()).build().inject(this);

        mProfileOverviewAdapter = new ProfileOverviewAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProfileOverviewAdapter);

        mProfilePresenter.setView(this);

//        mProfilePresenter.getRepos();
        mProfilePresenter.getReposWithEvents();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProfilePresenter = null;
    }

    @Override
    public void profile(List<Repository> repos) {
        mProfileOverviewAdapter.setRepos(repos);
    }

    @Override
    public void events(List<Event> events) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
