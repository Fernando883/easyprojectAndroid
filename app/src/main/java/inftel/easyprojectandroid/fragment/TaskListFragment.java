package inftel.easyprojectandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.ViewTaskActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewTaskAdapter;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.util.RecyclerItemClickListener;


public class TaskListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private View view;
    private String idProject;
    private ArrayList<Tarea> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewTaskAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);

        adapter = new RecyclerViewTaskAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        return view;
    }

    public void setTaskList(ArrayList<Tarea> taskList) {
        this.taskList = taskList;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public void loadTasks () {
        recyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        adapter = new RecyclerViewTaskAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), ViewTaskActivity.class);
        intent.putExtra("idTask", taskList.get(position).getIdTarea());
        intent.putExtra("taskDescription", taskList.get(position).getDescripcion());
        intent.putExtra("taskStatus", taskList.get(position).getEstado());
        intent.putExtra("taskName", taskList.get(position).getNombre());
        intent.putExtra("taskTime", taskList.get(position).getTiempo());
        Gson gson = new Gson();
        intent.putExtra("taskUser",gson.toJson(taskList.get(position).getUsuarioCollection()));
        intent.putExtra("idProject",idProject);


        startActivity(intent);
    }
}
