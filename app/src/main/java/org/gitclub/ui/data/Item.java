package org.gitclub.ui.data;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gitclub.utils.SLog;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public abstract class Item<T, VH extends Item.Holder> extends AbstractFlexibleItem<VH> {

    private T mData;

    public Item(T t) {
        mData = t;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Holder holder, int position, List payloads) {
        SLog.d(this, "position:" + position);
        holder.setData(mData);
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    public abstract static class Holder<T> extends FlexibleViewHolder {

        protected static final SimpleDateFormat DFT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //2017-05-28T05:42:49Z
        protected static final SimpleDateFormat DFS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

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
