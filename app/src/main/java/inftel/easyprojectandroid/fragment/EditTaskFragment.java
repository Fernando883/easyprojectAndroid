package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.RecyclerViewEditProjectAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

/**
 * Created by macbookpro on 10/4/16.
 */
public class EditTaskFragment extends Fragment implements ServiceListener {

    private View view;
    private ProjectService projectService;
    private MultiAutoCompleteTextView textAutocomplete;
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private EditText taskDuration;

    ArrayList<String> emails = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Usuario user = new Usuario();
        user.setEmail("fernando@gmail.com");
        user.setNombreU("Fernando");

        Usuario user1 = new Usuario();
        user1.setEmail("Victor@hotmail.es");
        user1.setNombreU("Victor");

        listUsersProject.add(user);
        listUsersProject.add(user1);

        projectService = new ProjectService(getActivity(), this);
        projectService.getUsersEmailProject("948");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_task,container,false);

        loadCheckBoxContent();
        return view;
    }

    public void edit(){
        taskDuration = (EditText) view.findViewById(R.id.input_layout_nameTask);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_editTask).setVisible(false);
        menu.findItem(R.id.action_visualizeTask).setVisible(true);

    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getUsersEmailProject")){
            for(Object email: response.second){
                emails.add((String) email);
                loadAutoCompleteContent();
            }
        } else if (response.first.equals("getUsersProject")) {

            for(Object user: response.second){
                listUsersProject.add((Usuario) user);
            }
            loadCheckBoxContent();

        }

    }

    public void loadAutoCompleteContent () {
        textAutocomplete=(MultiAutoCompleteTextView)getActivity().findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void loadCheckBoxContent(){

        recyclerView = (RecyclerView) view.findViewById(R.id.taskEditRecyclerView);
        adapter = new RecyclerViewEditProjectAdapter(listUsersProject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
}
