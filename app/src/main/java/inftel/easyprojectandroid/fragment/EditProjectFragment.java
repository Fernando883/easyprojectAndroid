package inftel.easyprojectandroid.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.service.ProjectService;

/**
 * Created by anotauntanto on 9/4/16.
 */
public class EditProjectFragment extends Fragment implements ServiceListener{

    private View view;
    private ProjectService projectService;
    private MultiAutoCompleteTextView textAutocomplete;
    ArrayList<String> emails = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectService = new ProjectService(getActivity(), this);
        projectService.getUsersEmailNonProject("948");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editproject, container, false);
        /*textAutocomplete=(MultiAutoCompleteTextView)getActivity().findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());*/

        return view;
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getUsersEmailNonProject")){
            for(Object email: response.second){
                emails.add((String) email);

            }
        }

    }
}
