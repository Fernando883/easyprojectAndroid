package inftel.easyprojectandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Message;

/**
 * Created by csalas on 9/4/16.
 */
public class RecyclerViewChatAdapter extends RecyclerView.Adapter<RecyclerViewChatAdapter.ListItemViewHolder> {

    private ArrayList<Message> messageList = null;
    private Context context;
    private int layout_mine, layout_user;

    public RecyclerViewChatAdapter(ArrayList<Message> messageList, Context context, int layout_mine, int layout_user) {
        this.messageList = messageList;
        this.context = context;
        this.layout_mine = layout_mine;
        this.layout_user = layout_user;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) { // Mensajes propios
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_mine, parent, false);
            return new ListItemViewHolder(itemView);
        } else { // Mensajes recibidos
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_user, parent, false);
            return new ListItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        Message msg = messageList.get(position);

        holder.msg.setText(msg.message);
        holder.dateTime.setText(msg.timestamp);
        if (holder.user != null) { // Solo si es un layout de usuario
            holder.user.setText(msg.name);
            if (msg.photoURL != null && !msg.photoURL.equals(""))
            Picasso.with(context).load(msg.photoURL).into(holder.userIcon);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getViewType();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView msg;
        TextView dateTime;
        CircleImageView userIcon;

        public ListItemViewHolder (View itemView) {
            super(itemView);
            if (itemView.getId() == R.id.row_chat)
                user = (TextView) itemView.findViewById(R.id.msgUser);
            msg = (TextView) itemView.findViewById(R.id.msgChat);
            dateTime = (TextView) itemView.findViewById(R.id.msgTime);
            userIcon = (CircleImageView) itemView.findViewById(R.id.iconChatUser);
        }
    }
}
