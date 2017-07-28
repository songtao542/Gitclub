package org.gitclub.ui.data;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.gitclub.R;
import org.gitclub.model.Repository;
import org.gitclub.utils.SLog;
import org.gitclub.widget.Circle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public class RepositoryItem extends SectionableItem<Repository, RepositoryItem.Holder> {

    public static List<RepositoryItem> convert(List<Repository> list) {
        ArrayList<RepositoryItem> items = new ArrayList<>();
        if (list != null) {
            HeaderItem header = new HeaderItem("Repositories");
            for (Repository t : list) {
                RepositoryItem item = new RepositoryItem(t, header);
                items.add(item);
            }
        }
        return items;
    }

    private RepositoryItem(Repository repository, HeaderItem header) {
        super(repository, header);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.repo_item;
    }

    @Override
    public Holder createViewHolder(View view, FlexibleAdapter adapter) {
        SLog.d(this, "createViewHolder:view=" + view);
        return new Holder(view, adapter);
    }

    public static class Holder extends SectionableItem.Holder<Repository> {
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
        public void setData(Repository repository) {
            SLog.d(this, "repository:" + repository.toString());
            repoName.setText(repository.fullName);
            if (repository.fork) {
                forked.setVisibility(View.VISIBLE);
                forked.setText(repository.forksUrl);
            } else {
                forked.setVisibility(View.GONE);
            }
            descrip.setText(repository.description);

            language.setText(repository.language);
            Context context = itemView.getContext();
            languageColor.setColor(Language.getColor(context, repository.language));
//            language.getCompoundDrawables()[0].setTint(Language.getColor(context, repository.language));
            if (repository.stargazersCount > 0) {
                stars.setVisibility(View.VISIBLE);
                stars.setText(String.valueOf(repository.stargazersCount));
            } else {
                stars.setVisibility(View.INVISIBLE);
            }
            if (repository.forksCount > 0) {
                network.setVisibility(View.VISIBLE);
                network.setText(String.valueOf(repository.forksCount));
            } else {
                network.setVisibility(View.INVISIBLE);
            }
        }


    }
}
