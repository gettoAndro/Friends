package com.getto.friends2.userlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getto.friends2.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Getto on 25.05.2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    private ArrayList<User> userList;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;

    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        userList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Context context = parent.getContext();
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.image_add = (ImageView) v.findViewById(R.id.image_add);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        User user = userList.get(position);
        Picasso.with(getContext()).load(user.getImage()).into(holder.imageview);
        holder.tvName.setText(user.getName());
        holder.image_add.setImageResource(R.drawable.tick);
        holder.image_add.setVisibility(user.isFriends() ? View.VISIBLE : View.INVISIBLE);


        return v;
    }
    static class ViewHolder {
        public ImageView imageview, image_add;
        public TextView tvName;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
