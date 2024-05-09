package com.example.depremradari;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepremDataKandilli {
    private String _id;
    private String title;
    private String district;
    private String province;
    private String date;
    private String mag;
    private String depth;
    private String latitude;
    private String longitude;

    public DepremDataKandilli(String _id, String title, String date, double mag, String depth, double[] coordinates) {
        this._id = _id;
        this.title = title;
        String[] cityInfo = processCity(title);
        this.district = cityInfo[0];
        this.province = cityInfo[1];
        this.date = formatTime(date);
        this.mag = formatMag(mag);
        this.depth = depth;
        this.latitude = String.valueOf(coordinates[0]);
        this.longitude = String.valueOf(coordinates[1]);
    }

    private String[] processCity(String cityString) {
        String[] parts = cityString.split("\\("); // Şehir adı ve ilçe bilgisini ayır
        String district = parts[0].trim().replaceAll("-", " "); // Şehir adını al ve tireleri boşlukla değiştir
        String province = ""; // İl bilgisi için boş bir string tanımla

        // Eğer ilçe ve il bilgisi varsa, ilçe bilgisini ve parantez içindeki il bilgisini al
        if (parts.length > 1) {
            province = parts[1].length() > 1 ? parts[1].replaceAll("\\)", "") : ""; // Parantez içindeki il bilgisini al
        }
        return new String[] { district, province };
    }

    private String formatTime(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Date logTime = format.parse(timeStamp);
            Date currentTime = new Date();

            // Zaman farkını hesapla (milisaniye cinsinden)
            long timeDifference = currentTime.getTime() - logTime.getTime();

            // Dakika cinsine dönüştür
            long minutesDifference = timeDifference / (1000 * 60);

            // Farka göre zamanı formatla
            if (minutesDifference < 60) {
                // 1 saatten az ise dakika cinsinden göster
                return minutesDifference + " dakika önce";
            } else {
                // 1 saatten fazla ise saat cinsinden göster
                long hoursDifference = minutesDifference / 60;
                return hoursDifference + " saat önce";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String formatMag(double mag) {
        return mag % 1 == 0 ? String.valueOf(mag) : String.format("%.1f", mag);
    }

    // Getter ve setter metotları burada olabilir
}

