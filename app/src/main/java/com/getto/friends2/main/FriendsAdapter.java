package com.getto.friends2.main;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.getto.friends2.R;

/**
 * Created by Getto on 27.05.2016.
 */
public class FriendsAdapter extends ResourceCursorAdapter {
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
FriendsAdapter(Context context, int resource, Cursor cursor, int flags){
    super(context, resource, cursor, flags);
    vi = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Resource = resource;
}
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        holder = new ViewHolder();
        holder.imageview = (ImageView) view.findViewById(R.id.image_friend);
        byte[] bb = cursor.getBlob(cursor.getColumnIndex("image"));
        if (bb != null) {
            holder.imageview.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
        } else holder.imageview.setImageResource(R.drawable.q);

        holder.tvName = (TextView) view.findViewById(R.id.name_friend);
        holder.tvName.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
    }
}
