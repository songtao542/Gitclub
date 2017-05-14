package org.gitclub.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gitclub.R;

/**
 * Created by wangsongtao on 2017/5/13.
 */

public class ProfileOverviewAdapter extends RecyclerView.Adapter<ProfileOverviewAdapter.Holder> {


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, null, false));
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);

        }
    }
}
