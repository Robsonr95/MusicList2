package pl.musiclist.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.musiclist.R;
import pl.musiclist.dialog.DeleteDialog;
import pl.musiclist.fragment.SongListFragment;
import pl.musiclist.fragment.SongInfoFragment;
import pl.musiclist.model.Song;
import pl.musiclist.model.SongContentHolder;
import pl.musiclist.utils.DataUtils;

// Główne Activity uruchamiane po starcie aplikacji, zawiera Fragment z listą utworów.
// W trybie poziomym zawiera również Fragment ze szczegółami utworu.
public class MainActivity extends AppCompatActivity implements SongListFragment.OnListFragmentInteractionListener, DeleteDialog.OnDeleteDialogInteractionListener {

    public static final int REQUEST_IMAGE_CAPTURE = 11;
    private String mCurrentPhotoPath;

    // Metoda wywoływana po starcie Activity. Odczytuje z pliku listę utwórów, przypisuje listenery do przycisków dodawania i kamery.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongContentHolder.addItems(DataUtils.readSongsList(this));

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddSongActivity.class), AddSongActivity.ADD_SONG_REQUEST_CODE);
            }
        });

        findViewById(R.id.fab_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

    }

    // Metoda sprawdza wynik zwrócony przez inne Activity i wykonuje odpowiednią akcję.
    // Jeśli jest to wynik Activity które dodaje nowy utwór to odświeża wyświetlana listę.
    // Jeśli jest to wynik wykonania zdjęcia to otwiera nowe Activity dodawania utworu i przekazuje tam ścieżkę do zdjęcia.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddSongActivity.ADD_SONG_REQUEST_CODE) {
                refreshList();
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    Intent intent = new Intent(MainActivity.this, AddSongActivity.class);
                    intent.putExtra(AddSongActivity.IMAGE_PATH, mCurrentPhotoPath);
                    startActivityForResult(intent, AddSongActivity.ADD_SONG_REQUEST_CODE);
                }
            }
        }
    }

    // Metoda wywoływana po potwierdzeniu usunięcia utworu z listy, usuwa element z listy, aktualizuje listę w pliku oraz odświeża widok listy.
    @Override
    public void onDeleteDialogPositiveClick(int position) {
        SongContentHolder.removeItem(position);
        DataUtils.saveSongsList(this, SongContentHolder.SONGS);
        refreshList();
    }

    // Metoda wywoływana po kliknięciu na utwór na liście.
    // Wyświetla szczegóły utworu w fragmencie (tryb poziomy) lub otwiera nowe Activity ze szczegółami utworu.
    @Override
    public void onListFragmentClickInteraction(Song song, int position) {
        SongInfoFragment songInfoFragment = (SongInfoFragment) getSupportFragmentManager().findFragmentById(R.id.song_info_fragment);
        if (songInfoFragment != null && songInfoFragment.isInLayout()) {
            songInfoFragment.showSongDetails(song);
        } else {
            Intent intent = new Intent(this, SongInfoActivity.class);
            intent.putExtra(SongInfoActivity.SONG, song);
            startActivity(intent);
        }
    }

    // Metoda wywoływa na po kliknięciu na przycisk usunięcia utworu. Otwiera okienko z potwierdzeniem usunięcia.
    @Override
    public void onListFragmentRemoveClickInteraction(Song song, int position) {
        DeleteDialog.newInstance(position).show(getSupportFragmentManager(), "DeleteDialog");
    }

    // Odświeża widok listy utwórów.
    private void refreshList() {
        SongListFragment songListFragment = (SongListFragment) getSupportFragmentManager().findFragmentById(R.id.song_list_fragment);
        if (songListFragment != null && songListFragment.isInLayout()) {
            songListFragment.notifyDataChanged();
        }
    }

    // Metoda wywołuje Intent kamery do zrobienia zdjęcia.
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, getString(R.string.myFileprovider), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // Metoda tworzy tymczasowy plik, gdzie ma zostać zapisane zdjęcie wykonane aparatem.
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(timestamp, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
