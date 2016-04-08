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
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.ViewProjectActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewTaskAdapter;
import inftel.easyprojectandroid.model.Tarea;


public class TaskListFragment extends Fragment {

    private View view;
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


        //Bundle arguments = getArguments();
        //int tab = arguments.getInt("Tab");

        loadTasks();
        return view;
    }

    public void setTaskList(ArrayList<Tarea> taskList) {
        this.taskList = taskList;
    }

    public void loadTasks () {
        recyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        adapter = new RecyclerViewTaskAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


}
