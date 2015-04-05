package edu.temple.cis3238.gravity.gravity.levellistitem;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Level;

/**
 * This class is an Adapter for populating the RecyclerView used in
 * the level select fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class LevelListItemAdapter extends RecyclerView.Adapter<LevelListItemViewHolder> {

    /* A reference to the list of Level objects */
    private List<Level> levels;

    /* A listener for click events */
    private OnItemClickListener listener;

    /**
     * A constructor for the StoryListItemAdapter.
     *
     * @param levels the list of Level objects
     * @param listener the listener for click events
     */
    public LevelListItemAdapter(List<Level> levels, OnItemClickListener listener) {
        this.levels = levels;
        this.listener = listener;
    }

    @Override
    public LevelListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_list_item, parent, false);
        return new LevelListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LevelListItemViewHolder viewHolder, final int position) {
        viewHolder.bindLevelListItem(levels.get(position));
        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    /**
     * This interface must be implemented by activities using this adapter.
     * This allows for interaction between the adapter and the activity.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
