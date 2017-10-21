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
import com.aperise.gitclub.model.Gist;
import com.aperise.gitclub.presenter.GistsPresenter;
import com.aperise.gitclub.ui.data.GistItem;
import com.aperise.gitclub.ui.view.GistsView;

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
 * {@link GistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GistFragment extends BaseFragment implements GistsView {


    @ActivityScope
    @dagger.Component(dependencies = {ApplicationComponent.class})
    public interface Component {
        void inject(GistFragment fragment);
    }

    private OnFragmentInteractionListener mListener;


    @Inject
    GistsPresenter mGistsPresenter;
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public GistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GistFragment.
     */
    public static GistFragment newInstance() {
        GistFragment fragment = new GistFragment();
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
        return inflater.inflate(R.layout.fragment_gist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        DaggerGistFragment_Component.builder().applicationComponent(getApplicationComponent()).build().inject(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gists");

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

        mGistsPresenter.setGistsView(this);
        mGistsPresenter.getGists();

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
    }

    @Override
    public void gists(List<Gist> gists) {
        ArrayList<AbstractFlexibleItem> items = new ArrayList<>();
        items.addAll(GistItem.convert(gists));
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
