package edu.temple.cis3238.gravity.gravity.storylistitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.dlc.Story;

/**
 * This class is a ViewHolder for populating the story items within the
 * RecyclerView used in the the story select fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class StoryListItemViewHolder extends RecyclerView.ViewHolder {

    private View storyListItemView;
    private ImageView storyThumb;
    private TextView storyName;
    private TextView storyDesc;

    /**
     * A constructor for the StoryListItemViewHolder.
     *
     * @param v the View of the list item
     */
    public StoryListItemViewHolder(View v) {
        super(v);

        storyListItemView = v;
        storyThumb = (ImageView) storyListItemView.findViewById(R.id.story_thumb);
        storyName = (TextView) storyListItemView.findViewById(R.id.story_name);
        storyDesc = (TextView) storyListItemView.findViewById(R.id.story_desc);
    }

    /**
     * Binds a given Story object to the view.
     *
     * @param story the Story object to bind
     */
    public void bindStoryListItem(Story story) {
        storyThumb.setImageResource(story.getThumbResId());
        storyName.setText(story.getName());
        storyDesc.setText(story.getDescription());
    }

    /**
     * Returns the View associated with this story item.
     *
     * @return the story item's View
     */
    public View getView() {
        return storyListItemView;
    }
}

