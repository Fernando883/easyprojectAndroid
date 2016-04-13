package inftel.easyprojectandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Comentario;

/**
 * Created by inftel10 on 7/4/16.
 */
public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<RecyclerViewCommentAdapter.ViewHolder>
{
    private ArrayList<Comentario> commentList;

    public RecyclerViewCommentAdapter(ArrayList<Comentario> commentList) { this.commentList = commentList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("ERROR", String.valueOf(commentList.get(position).getIdUsuario()));


        holder.nameUser.setText(commentList.get(position).getIdUsuario().getNombreU());
        holder.commentUsers.setText(commentList.get(position).getTexto());

        String fecha=commentList.get(position).getFecha();
        System.out.println("FECHA: " + fecha);
        //String Array[]= fecha.split("\\+");

        //2016-04-12T12:32:01+02:00


        holder.date.setText(fecha);







    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void add(Comentario comentario) {
        commentList.add(comentario);
        notifyItemInserted(commentList.indexOf(comentario));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView commentUsers;
        public TextView nameUser;
        public TextView date;
        public EditText insertComment;

        public ViewHolder(View v){
            super(v);

            nameUser = (TextView) v.findViewById(R.id.nameUser);


            commentUsers = (TextView) v.findViewById(R.id.commentUser);

            date=(TextView) v.findViewById(R.id.date);

            insertComment=(EditText) v.findViewById(R.id.insertComment);
        }

    }
}
