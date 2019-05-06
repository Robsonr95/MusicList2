package pl.musiclist.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.musiclist.utils.PicUtils;
import pl.musiclist.R;
import pl.musiclist.model.Song;

// Fragment wyświetlania szczegółów utworu, w zależności od trybu: pionowego - Fragment wyświetlany w osobnym Activity
// w trybie poziomym - wyświetlany w MainActivity obok Fragmentu z listą utworów.
public class SongInfoFragment extends Fragment {

    private TextView songTitle;
    private TextView songAuthor;
    private TextView songDate;
    private ImageView songImage;

    // Metoda inicjalizuje widok z pliku xml z layoutem, przypisuje widoki do zmiennych
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);

        songTitle = view.findViewById(R.id.song_title);
        songAuthor = view.findViewById(R.id.song_author);
        songDate = view.findViewById(R.id.song_date);
        songImage = view.findViewById(R.id.song_image);

        return view;
    }

    // Metoda wyświetla szczegóły utworu przekazanego jako argument, jeśli jest nullem to wyświetla puste wartości.
    public void showSongDetails(final Song song) {
        if(song != null) {
            songTitle.setText(song.getTitle());
            songAuthor.setText(song.getAuthor());
            songDate.setText(String.valueOf(song.getYear()));
            if(song.getPhotoFilename() != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = PicUtils.decodePic(song.getPhotoFilename(), songImage.getWidth(), songImage.getHeight());
                        songImage.setImageBitmap(bitmap);
                    }
                }, 200);
            } else {
                songImage.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            songTitle.setText("");
            songAuthor.setText("");
            songDate.setText("");
            songImage.setImageDrawable(null);
        }
    }
}
