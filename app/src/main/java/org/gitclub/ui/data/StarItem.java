package org.gitclub.ui.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.gitclub.R;
import org.gitclub.model.Repository;
import org.gitclub.model.Star;
import org.gitclub.utils.SLog;
import org.gitclub.widget.Circle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public class StarItem extends Item<Star, StarItem.Holder> {

    public static List<StarItem> convert(List<Star> list) {
        ArrayList<StarItem> items = new ArrayList<>();
        if (list != null) {
            for (Star t : list) {
                StarItem item = new StarItem(t);
                items.add(item);
            }
        }
        return items;
    }

    private StarItem(Star star) {
        super(star);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.star_item;
    }

    @Override
    public Holder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Holder(view, adapter);
    }

    public static class Holder extends Item.Holder<Star> {
        @BindView(R.id.repoName)
        TextView repoName;
        @BindView(R.id.forked)
        TextView forked;
        @BindView(R.id.descrip)
        TextView descrip;
        @BindView(R.id.languageColor)
        Circle languageColor;
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.stars)
        TextView stars;
        @BindView(R.id.network)
        TextView network;

        public Holder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
        }

        @Override
        public void setData(Star star) {
            SLog.d(this, "star:" + star.toString());
            repoName.setText(star.fullName);
            if (star.fork) {
                forked.setVisibility(View.VISIBLE);
                forked.setText(star.forksUrl);
            } else {
                forked.setVisibility(View.GONE);
            }
            descrip.setText(star.description);

            language.setText(star.language);
            Context context = itemView.getContext();
            languageColor.setColor(Language.getColor(context, star.language));
            if (star.stargazersCount > 0) {
                stars.setVisibility(View.VISIBLE);
                stars.setText(String.valueOf(star.stargazersCount));
            } else {
                stars.setVisibility(View.INVISIBLE);
            }
            if (star.forksCount > 0) {
                network.setVisibility(View.VISIBLE);
                network.setText(String.valueOf(star.forksCount));
            } else {
                network.setVisibility(View.INVISIBLE);
            }
        }
    }
}
