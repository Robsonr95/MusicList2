package pl.musiclist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.musiclist.R;
import pl.musiclist.fragment.SongInfoFragment;
import pl.musiclist.model.Song;

// Activity ze szczegółami utworu, zawiera Fragment
public class SongInfoActivity extends AppCompatActivity {

    public static final String SONG = "SONG";

    // Metoda wywoływana po starcie Activity.
    // Odczytuje przekazany argument z utworem i ustawia go w Fragmencie.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_info);

        Song song = getIntent().getParcelableExtra(SONG);

        SongInfoFragment songInfoFragment = (SongInfoFragment) getSupportFragmentManager().findFragmentById(R.id.song_list_fragment);
        if(songInfoFragment != null)
            songInfoFragment.showSongDetails(song);
    }
}
