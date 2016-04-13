package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;

/**
 * Created by macbookpro on 10/4/16.
 */
public class ViewTaskDetailsFragment extends Fragment {

    private View view;
    private Tarea task;
    private TextView taskNameInfo;
    private TextView hourTasks;
    private TextView estado;
    private TextView taskDescriptionInfo;
    private TextView tasktMemberInfo;
    private String tasktMember;
    MenuItem fav;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_task_details,container,false);

        taskNameInfo = (TextView) view.findViewById(R.id.taskNameInfo);
        hourTasks = (TextView) view.findViewById(R.id.hourTasks);
        estado = (TextView) view.findViewById(R.id.statusTaskInfo1);
        taskDescriptionInfo = (TextView) view.findViewById(R.id.taskDescriptionInfo);
        tasktMemberInfo = (TextView) view.findViewById(R.id.tasktMemberInfo);

        taskNameInfo.setText(task.getNombre());
        hourTasks.setText(task.getTiempo().toString());
        estado.setText(task.getEstado());
        taskDescriptionInfo.setText(task.getDescripcion());
        for(Usuario user: task.getUsuarioCollection()){
            tasktMemberInfo.append(user.getNombreU());
        }

        return view;
    }

    public void setTask(Tarea task) {
        this.task = task;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_visualize).setVisible(false);
    }

}
