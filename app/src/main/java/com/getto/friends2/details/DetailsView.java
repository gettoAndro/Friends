package com.getto.friends2.details;

import android.database.Cursor;

/**
 * Created by Getto on 08.07.2016.
 */
public interface DetailsView {
    void onCreateView(Cursor cursor);
    long getID();
    void launchGallery();
    void onApplyChanges();
}
