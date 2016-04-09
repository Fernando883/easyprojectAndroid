package inftel.easyprojectandroid.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.ViewProjectActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewTaskAdapter;
import inftel.easyprojectandroid.model.Tarea;


public class TaskListFragment extends Fragment {

    private View view;
    private ArrayList<Tarea> taskList;
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

        Bundle arguments = getArguments();
        int tab = arguments.getInt("Tab");

        if (tab == ViewProjectActivity.DOING) {
           loadDoingTasks();
        } else if (tab == ViewProjectActivity.DONE) {
            loadDoneTasks();
        } else if (tab == ViewProjectActivity.TODO) {
            loadTodoTasks();
        }
        return view;
    }

    public void setTaskList(ArrayList<Tarea> taskList) {
        this.taskList = taskList;
    }

    public void loadDoingTasks () {

        /*taskList = new ArrayList<>();
        Tarea t1 = new Tarea();
        t1.setDescripcion("Esta es la descripción de la tarea 1");
        t1.setEstado("doing");
        t1.setNombre("Tarea número 1");
        t1.setTiempo(new BigInteger("50"));
        taskList.add(t1);

        Tarea t2 = new Tarea();
        t2.setDescripcion("Esta es la descripción de la tarea 2");
        t2.setEstado("doing");
        t2.setNombre("Tarea número 2");
        t2.setTiempo(new BigInteger("70"));
        taskList.add(t2);*/

        recyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        adapter = new RecyclerViewTaskAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    public void loadTodoTasks() {

    }

    public void loadDoneTasks(){

    }

}
