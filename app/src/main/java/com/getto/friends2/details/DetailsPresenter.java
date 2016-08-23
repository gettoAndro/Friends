package com.getto.friends2.details;

import android.database.Cursor;
import com.getto.friends2.main.Model;



/**
 * Created by Getto on 08.07.2016.
 */
public class DetailsPresenter {
    private Model model;
    private DetailsView detailsView;


    public DetailsPresenter(Model model, DetailsView detailsView) {
        this.model = model;
        this.detailsView = detailsView;
    }
public void onCreate(){
    model.Open();
    Cursor cursor = model.SelectItem(detailsView.getID());
detailsView.onCreateView(cursor);
}
    public void onLaunchGallery(){
        detailsView.launchGallery();
    }

public void ApplyChanges(){
 detailsView.onApplyChanges();
}

}
