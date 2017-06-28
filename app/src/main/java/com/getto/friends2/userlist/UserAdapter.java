package com.getto.friends2.userlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.getto.friends2.R;
import com.getto.friends2.model_retrofit.Users;
import com.getto.friends2.model_retrofit.Result;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Getto on 25.05.2016.
 */
public class UserAdapter extends ArrayAdapter<Users> {
    private Users users;
    private Context context;
    private int resource;
    private LayoutInflater vi;


   private static class ViewHolderUsers {
        CircleImageView imageview;
        ImageView image_add;
        TextView tvName;
    }

    public UserAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        users = new Users();
    }

    public void setUsers(Users users){
        for (int i = 0 ;i < users.getResults().size(); i++)
            this.users.getResults().add(users.getResults().get(i));
        notifyDataSetChanged();
    }

    public Result getUser(int position){
        return users.getResults().get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolderUsers holder;
        if (v == null) {
            holder = new ViewHolderUsers();
            v = vi.inflate(resource, null);
            holder.imageview = (CircleImageView) v.findViewById(R.id.ivImage);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.image_add = (ImageView) v.findViewById(R.id.image_add);
            v.setTag(holder);
        } else {
            holder = (ViewHolderUsers) v.getTag();
        }
        Result result = users.getResults().get(position);
        Picasso.with(context).load(result.getPicture().getMedium()).into(holder.imageview);
        holder.tvName.setText(result.getName().getFirst() + " " + result.getName().getLast());
        holder.image_add.setImageResource(R.drawable.tick);
        holder.image_add.setVisibility(result.isFriend() ? View.VISIBLE : View.INVISIBLE);
        return v;
    }

    @Override
    public int getCount() {
        return users.getResults().size();
    }


}
