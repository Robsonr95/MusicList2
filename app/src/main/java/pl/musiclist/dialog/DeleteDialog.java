package pl.musiclist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import pl.musiclist.R;

// Klasa okienka dialogowego do potwierdzenie usunięcia utworu
public class DeleteDialog extends DialogFragment {

    private static final String POSITION = "POSITION";

    private OnDeleteDialogInteractionListener mListener;

    // Metoda statyczna tworzy instancję okna dialogowego wraz z przekazanym argumentem position,
    // który określa, który element z listy należy usunąć.
    public static DeleteDialog newInstance(int position) {
        DeleteDialog fragment = new DeleteDialog();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    // Metoda tworzy okno dialogowe z żądaną treścią, przyciskami i listenerami do kliknięcia przycisków.
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final int position = bundle.getInt(POSITION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_question)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDeleteDialogPositiveClick(position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    // Metoda wywoływana automatycznie przy dodaniu okna do Activity, ustawia listener.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDeleteDialogInteractionListener) {
            mListener = (OnDeleteDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDeleteDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Interfejs służący do komunikacji okienka dialogowego z Activity
    public interface OnDeleteDialogInteractionListener {
        void onDeleteDialogPositiveClick(int position);
    }
}
