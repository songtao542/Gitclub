package com.aperise.gitclub.ui.data;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.aperise.gitclub.utils.SLog;

import java.util.List;

import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public abstract class SectionableItem<T, VH extends SectionableItem.Holder> extends AbstractSectionableItem<VH, HeaderItem> {

    private T mData;

    public SectionableItem(T t, HeaderItem header) {
        super(header);
        mData = t;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Holder holder, int position, List payloads) {
        SLog.d(this, "position:" + position);
        holder.setData(mData);
    }


    public boolean equals(Object o) {
        return this == o;
    }

//    public VH createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
//        return createViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
//    }

    public abstract static class Holder<T> extends FlexibleViewHolder {

        public Holder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            if (mAdapter.getRecyclerView().getLayoutManager() instanceof GridLayoutManager) {
                if (position % 2 != 0)
                    AnimatorHelper.slideInFromRightAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
                else
                    AnimatorHelper.slideInFromLeftAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
            } else {
                if (isForward)
                    AnimatorHelper.slideInFromBottomAnimator(animators, itemView, mAdapter.getRecyclerView());
                else
                    AnimatorHelper.slideInFromTopAnimator(animators, itemView, mAdapter.getRecyclerView());
            }
        }

        public abstract void setData(T t);
    }
}
