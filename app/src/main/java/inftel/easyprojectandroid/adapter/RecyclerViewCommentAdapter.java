package inftel.easyprojectandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Comentario;
import inftel.easyprojectandroid.model.ComentarioComparator;

/**
 * Created by inftel10 on 7/4/16.
 */
public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<RecyclerViewCommentAdapter.ViewHolder>
{
    private ArrayList<Comentario> commentList;

    public RecyclerViewCommentAdapter(ArrayList<Comentario> commentList) {

        Collections.sort(commentList, new ComentarioComparator());
        this.commentList = commentList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("Comentario", commentList.get(position).getTexto());

        holder.nameUser.setText(commentList.get(position).getIdUsuario().getNombreU());

        Date date= (Date) commentList.get(position).getFecha();
        String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        holder.date.setText(dateString);

        if(commentList.get(position).getTexto().contains("Ha subido el fichero:"))
        {
            String file=commentList.get(position).getTexto();
            holder.commentUsers.loadData(file.replace("localhost","192.168.183.76"), "text/html; charset=UTF-8", null);
        }
        else {
            holder.commentUsers.loadData(commentList.get(position).getTexto(), "text/plain; charset=UTF-8", null);
        }

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


        public WebView commentUsers;
        public TextView nameUser;
        public TextView date;
        public EditText insertComment;

        public ViewHolder(View v){
            super(v);

            nameUser = (TextView) v.findViewById(R.id.nameUser);
            commentUsers = (WebView) v.findViewById(R.id.commentUser);
            date=(TextView) v.findViewById(R.id.date);
            insertComment=(EditText) v.findViewById(R.id.insertComment);

        }

    }
}