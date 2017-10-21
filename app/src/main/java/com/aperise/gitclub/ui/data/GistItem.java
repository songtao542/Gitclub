package com.aperise.gitclub.ui.data;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aperise.gitclub.R;
import com.aperise.gitclub.model.Gist;
import com.aperise.gitclub.utils.Color;
import com.aperise.gitclub.utils.SLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public class GistItem extends Item<Gist, GistItem.Holder> {


    public static List<GistItem> convert(List<Gist> list) {
        ArrayList<GistItem> items = new ArrayList<>();
        if (list != null) {
            for (Gist t : list) {
                GistItem item = new GistItem(t);
                items.add(item);
            }
        }
        return items;
    }

    private GistItem(Gist gist) {
        super(gist);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.gist_item;
    }

    @Override
    public Holder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Holder(view, adapter);
    }

    public static class Holder extends Item.Holder<Gist> {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.createat)
        TextView createat;
        @BindView(R.id.descrip)
        TextView descrip;
        @BindView(R.id.files)
        TextView files;
        @BindView(R.id.forks)
        TextView forks;
        @BindView(R.id.comments)
        TextView comments;
        @BindView(R.id.stars)
        TextView stars;

        public Holder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
        }

        @Override
        public void setData(Gist gist) {
            SLog.d(this, "gist:" + gist.toString());
            name.setText(gist.owner.login + "/" + gist.getFilename());
            createat.setText("Created at " + gist.createdAt.replaceAll("[TZ]", " "));
            if (!TextUtils.isEmpty(gist.description)) {
                descrip.setTextColor(Color.getColor(itemView.getContext(), R.color.textcolor));
                descrip.setText(gist.description);
            } else {
                descrip.setTextColor(Color.getColor(itemView.getContext(), R.color.textcolorHint));
                descrip.setText("Have no description.");
            }
            files.setText(gist.getFileCount() + " file");
            comments.setText(gist.comments + " comment");
        }
    }
}
