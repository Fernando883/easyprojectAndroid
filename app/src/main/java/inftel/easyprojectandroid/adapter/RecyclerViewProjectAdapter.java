package inftel.easyprojectandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        holder.projectNumUsers.setText(String.valueOf(projectList.get(position).getNumUsers()));
        holder.projectName.setText(projectList.get(position).getNombreP());
        holder.projectDescription.setText(projectList.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

      /*
    public Proyecto removeItem (int position) {
        Proyecto project = projectList.remove(position);
        notifyItemRemoved(position);
        return project;
    }

    public void addItem (int position, Proyecto project) {
        projectList.add(position, project);
        notifyItemInserted(position);
    }

    public void moveItem (int fromPosition, int toPosition) {
        Proyecto project = projectList.remove(fromPosition);
        projectList.add(toPosition, project);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo (List<Proyecto> listProjects) {
        applyAndAnimateRemovals(listProjects);
        applyAndAnimateAdditions(listProjects);
        applyAndAnimateMovedItems(listProjects);
    }

    private void applyAndAnimateRemovals(List<Proyecto> newProjects) {
        for (int i = newProjects.size() - 1; i >= 0; i--) {
            final Proyecto project = newProjects.get(i);
            if (!newProjects.contains(project)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Proyecto> newProjects) {
        for (int i = 0, count = newProjects.size(); i < count; i++) {
            final Proyecto project = newProjects.get(i);
            if (!newProjects.contains(project)) {
                addItem(i, project);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Proyecto> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Proyecto project = newModels.get(toPosition);
            final int fromPosition = newModels.indexOf(project);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }*/

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
