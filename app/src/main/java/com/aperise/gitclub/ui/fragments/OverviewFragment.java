package com.aperise.gitclub.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aperise.gitclub.R;
import com.aperise.gitclub.di.ActivityScope;
import com.aperise.gitclub.di.components.ApplicationComponent;
import com.aperise.gitclub.model.Event;
import com.aperise.gitclub.model.Repository;
import com.aperise.gitclub.presenter.ProfilePresenter;
import com.aperise.gitclub.ui.data.EventItem;
import com.aperise.gitclub.ui.data.RepositoryItem;
import com.aperise.gitclub.ui.view.ProfileView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends BaseFragment implements ProfileView {

    @ActivityScope
    @dagger.Component(dependencies = {ApplicationComponent.class})
    public interface Component {
        void inject(OverviewFragment fragment);
    }

    @Inject
    ProfilePresenter mProfilePresenter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    //    OverviewAdapter mProfileOverviewAdapter;
    FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private OnFragmentInteractionListener mListener;

    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment .
     *
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
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

        DaggerOverviewFragment_Component.builder().applicationComponent(getApplicationComponent()).build().inject(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Overview");

//        mProfileOverviewAdapter = new OverviewAdapter();

        mAdapter = new FlexibleAdapter<>(null, null, true);


        mRecyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mProfileOverviewAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setStickyHeaders(true)// Simulate developer 2nd call mistake, now it's safe, not executed, no warning log message!
                //.setDisplayHeadersAtStartUp(true)
                .showAllHeaders();
        mAdapter.setAnimationOnScrolling(true);
        mAdapter.setAnimationOnReverseScrolling(true);

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
        //mProfileOverviewAdapter.setRepos(repos);
    }

    @Override
    public void events(List<Event> events) {

    }

    @Override
    public void overview(List<Repository> repos, List<Event> events) {
//        mProfileOverviewAdapter.setData(repos, events);
        ArrayList<AbstractFlexibleItem> items = new ArrayList<>();
        items.addAll(RepositoryItem.convert(repos));
        items.addAll(EventItem.convert(events));
        mAdapter.updateDataSet(items);
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
