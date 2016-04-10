package inftel.easyprojectandroid.adapter;

/**
 * Created by anotauntanto on 10/4/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Usuario;


public class RecyclerViewEditProjectAdapter extends RecyclerView.Adapter<RecyclerViewEditProjectAdapter.ViewHolder> {

    private ArrayList<Usuario> userList;
    public RecyclerViewEditProjectAdapter(ArrayList<Usuario> userList) { this.userList = userList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checkbox, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.userName.setText(userList.get(position).getNombreU());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public ViewHolder(View v){
            super(v);
            userName = (TextView) v.findViewById(R.id.checkbox);
        }

    }

}
