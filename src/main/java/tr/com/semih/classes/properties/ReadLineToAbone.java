package tr.com.semih.classes.properties;

import tr.com.semih.classes.beans.Abone;
import tr.com.semih.classes.logging.AbstractLogger;
import tr.com.semih.classes.logging.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReadLineToAbone {

    private AbstractLogger loggerChain = Logger.getChainOfLoggers(this.getClass());

    public Abone readAbone(String line) {
        Abone abone = null;
        if (line.length() < 61) {
            loggerChain.logMessage(AbstractLogger.FILE, "Satır uzunluğu olması gerektiğinden daha kısa");
            Global.getErrorList().put(line, "Satır uzunluğu olması gerektiğinden daha kısa");
            return null;
        } else {
            abone = new Abone();
            abone.setAboneNumarası(line.substring(1, 10));


            String borcTutari = line.substring(18, 33);
            try {
                abone.setBorcYuklemeTutari(new BigDecimal(borcTutari));
            }catch (NumberFormatException e)
            {
                Global.getErrorList().put(line, "Borç Tutarı Düzgün Format da Değil");
                loggerChain.logMessage(AbstractLogger.FILE, e.getMessage());
                return null;
            }



            String tarih = line.substring(33, 43);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                abone.setSonOdemeTarihi(format.parse(tarih));
            } catch (ParseException e) {
                Global.getErrorList().put(line, "Son Ödeme Tarihi Dönüştürülemedi");
                loggerChain.logMessage(AbstractLogger.FILE, e.getMessage());
                return null;
            }

            abone.setDonemYil(line.substring(46, 50));


            abone.setFaturaNo(line.substring(50, 61));
        }
        return abone;
    }
}
