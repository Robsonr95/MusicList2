package pl.musiclist.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import pl.musiclist.R;
import pl.musiclist.model.Song;
import pl.musiclist.model.SongContentHolder;
import pl.musiclist.utils.DataUtils;
import pl.musiclist.utils.PicUtils;

// Activity dodawania nowego utworu.
public class AddSongActivity extends AppCompatActivity {

    public static final int ADD_SONG_REQUEST_CODE = 10;
    public static final String IMAGE_PATH = "image_path";

    private ImageView image;
    private EditText title;
    private EditText author;
    private EditText year;

    private String imagePath;

    // Metoda wywoływana po starcie Activity.
    // Odczytuje przekazany (o ile był przekazany) argument ze ścieżką zdjęcia, które ma być przypisane do utworu i wyświetla je.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        year = findViewById(R.id.year);

        findViewById(R.id.add_song).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSong();
            }
        });

        if(getIntent() != null) {
            String imagePath = getIntent().getStringExtra(IMAGE_PATH);
            if(imagePath != null) {
                setImagePath(imagePath);
            }
        }
    }

    // Metoda sprawdza czy wszystkie pola są wypełnione i jeśli tak to tworzy na ich podstawie obiekt Song z utworem.
    // Jeśli jest ustawione zdjęcie to przypisuje jego ścieżkę do utworu.
    // Następnie dodaje obiekt do listy utwórów i zapisuje listę w pliku.
    // Kończy Activity z RESULT_OK, które jest później interpretowane w Activity wyświetlanym wcześniej.
    private void addSong() {
        if(title.getText().length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_title, Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(author.getText().length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_author, Snackbar.LENGTH_SHORT).show();
            return;
        }
        try {
            Integer.parseInt(year.getText().toString());
        } catch (NumberFormatException ignored) {
            Snackbar.make(findViewById(android.R.id.content), R.string.invalid_year, Snackbar.LENGTH_SHORT).show();
            return;
        }

        Song song = new Song(title.getText().toString(), author.getText().toString(), year.getText().toString());
        if(imagePath != null)
            song.setPhotoFilename(imagePath);
        SongContentHolder.addItem(song);
        DataUtils.saveSongsList(this, SongContentHolder.SONGS);
        setResult(Activity.RESULT_OK);
        finish();
    }

    // Metoda ustawia ścieżkę do zdjęcia i wyświetla je w ImageView
    private void setImagePath(final String imagePath) {
        this.imagePath = imagePath;

        image.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = PicUtils.decodePic(imagePath, image.getWidth(), image.getHeight());
                image.setImageBitmap(bitmap);
            }
        }, 200);
    }
}
