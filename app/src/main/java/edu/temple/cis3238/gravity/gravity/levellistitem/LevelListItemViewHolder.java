package edu.temple.cis3238.gravity.gravity.levellistitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Level;

/**
 * This class is a ViewHolder for populating the story items within the
 * RecyclerView used in the the level select fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class LevelListItemViewHolder extends RecyclerView.ViewHolder {

    private View levelListItemView;
    private ImageView levelThumb;
    private TextView levelName;
    private TextView levelDesc;

    /**
     * A constructor for the LevelListItemViewHolder.
     *
     * @param v the View of the list item
     */
    public LevelListItemViewHolder(View v) {
        super(v);

        levelListItemView = v;
        levelThumb = (ImageView) levelListItemView.findViewById(R.id.level_thumb);
        levelName = (TextView) levelListItemView.findViewById(R.id.level_name);
        levelDesc = (TextView) levelListItemView.findViewById(R.id.level_desc);
    }

    /**
     * Binds a given Level object to the view.
     *
     * @param level the Level object to bind
     */
    public void bindLevelListItem(Level level) {
        levelThumb.setImageResource(level.getThumbResId());
        levelName.setText(level.getName());
        levelDesc.setText(level.getDescription());
    }

    /**
     * Returns the View associated with this level item.
     *
     * @return the level item's View
     */
    public View getView() {
        return levelListItemView;
    }
}
