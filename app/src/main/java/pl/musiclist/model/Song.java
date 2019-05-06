package pl.musiclist.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

// Klasa modelu do przechowywania utworu, zawiera pola z tytułem, autorem, rokiem wydania oraz ścieżką do zdjęcia (jeśli jest).
// Implementuje interfejs Parcelable, który umożliwia przekazywanie elementu do Activity lub Fragmentu przez Bundle
public class Song implements Parcelable {

    private String title;
    private String author;
    private String year;
    private String photoFilename;

    public Song(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Song(String title, String author, String year, String photoFilename) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.photoFilename = photoFilename;
    }

    protected Song(Parcel in) {
        title = in.readString();
        author = in.readString();
        year = in.readString();
        photoFilename = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(year);
        dest.writeString(photoFilename);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPhotoFilename() {
        return photoFilename;
    }

    public void setPhotoFilename(String photoFilename) {
        this.photoFilename = photoFilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (title != null ? !title.equals(song.title) : song.title != null) return false;
        if (author != null ? !author.equals(song.author) : song.author != null) return false;
        if (year != null ? !year.equals(song.year) : song.year != null) return false;
        return photoFilename != null ? photoFilename.equals(song.photoFilename) : song.photoFilename == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (photoFilename != null ? photoFilename.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", photoFilename='" + photoFilename + '\'' +
                '}';
    }
}
