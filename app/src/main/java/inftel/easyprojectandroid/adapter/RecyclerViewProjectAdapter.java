package inftel.easyprojectandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Proyecto;

/**
 * Created by csalas on 6/4/16.
 */
public class RecyclerViewProjectAdapter extends RecyclerView.Adapter<RecyclerViewProjectAdapter.ViewHolder> {
    private ArrayList<Proyecto> projectList;

    public RecyclerViewProjectAdapter(ArrayList<Proyecto> projectList) { this.projectList = projectList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_project_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iconProjectNumUsers.setImageResource(R.drawable.icon_user);
        holder.projectNumUsers.setText(projectList.get(position).getNumUsers());
        holder.projectName.setText(projectList.get(position).getNombreP());
        holder.projectDescription.setText(projectList.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iconProjectNumUsers;
        public TextView projectNumUsers;
        public TextView projectName;
        public TextView projectDescription;
        public ViewHolder(View v){
            super(v);
            iconProjectNumUsers = (ImageView) v.findViewById(R.id.iconProjectNumUsers);
            projectNumUsers = (TextView) v.findViewById(R.id.projectNumUsers);
            projectName = (TextView) v.findViewById(R.id.projectName);
            projectDescription = (TextView) v.findViewById(R.id.projectDescription);
        }

    }

}
