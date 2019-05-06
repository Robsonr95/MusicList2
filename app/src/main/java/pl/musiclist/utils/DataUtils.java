package pl.musiclist.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import pl.musiclist.model.Song;

// Klasa ze statycznymi metodami związanymi z odczytem i zapisem list utwórów do pliku.
public class DataUtils {

    private static final String SONGS_LIST_FILE = "songs.txt";

    // Metoda odczytuje listę utworów z pliku, jeśli plik nie istnieje to zwraca pustą listę.
    // W pliku są zapisane utwory w kolejnych liniach.
    public static List<Song> readSongsList(Context context) {
        String filePath = getFilePath(context);
        List<Song> songs = new ArrayList<>();
        try {
            FileInputStream is = new FileInputStream(new File(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            do {
                line = reader.readLine();
                if(line != null) {
                    String[] elements = line.split(";;");
                    Log.d("PARSE", "len " + elements.length);
                    if (elements.length == 4) {
                        String title = elements[0];
                        String author = elements[1];
                        String year = elements[2];
                        String imagePath = elements[3];
                        Song song = new Song(title, author, year, imagePath);
                        songs.add(song);
                    } else if(elements.length == 3) {
                        String title = elements[0];
                        String author = elements[1];
                        String year = elements[2];
                        Song song = new Song(title, author, year);
                        songs.add(song);
                    }
                }
            } while(line != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    // Metoda zapisuje listę utworów przekazaną jako argument do pliku.
    // Lista jest zapisywana do pliku linia po linii z polami oddzielonymi dwoma średnikami.
    public static void saveSongsList(Context context, List<Song> songs) {
        String filePath = getFilePath(context);
        try {
            PrintWriter out = new PrintWriter(filePath);
            for(Song song : songs) {
                if(song.getPhotoFilename() != null)
                    out.println(song.getTitle() + ";;" + song.getAuthor() + ";;" + song.getYear() + ";;" + song.getPhotoFilename());
                else
                    out.println(song.getTitle() + ";;" + song.getAuthor() + ";;" + song.getYear());
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Metoda zwraca absolutną ścieżkę do pliku z listą utworów
    private static String getFilePath(Context context) {
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        return directory.getAbsolutePath() + "/" + SONGS_LIST_FILE;
    }


}
