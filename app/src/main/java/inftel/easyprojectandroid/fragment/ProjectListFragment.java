package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.RecyclerViewProjectAdapter;
import inftel.easyprojectandroid.model.Proyecto;

/**
 * Created by csalas on 7/4/16.
 */
public class ProjectListFragment extends Fragment {
    private View view;
    private ArrayList<Proyecto> projectList;
    private RecyclerView recyclerView;
    private RecyclerViewProjectAdapter adapter;


    public ProjectListFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_project_list, container, false);

        // Aquí tenemos que tener los datos recuperados
        projectList = new ArrayList<Proyecto>();

        recyclerView = (RecyclerView) view.findViewById(R.id.projectRecyclerView);
        adapter = new RecyclerViewProjectAdapter(projectList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();

        return view;
    }
}
