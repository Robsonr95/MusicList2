package pl.musiclist.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import pl.musiclist.utils.PicUtils;
import pl.musiclist.R;
import pl.musiclist.fragment.SongListFragment.OnListFragmentInteractionListener;
import pl.musiclist.model.Song;

import java.util.List;

// Klasa adaptera do wyświelania elementów na liście RecyclerView
public class MySongRecyclerViewAdapter extends RecyclerView.Adapter<MySongRecyclerViewAdapter.ViewHolder> {

    private final List<Song> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySongRecyclerViewAdapter(List<Song> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    // Metoda tworzy i zwraca ViewHolder, który przechowuje widok elementu listy.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(view);
    }

    // Metoda ustawia wartości w widoku elementu na liście dla konkretnego elementu wg zmiennej position.
    // Ustawia również akcje listenerów kliknięcia na element listy oraz kliknięcia na przycisk usuwania.
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Song song = mValues.get(position);
        holder.mItem = song;
        holder.mSongTitle.setText(song.getTitle());

        if (song.getPhotoFilename() != null) {
            // jeśli utwór ma przypisane zdjęcie to odczytuje je z pliku i ustawia
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = PicUtils.decodePic(song.getPhotoFilename(), holder.mSongImage.getWidth(), holder.mSongImage.getHeight());
                    holder.mSongImage.setImageBitmap(bitmap);
                }
            }, 200);
        } else {
            // jeśli nie to ustawia domyślną grafikę
            holder.mSongImage.setImageResource(R.mipmap.ic_launcher);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentClickInteraction(holder.mItem, holder.getAdapterPosition());
                }
            }
        });
        holder.mRemoveSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentRemoveClickInteraction(holder.mItem, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mSongImage;
        public final TextView mSongTitle;
        public final ImageButton mRemoveSong;
        public Song mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSongImage = (ImageView) view.findViewById(R.id.song_image);
            mSongTitle = (TextView) view.findViewById(R.id.song_title);
            mRemoveSong = (ImageButton) view.findViewById(R.id.remove_song);
        }

    }
}
