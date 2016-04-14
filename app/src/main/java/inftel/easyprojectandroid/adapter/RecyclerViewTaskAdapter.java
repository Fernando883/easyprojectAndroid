package inftel.easyprojectandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;


/**
 * Created by anotauntanto on 7/4/16.
 */
public class RecyclerViewTaskAdapter extends RecyclerView.Adapter<RecyclerViewTaskAdapter.ViewHolder> {

    private ArrayList<Tarea> taskList;

    public RecyclerViewTaskAdapter(ArrayList<Tarea> taskList) { this.taskList = taskList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iconTaskHours.setImageResource(R.drawable.ic_clock);
        holder.hourTasks.setText(String.valueOf(taskList.get(position).getTiempo().intValue() / 60) + " h");
        holder.taskName.setText(taskList.get(position).getNombre());

        for (Usuario user: taskList.get(position).getUsuarioCollection()) {
            holder.taskMembers.append(user.getNombreU() + "\n");
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iconTaskHours;
        public TextView hourTasks;
        public TextView taskName;
        public TextView taskMembers;
        public ViewHolder(View v){
            super(v);
            iconTaskHours = (ImageView) v.findViewById(R.id.iconhourTasks);
            hourTasks = (TextView) v.findViewById(R.id.hourTasks);
            taskName = (TextView) v.findViewById(R.id.taskName);
            taskMembers = (TextView) v.findViewById(R.id.taskMembers);
        }

    }

}
