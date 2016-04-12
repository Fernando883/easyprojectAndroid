package inftel.easyprojectandroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.MainActivity;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

/**
 * Created by anotauntanto on 10/4/16.
 */
public class ConfirmDialog extends DialogFragment implements ServiceListener {

    private String itemDelete;
    private int type;
    private String nameItem;
    public static final int project = 1;
    public static final int task = 2;

    private ProjectService projectService;
    private TaskService taskService;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        projectService = new ProjectService(getActivity(), this);
        super.onCreate(savedInstanceState);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        itemDelete = arguments.getString("itemDelete");
        type = arguments.getInt("type");
        nameItem = arguments.getString("nameItem");


        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(getString(R.string.questionDelete) +  " " + nameItem + "?")
                .setTitle(getString(R.string.confirmation))
                .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Aceptada.");
                        if (type == project) {
                            deleteProject(itemDelete);
                        } else if (type == task) {
                            deleteTask(itemDelete);
                        }
                        Intent goToMainActivity = new Intent (getActivity(), MainActivity.class);
                        startActivity(goToMainActivity);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Cancelada.");
                    }
                });

        return builder.create();
    }


    public void deleteProject (String id) {
        projectService.deleteProject(id);

    }

    public void deleteTask (String id) {
        taskService.deleteTask(id);

    }


    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

    }
}

