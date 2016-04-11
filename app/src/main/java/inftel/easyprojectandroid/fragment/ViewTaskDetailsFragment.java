package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import inftel.easyprojectandroid.R;

/**
 * Created by macbookpro on 10/4/16.
 */
public class ViewTaskDetailsFragment extends Fragment {

    private View view;
    MenuItem fav;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_task_details,container,false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_editTask).setVisible(true);
        menu.findItem(R.id.action_visualizeTask).setVisible(false);
    }

}
