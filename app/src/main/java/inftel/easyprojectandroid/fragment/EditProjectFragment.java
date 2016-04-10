package inftel.easyprojectandroid.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.RecyclerViewEditProjectAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

/**
 * Created by anotauntanto on 9/4/16.
 */
public class EditProjectFragment extends Fragment implements ServiceListener{

    private View view;
    private ProjectService projectService;
    private MultiAutoCompleteTextView textAutocomplete;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private Proyecto project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectService = new ProjectService(getActivity(), this);
        projectService.getUsersEmailNonProject("948");
        projectService.getUsersProject("948");
        projectService.getProject("948");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editproject, container, false);

        return view;
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

        if (response.first.equals("getProject")){
            project = (Proyecto) response.second;
            System.out.println("getProject");
            loadContentProject();
        }

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getUsersEmailNonProject")){
            for(Object email: response.second){
                emails.add((String) email);

            }
            loadAutoCompleteContent();

        } else if (response.first.equals("getUsersProject")) {

            for(Object user: response.second){
                listUsersProject.add((Usuario) user);
            }
            loadCheckBoxContent();

        }

    }

    public void loadContentProject () {

        //projectName
        android.support.design.widget.TextInputLayout projectName = (android.support.design.widget.TextInputLayout) view.findViewById(R.id.input_layout_editNameProject);
        projectName.setHintAnimationEnabled(true);
        projectName.setHint(project.getNombreP());

        //projectDescripcion
        android.support.design.widget.TextInputLayout projectDescription = (android.support.design.widget.TextInputLayout) view.findViewById(R.id.input_layout_editprojectDescription);
        projectDescription.setHintAnimationEnabled(true);
        projectDescription.setHint(project.getDescripcion());



    }

    public void loadAutoCompleteContent () {
        textAutocomplete=(MultiAutoCompleteTextView)getActivity().findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void loadCheckBoxContent(){

        recyclerView = (RecyclerView) view.findViewById(R.id.projectEditRecyclerView);
        adapter = new RecyclerViewEditProjectAdapter(listUsersProject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
