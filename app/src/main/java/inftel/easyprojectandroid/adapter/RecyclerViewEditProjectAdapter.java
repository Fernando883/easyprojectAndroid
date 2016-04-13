package inftel.easyprojectandroid.adapter;

/**
 * Created by anotauntanto on 10/4/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Usuario;


public class RecyclerViewEditProjectAdapter extends RecyclerView.Adapter<RecyclerViewEditProjectAdapter.ViewHolder> {

    private ArrayList<Usuario> userList;
    private ArrayList<String> removeUserList = new ArrayList<String>();

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
        final int pos = position;

        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    String email = userList.get(pos).getEmail();
                    removeUserList.add(email);
                }
                Toast.makeText(v.getContext() , "Clicked on Checkbox:" + userList.get(pos).getNombreU(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<String>  getRemoveUserList() {
        return removeUserList;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        CheckBox chkSelected;
        public ViewHolder(View v){
            super(v);
            userName = (TextView) v.findViewById(R.id.checkbox);
            chkSelected = (CheckBox) v.findViewById(R.id.checkbox);
        }

    }

}
