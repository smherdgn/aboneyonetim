package tr.com.semih.classes.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Abone {
    String aboneNumarası;
    BigDecimal borcYuklemeTutari;
    Date sonOdemeTarihi;
    String donemYil;
    String faturaNo;


    public String getAboneNumarası() {
        return aboneNumarası;
    }

    public void setAboneNumarası(String aboneNumarası) {
        this.aboneNumarası = aboneNumarası;
    }

    public BigDecimal getBorcYuklemeTutari() {
        return borcYuklemeTutari;
    }

    public void setBorcYuklemeTutari(BigDecimal borcYuklemeTutari) {
        this.borcYuklemeTutari = borcYuklemeTutari;
    }

    public Date getSonOdemeTarihi() {
        return sonOdemeTarihi;
    }

    public void setSonOdemeTarihi(Date sonOdemeTarihi) {
        this.sonOdemeTarihi = sonOdemeTarihi;
    }

    public String getDonemYil() {
        return donemYil;
    }

    public void setDonemYil(String donemYil) {
        this.donemYil = donemYil;
    }

    public String getFaturaNo() {
        return faturaNo;
    }

    public void setFaturaNo(String faturaNo) {
        this.faturaNo = faturaNo;
    }

    @Override
    public String toString() {
        return "Abone{" +
                "aboneNumarası='" + aboneNumarası + '\'' +
                '}';
    }

    public static List<String> getFieldNames() {
        List<String> fields = new ArrayList<String>();
        fields.add("Abone Numarası");
        fields.add("Borç Yükelem Tutarı");
        fields.add("Son Ödeme Tarihi");
        fields.add("Dönem Yıl");
        fields.add("Fatura No");
        return fields;
    }


    public String[] toArray() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new String[]{aboneNumarası, borcYuklemeTutari.toString(), format.format(sonOdemeTarihi), donemYil, faturaNo};
    }

    public static String getHtmlHeaders() {
        StringBuilder header = new StringBuilder();
        header.append("<th>").append("Abone Numarası").append("</th>").append("\n")
                .append("<th>").append("Borç Yükelem Tutarı").append("</th>").append("\n")
                .append("<th>").append("Son Ödeme Tarihi").append("</th>").append("\n")
                .append("<th>").append("Dönem Yıl").append("</th>").append("\n")
                .append("<th>").append("Fatura No").append("</th>").append("\n");

        return header.toString();
    }

    public String getHtmlRow() {
        StringBuilder header = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        header.append("<tr>")
                .append("<td>").append(aboneNumarası).append("</td>").append("\n")
                .append("<td>").append(borcYuklemeTutari).append("</td>").append("\n")
                .append("<td>").append(format.format(sonOdemeTarihi)).append("</td>").append("\n")
                .append("<td>").append(donemYil).append("</td>").append("\n")
                .append("<td>").append(faturaNo).append("</td>").append("\n")
                .append("</tr>");

        return header.toString();

    }
}
