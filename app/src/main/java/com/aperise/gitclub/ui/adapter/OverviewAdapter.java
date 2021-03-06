package com.aperise.gitclub.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.aperise.gitclub.R;
import com.aperise.gitclub.model.Commit;
import com.aperise.gitclub.model.Event;
import com.aperise.gitclub.model.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangsongtao on 2017/5/13.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.Holder> {

    List<Repository> mRepos;
    List<Event> mEvents;


    public void setData(List<Repository> repos, List<Event> events) {
        mRepos = repos;
        mEvents = events;
        notifyDataSetChanged();
    }

    public void setRepos(List<Repository> data) {
        this.mRepos = data;
        notifyDataSetChanged();
    }

    public void setEvents(List<Event> data) {
        this.mEvents = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mRepos.size()) {
            return 2;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        if (mRepos != null) {
            if (mEvents != null) {
                return mRepos.size() + mEvents.size();
            }
            return mRepos.size();
        }
        return 0;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new RepoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false));
        } else {
            return new EventHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (getItemViewType(position) == 1) {
            holder.setData(mRepos.get(position));
        } else {
            holder.setData(mEvents.get(position - mRepos.size()));
        }
    }

    static class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }

        public void setData(Repository repository) {
        }

        public void setData(Event event) {
        }
    }


    static class RepoHolder extends Holder {
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


        public RepoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Repository repository) {
            repoName.setText(repository.fullName);
            if (repository.fork) {
                forked.setVisibility(View.VISIBLE);
                forked.setText(repository.forksUrl);
            } else {
                forked.setVisibility(View.GONE);
            }
            descrip.setText(repository.description);

            language.setText(repository.language);
            language.getCompoundDrawables()[0].setTint(getLanguageColor(repository.language));
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

        public void setData(Event event) {

        }


        @TargetApi(Build.VERSION_CODES.M)
        private int getLanguageColor(String language) {
            Context context = itemView.getContext();
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

    static class EventHolder extends Holder {
        @BindView(R.id.repo)
        TextView repo;
        @BindView(R.id.commit)
        TextView commit;

        @BindView(R.id.avatar)
        SimpleDraweeView avatar;

        @BindView(R.id.actor)
        TextView actor;

        public EventHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Event event) {
            repo.setText(event.repo.name);
            avatar.setImageURI(event.actor.avatarUrl);
            actor.setText(event.actor.login);
            if ("PushEvent".equals(event.type)) {
                StringBuilder builder = new StringBuilder();
                List<Commit> commits = event.payload.commits;
                int size = commits.size();
                for (int i = 0; i < size; i++) {
                    Commit commit = commits.get(i);
                    builder.append("commit: " + commit.sha + "\n");
                    builder.append("Author: " + commit.author.name + " <" + commit.author.email + ">" + "\n");
                    builder.append("Date: " + event.createdAt + "\n");
                    if (i == size - 1) {
                        builder.append(commit.message);
                    } else {
                        builder.append(commit.message + "\n\n");
                    }
                }
                commit.setText(builder.toString());
            } else if ("CreateEvent".equals(event.type)) {
                StringBuilder builder = new StringBuilder();
                builder.append("Date:" + event.createdAt + "\n");
                builder.append(event.payload.description);
                commit.setText(builder.toString());
            }
        }
    }
}
