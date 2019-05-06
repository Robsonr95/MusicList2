package pl.musiclist.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

// Klasa z metodami służącymi do odczytu obrazka z pliku
public class PicUtils {

    // Metoda odczytuje obraz z pliku oraz zmniejsza odpowiednio jego rozmiary, aby dopasować do miejsca gdzie ma zostać wyświetlony.
    public static Bitmap decodePic(String pPath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pPath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pPath, options);
    }

    // Metoda oblicza współczynnik o ile ma być pomniejszony obraz względem jego oryginalnych rozmiarów,
    // aby wielkością był dopasowany do miejsca gdzie ma zostać wyświetlony (chodzi o oszczędzanie pamięci)
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
