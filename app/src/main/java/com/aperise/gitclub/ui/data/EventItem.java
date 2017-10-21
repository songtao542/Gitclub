package com.aperise.gitclub.ui.data;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.aperise.gitclub.R;
import com.aperise.gitclub.model.Commit;
import com.aperise.gitclub.model.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by wangsongtao on 2017/5/22.
 */

public class EventItem extends SectionableItem<Event, EventItem.Holder> {

    public static List<EventItem> convert(List<Event> list) {
        ArrayList<EventItem> items = new ArrayList<>();
        if (list != null) {
            HeaderItem header = new HeaderItem("Recent Events");
            for (Event t : list) {
                EventItem item = new EventItem(t, header);
                items.add(item);
            }
        }
        return items;
    }


    public EventItem(Event event, HeaderItem header) {
        super(event, header);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.event_item;
    }

    @Override
    public Holder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Holder(view, adapter);
    }

    public static class Holder extends SectionableItem.Holder<Event> {

        @BindView(R.id.repo)
        TextView repo;
        @BindView(R.id.commit)
        TextView commit;
        @BindView(R.id.avatar)
        SimpleDraweeView avatar;
        @BindView(R.id.actor)
        TextView actor;

        public Holder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
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
            } else if ("WatchEvent".equals(event.type)) {
                StringBuilder builder = new StringBuilder();
                builder.append("Date:" + event.createdAt + "\n");
                builder.append("You watched \"" + event.repo.name + "\".");
                commit.setText(builder.toString());
            }
        }
    }

}
