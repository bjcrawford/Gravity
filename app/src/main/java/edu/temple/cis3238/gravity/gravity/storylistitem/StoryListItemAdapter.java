package edu.temple.cis3238.gravity.gravity.storylistitem;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Story;

/**
 * This class is an Adapter for populating the RecyclerView used in
 * the story select fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class StoryListItemAdapter extends RecyclerView.Adapter<StoryListItemViewHolder> {

    /* A reference to the list of Story objects */
    private List<Story> stories;

    /* A listener for click events */
    private OnItemClickListener listener;

    /**
     * A constructor for the StoryListItemAdapter.
     *
     * @param stories the list of Story objects
     * @param listener the listener for click events
     */
    public StoryListItemAdapter(List<Story> stories, OnItemClickListener listener) {
        this.stories = stories;
        this.listener = listener;
    }

    @Override
    public StoryListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_item, parent, false);
        return new StoryListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoryListItemViewHolder viewHolder, final int position) {
        viewHolder.bindStoryListItem(stories.get(position));
        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    /**
     * This interface must be implemented by activities using this adapter.
     * This allows for interaction between the adapter and the activity.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
