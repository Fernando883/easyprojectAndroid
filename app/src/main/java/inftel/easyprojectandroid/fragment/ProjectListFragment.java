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

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.ViewProjectActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewProjectAdapter;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.util.RecyclerItemClickListener;

/**
 * Created by csalas on 7/4/16.
 */
public class ProjectListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    private View view;
    private ArrayList<Proyecto> projectList;
    private RecyclerView recyclerView;
    private RecyclerViewProjectAdapter adapter;


    public ProjectListFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_project_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.projectRecyclerView);
        adapter = new RecyclerViewProjectAdapter(projectList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        return view;
    }

    public void setProjectList(ArrayList<Proyecto> projectList) {
        this.projectList = projectList;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), ViewProjectActivity.class);
        intent.putExtra("idProject", projectList.get(position).getIdProyect());
        startActivity(intent);
    }

    /*public void addExampleData() {
        Proyecto p = new Proyecto();
        p.setNombreP("TeleRiego");
        p.setDescripcion("TeleRiego");
        p.setNumUsers(3);
        Usuario u = new Usuario();
        u.setNombreU("Guillermo");
        p.setDirector(u);

        Proyecto p2 = new Proyecto();
        p2.setNombreP("TodoTest");
        p2.setDescripcion("TodoTest");
        p2.setNumUsers(2);
        Usuario u2 = new Usuario();
        u2.setNombreU("Carlos Salas");
        p2.setDirector(u2);

        projectList.add(p);
        projectList.add(p2);

    }*/
}
