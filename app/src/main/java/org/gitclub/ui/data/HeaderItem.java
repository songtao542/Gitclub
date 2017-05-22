package org.gitclub.ui.data;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gitclub.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.viewholders.FlexibleViewHolder;

public class HeaderItem extends AbstractHeaderItem<HeaderItem.HeaderViewHolder> {

    private String title;

    public HeaderItem(String title) {
        super();
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HeaderItem) {
            HeaderItem item = (HeaderItem) obj;
            return title.equals(item.title);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.header_item;
    }

    @Override
    public HeaderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindViewHolder(FlexibleAdapter adapter, HeaderViewHolder holder, int position, List payloads) {
        if (payloads.size() > 0) {
            Log.d(this.getClass().getSimpleName(), "InstagramHeaderItem Payload " + payloads);
        } else {
            holder.setData(title);
        }
    }

    static class HeaderViewHolder extends FlexibleViewHolder {

        TextView mTextView;

        public HeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true);//True for sticky
            mTextView = (TextView) view.findViewById(R.id.textview);

            //Support for StaggeredGridLayoutManager
            if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);
            }
        }

        public void setData(String title) {
            mTextView.setText(title);
        }
    }
}