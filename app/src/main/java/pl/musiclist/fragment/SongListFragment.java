package pl.musiclist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.musiclist.R;
import pl.musiclist.adapter.MySongRecyclerViewAdapter;
import pl.musiclist.model.Song;
import pl.musiclist.model.SongContentHolder;

// Fragment do wyświetlania listy utwórów
public class SongListFragment extends Fragment {

    // Interfejs służący do komunikacji między elementami listy (adapterem listy) a Activity
    public interface OnListFragmentInteractionListener {
        void onListFragmentClickInteraction(Song song, int position);
        void onListFragmentRemoveClickInteraction(Song song, int position);
    }

    private OnListFragmentInteractionListener mListener;
    private MySongRecyclerViewAdapter mRecyclerViewAdapter;

    // Metoda inicjalizuje widok z pliku xml z layoutem oraz inicjalizuje RecyclerView i adapter do wyświetlania elementów listy
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerViewAdapter = new MySongRecyclerViewAdapter(SongContentHolder.SONGS, mListener);
            recyclerView.setAdapter(mRecyclerViewAdapter);
        }
        return view;
    }

    // Metoda wywoływana automatycznie przy dodaniu Fragmentu do Activity, ustawia listener.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Metoda odświeża widok listy (aktualizuje elementy na niej wyświetlane)
    public void notifyDataChanged() {
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

}
