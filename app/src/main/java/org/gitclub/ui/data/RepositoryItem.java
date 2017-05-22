package org.gitclub.ui.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gitclub.R;
import org.gitclub.model.Repository;
import org.gitclub.utils.SLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public class RepositoryItem extends Item<Repository, RepositoryItem.Holder> {

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
        return new Holder(view, adapter);
    }

    public static class Holder extends Item.Holder<Repository> {
        @BindView(R.id.repoName)
        TextView repoName;
        @BindView(R.id.forked)
        TextView forked;
        @BindView(R.id.descrip)
        TextView descrip;
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
            language.getCompoundDrawables()[0].setTint(getLanguageColor(context, repository.language));
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

        @TargetApi(Build.VERSION_CODES.M)
        private int getLanguageColor(Context context, String language) {
            int v = Build.VERSION.SDK_INT;
            int color = 0x000000;
            if (v >= Build.VERSION_CODES.M) {
                if ("Java".equals(language)) {
                    color = context.getColor(R.color.lang_java);
                } else if ("Python".equals(language)) {
                    color = context.getColor(R.color.lang_python);
                } else if ("JavaScript".equals(language)) {
                    color = context.getColor(R.color.lang_js);
                } else if ("Shell".equals(language)) {
                    color = context.getColor(R.color.lang_shell);
                } else if ("C++".equals(language)) {
                    color = context.getColor(R.color.lang_cpp);
                } else if ("Go".equals(language)) {
                    color = context.getColor(R.color.lang_go);
                }
            } else {
                if ("Java".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_java);
                } else if ("Python".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_python);
                } else if ("JavaScript".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_js);
                } else if ("Shell".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_shell);
                } else if ("C++".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_cpp);
                } else if ("Go".equals(language)) {
                    color = context.getResources().getColor(R.color.lang_go);
                }
            }
            return color;
        }
    }
}
