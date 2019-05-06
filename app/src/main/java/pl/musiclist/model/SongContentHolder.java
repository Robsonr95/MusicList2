package pl.musiclist.model;

import java.util.ArrayList;
import java.util.List;

// Klasa służąca do przechowywania listy utworów wraz z funkcjami do dodawania i usuwania elementów listy.
public class SongContentHolder {

    public static final List<Song> SONGS = new ArrayList<>();

    // Czyści aktualną listę oraz dodaje wszystkie elementy z listy przekazanej jako argument
    public static void addItems(List<Song> songs) {
        SONGS.clear();
        SONGS.addAll(songs);
    }

    // Dodaje nowy element listy.
    public static void addItem(Song song) {
        SONGS.add(song);
    }

    // Usuwa element listy na pozycji przekazanej jako argument.
    public static void removeItem(int position) {
        SONGS.remove(position);
    }

}
