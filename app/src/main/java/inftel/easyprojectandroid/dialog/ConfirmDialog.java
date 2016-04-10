package inftel.easyprojectandroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import inftel.easyprojectandroid.R;

/**
 * Created by anotauntanto on 10/4/16.
 */
public class ConfirmDialog extends DialogFragment {

    private String itemDelete;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        itemDelete = arguments.getString("itemDelete");

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(getString(R.string.questionDelete) +  " " + itemDelete + "?")
                .setTitle(getString(R.string.confirmation))
                .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Aceptada.");
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


}

