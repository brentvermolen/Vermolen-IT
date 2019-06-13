package com.example.vermolenit.Class;

import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.CalendarContract;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;
import com.example.vermolenit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.PRINT_SERVICE;

public class Methodes {
    public static List<Kasticket> getKasticketByKlant(List<Kasticket> kastickets, Klant klant){
        List<Kasticket> kasticketen = new ArrayList<>();

        for (Kasticket k : kastickets){
            if (k.getKlant_id() == klant.getId()){
                kasticketen.add(k);
                k.setKlant(klant);
            }
        }

        return kasticketen;
    }

    public static Artikel getArtikelById(List<Artikel> artikels, int artikel_id) {
        for (Artikel artikel : artikels){
            if (artikel.getId() == artikel_id){
                return artikel;
            }
        }

        return null;
    }

    public static Kasticket getKasticketById(List<Kasticket> kastickets, int kasticket_id) {
        for (Kasticket kasticket : kastickets) {
            if (kasticket.getId() == kasticket_id){
                return kasticket;
            }
        }

        return null;
    }

    public static Klant getKlantById(List<Klant> klanten, int klant_id) {
        for (Klant klant : klanten){
            if (klant.getId() == klant_id){
                return klant;
            }
        }

        return null;
    }

    public static List<Artikel> getWhereVoorraadIsLow(List<Artikel> artikels){
        List<Artikel> data = new ArrayList<>();

        for (Artikel a : artikels){
            if (a.getVoorraad() <= a.getMeldingOpVoorraad() && a.getVoorraad() != -1 && a.getMeldingOpVoorraad() != -1){
                data.add(a);
            }
        }

        return data;
    }


    public static String getIntroHtml(){
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        * {\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        .column {\n" +
                "            float: left;\n" +
                "            width: 20%;\n" +
                "        }\n" +
                "\n" +
                "        .row:after {\n" +
                "            content: \"\";\n" +
                "            display: table;\n" +
                "            clear: both;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            margin: 2px;\n" +
                "        }\n" +
                "\n" +
                "        h2 {\n" +
                "            margin: 5px;\n" +
                "        }\n" +
                "\n" +
                "        h3 {\n" +
                "            margin: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .bedrijf_info {\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        .one_third, .one_fourth, .one_half, .three_fourth, .two_third {\n" +
                "            display: inline-block;\n" +
                "            float: left;\n" +
                "            margin: 0 0 0 3.06748466257669%;\n" +
                "            list-style: none;\n" +
                "        }\n" +
                "\n" +
                "        .three_fourth {\n" +
                "            width: 69%;\n" +
                "        }\n" +
                "\n" +
                "        .one_half {\n" +
                "            width: 47%\n" +
                "        }\n" +
                "\n" +
                "        .one_third {\n" +
                "            width: 31.28834355828221%;\n" +
                "        }\n" +
                "\n" +
                "        .two_third {\n" +
                "            width: 62.5%\n" +
                "        }\n" +
                "\n" +
                "        .one_fourth {\n" +
                "            width: 23%\n" +
                "        }\n" +
                "\n" +
                "        .first {\n" +
                "            margin-left: 0;\n" +
                "            clear: left;\n" +
                "        }\n" +
                "\n" +
                "        .logo {\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        hr {\n" +
                "            background-color: red;\n" +
                "        }\n" +
                "\n" +
                "        .artikels {\n" +
                "            padding-top: 30px\n" +
                "        }\n" +
                "\n" +
                "        .artikels th {\n" +
                "            background-color: aqua;\n" +
                "            border: solid 1px black;\n" +
                "            padding-top: 15px;\n" +
                "            padding-bottom: 15px;\n" +
                "            padding-left: 15px;\n" +
                "        }\n" +
                "\n" +
                "        .artikels tr:nth-child(even) {\n" +
                "            background-color: azure;\n" +
                "        }\n" +
                "\n" +
                "        .artikels td {\n" +
                "            padding-left: 15px;\n" +
                "            border: solid 1px black;\n" +
                "            padding-top: 15px;\n" +
                "            padding-bottom: 15px;\n" +
                "        }\n" +
                "\n" +
                "        .prijzen {\n" +
                "            text-align: right;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .betaalinfo {\n" +
                "            padding-top: 15px;\n" +
                "            text-align: right;\n" +
                "        }" +
                "\n" +
                "        .title {\n" +
                "            margin-top: 15px;\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            position: absolute;\n" +
                "            bottom: 10px;\n" +
                "            width: 100%;\n" +
                "            border-top: 1px solid black;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .header {\n" +
                "            padding-bottom: 8px;\n" +
                "        }" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"header\">\n" +
                "        <div class=\"bedrijf_info two_third first\">\n" +
                "            <h1>Vermolen-IT</h1>\n" +
                "            <h3>BTW BE0726.879.495</h3>" +
                "            <h3>vermolen-it@hotmail.com</h3>\n" +
                "            <h3>Consciencestraat 8</h3>\n" +
                "            <h3>2330 Merksplas</h3>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAO" +
                "EAAADhCAYAAAA+s9J6AAAYjklEQVR4Xu3de3RV1Z0H8L3PJY+bhAB5ACa5Sajo2Focuuj4QM" +
                "cWq9Jql52uqiNLHaYzFlDpVCud2qIullNfCOrqwym0zui4XDOLsQuLEsdWO7SOiIgIvrAgEA" +
                "iRRwKBPG6S+zh71g2ckNzcc+/e++zz/vqn+e199vnt34d97j7n3EsJ/kMGkAFXM0BdPbofDs7" +
                "WRGZ0dF+s6doVlLKP0wODLe833dblh6FjjP7IABCazZOBL0W/RjVaNRzGWIpoZCMw+qPA/TBKI" +
                "MyapVlbVhUlGiMXadn4smcTGP1Q374YIxCemqYMvmRD5FKNReYSyiZwzx4wcqcKgbkzEHqE0viw" +
                "MsKUogyEFqEyfMCoqBTD203oENqGDxjDq8jimYcGoWP4gNFiSYaveeARuoYPGMOnSfKMA4vQM/h" +
                "MMMbTqVd21i3slJw3NAtQBgKH0LP4soqGMaZTQt6Ks9RLwBggURKnEhiEfsE3dmEERom6DVQT3yP" +
                "0Kz5gDJQjSyfjW4RBwQeMluo3EI19h3D6rp+WlI0vu0T48TKfTRc+M/pswiwM1zcIh/CVl82hlF5B" +
                "Ka2wcM6+agqMvpouqcF6HmFY8eEyVaqefdnIswiBL3c9YWX0pbO8g/YcQuDjKzJg5MuTH6I8gxD45M" +
                "oFGOXy5qVWriMEPjXlAIxq8uhGL64hBD57phsY7cmrnb06jhD47JzO030bGFOa/vIHU75z2Jmj4igy" +
                "GXAMIfDJTI/1NoxRnTJ9ayqirwNG6/m0owfbEQKfHdMm3icwiufMqRa2IQQ+p6ZQ7DjAKJYvJ6KVI2" +
                "xoeyxaFan8UtgeL3NislQeAxhVZtNaX8oQDuErqrxc07WvEI1ErQ0LrZ3KADA6lWnz41hGCHzuT6KKE" +
                "QCjiizK9SGNEPjkEu71VsDo/AwJIwQ+5yfJjSMCo3NZ50YIfM5NipeOBIz2z0ZBhMBn/yT44QjAaN8sm" +
                "SIEPvuS7ueegVH97I1BCHzqkxzEHg2MLD2uZXtsfnsQz9GpcxpGCHxOpTx4x6GUbNMTkXXAKDe3QwhnH" +
                "vz3qwhjV+Imu1wS0epkBjIYB442vLrjc1fuQk74M0AJWxOZebj3Sf4miEQGxmaApkqrWbK8kZ1oatf6z/" +
                "idzhLrts+O4TKVo1iAkCNJCDHPgIGPMm3oayj17tifaXzqkZMrI90GjIWrBwgL5wgROTKQjc8IGYnQ+H/A" +
                "mL+EgBDExDKQjNbSZLSRkEhZroa5EAIjEIoVGaJzZ6AAvnwrYXaHWBlHZwQrIdDlzwAnPhGEWBmBEOx4Mi" +
                "CITwYhMJ7MAFZCnoIMU4wkPisIw44RCMMELN+5WsSnAuFIjERPt7x7cd2+MEwPEIZhlh3ApxLhcF+MfBgh" +
                "+m+DjhEIw4pQ0cqXnb58tyhkU60HHCMQylaGX9vZhM+OlXAM8IBiBEK/YhIdt834nEAY1MtUIBQtZj/F65" +
                "SSdGlNvidcVJ+OHZejZmMMymUqEKquQi/0N4QvOpmkShspi5Q6OSQnEQZlZQRCJyvU7mO5iM/Jy9GgrYxA" +
                "aDcMJ/r3AD4vIPTrygiETiCx6xgewuclhH7DCIR2AbGzXw/i8yJCv2AEQjuxqO7bw/i8jNDrGIFQNRQ7+v" +
                "MBPj8g9CpGILQDjao+fYTPTwhHYkxTreXD2ZM/UTVlMv0AoUzW7G7jQ3x+RHh6GtknSRJZ6xZGILQblEj/" +
                "Psbnb4TG6N3BCIQiSOyKDQC+YCB0ByMQ2gWLq19KSdKdx8u4hicR5MZjaxLD5GzizMoIhJzToTaMUpYom0" +
                "rTpTHKtBK1fbvbW7AQOrMyAqGjNRtcfMG6HDUrCntWRiB0BGHw8YUDoT0rIxDaijA8+MKFUC1GILQFYfjw" +
                "hROhGoxAqBRhePGFG6E1jECoBCHwAeHIQhLbwAFCSwiBLzt9wbxFIVckjLGdZNy4F7dfULszXw9AKJVf4" +
                "DNLGxCOzQxjbA9h5KXtl5zxYa68AaEQQuArlC4gNM+QGUYgLFRVwx92KqrIYMn0oD3hwnv6vHFAyJEpSvZt" +
                "u2jqg0YkEBbIGaNlUS3d0KzTCfVaOl1Jkl29hPX2c6Q6lCFAyDftxwf7v9c6Z9pAJhoITXJm4GN0QjUlhD" +
                "JCSklam3gyack0MOZOHBACIV8G8kXp5eWU1Dca+IbvAI1AePoyAhixOypXclgJc+VNLy8nkcYmqldU5/rz" +
                "yJUw++9YGU9nBCshH0ogHJmnAvjyrYTAOLbggBAI+TKQieLEJ4IQl6mEACFfCYZ7JRTEJ4MwzBiBEAjNM" +
                "yCJzwrCMGIEQiDM8SEl/4YLX8oIybcxw9tHGDZwgJCvGsJxOWpx5ctOpQqEY1ZG0tdPGOObNZ9EASHfRA" +
                "UboWJ8Ki5HzaaF6ok0Y929VO8JDEYgDDNCm/DZiXB4ZQwQRiAMI0Kb8TmBMEgYgTBMCB3C5yTCIGAEwlA" +
                "grKwgtL7R7PEyvhSIR6ncmOE9uh8/MwIh3+z6dGOmsoKQ+mbKKibxnabaKDcQ+nFlBEK+unMF4aGVL5516" +
                "NG1c/iGeDqqYfmCP9b83beLzPB1b9hYtnvewiaRfideffmJ2GP3HxpXOV7nbecmwlwYEx3HIq2Ll9f3bf6" +
                "gnPccYg/c3l4z76vdvPHty5+uOfLL39TyxquKq7p63pbGpY9vVdWfF/txBeGxtZum7r919TWiCalftuTg5" +
                "IXzj5u163zuN5VtS5bVi/Rbe8uNnfXLlnTQyDjuZl5AOBJjonN/vHXR0uogIqz+xs2bYz98dBv35Pgw0BW" +
                "EvZt3Tdh326rLkweO5XxVyCyPk2+d31l3710dlNKcIQefWFV16JGfTxGZh0Kwc/XlJYSZ8aWOddHWW79f" +
                "3vvG5iLecxdZCdPxAdp2zy+mdL2wwfHL/6kL7/7D1Pl3uPrrubw5lY1zBWHyYFfxnvk/u6z/vdZGkYFPu" +
                "vbrXbHl9x2ORKM5Hy1pf+DxmiM//zehS6ZpT/90/8S5c/pExuFJhLctKe/9v01AKDKRHol1BaHeMxBpvX" +
                "3Vhd2/236uSB7GX3JBb/PqFe3jJk0c8/kt3d9P2/75/ildz7/E/a91caw+0bx6xYHymZ8fFBlH2BCmTvR" +
                "o++58rK57w5bxInlSEdv448fXV319XruKvrzahysIM8lo+8EzM48++8fzRRITPe+z/dN++Wh7ybSmZHa7" +
                "dLyfti1ZNrVrbcvQd7/w/Fc267x486oV7SX1Z6R44o2YsCFMdHYJb/yI5DNfbPNPVv924mXXHFbVnxf7c" +
                "Q1h51OvNR1Y+txckaTkW7kSHUcjrQvuqu/b9A73DqHMzmhmvKFDeCSz+/pIfd+Wj7hzKzKvQOjKt61VVv" +
                "S+2XFu6y0/Pjd1tIt/W5IQcuZ/rtpX+eXZ8eyJG9y7r2jvoh/U97+3I8pbADI7o4FB+ODi9pob5nLdooh" +
                "/3Frc+t3lDYO72xz9JeGiqQ1Hm5Y9+WrFeeef4J1TP8Y5vBKevskugyaT4NiKZe01N35rTPHEd+wqbl24" +
                "pGFw1x7uQpHZGQ0KwsaV9/dUXTvnGNVPxAu9QgWE9tJ2COHYJ1xS3T1a2/fvm3p8/asTRE5xyh0LjtT98" +
                "LtHs9t0v/5WdPf1tzSL9CWzMxoYhI8/2Fd13TcShCXTRO/py4exd8uO0r2LHoiljp0QumoRmYtcsaXT/u" +
                "LTaQ/86g8lzWePufKx2reX2tuKkJGqSkqmNOZ6woWlU6R92Yrajl8/VyOSkOqbrz0W+8mPDtPi4lHNRJ+" +
                "Wkd0ZDRzC4d0mc4zdG7dHd990j9A/cCJzahYbPevzB5qXP/1ayZQGod1rFcd2sg9bEDIysZKw+mkaKavM" +
                "dzIdz6yZcODuf6kTOWGzzRTRp2XKz/9CX/OvVrYXT65Nixw/sAjzYGSJJEl290by5Sn+zo7Svbc+JHTft" +
                "+HuR1vGXzCnw6xfLRJh46prE5Q6ugCLloPleKUIefEZoxZdvTLtyi+c1de8emV7cW31KDyiT8tM+uZVx2" +
                "Mrlh2KlOW+8Z8vs0HYHW00LkfNTpTjMnVkU5nVMgy3H3iEKkEois8YWPyjncWtC+5qGNzdyr2ZUnLWZwa" +
                "bV604UPbZsxIjT1D0aZlCj8CFHiHHZSoQ8hArHGMJoSw+Y1gy9/a0sqh+5rO/2F8x+6+Gfw1J5mmZhofv" +
                "/bR2/vVSW9+hWAmza6fAyoiVsDA2swgphFbxGYORwZNp2/yvj7ZN+puv9hr9yOy0mt1v5EllKBEWWBmBkKdycscIIVSF" +
                "b+RQPn3kZ9WHn1g9WeQUsu/via6oJWc2DzavXnmg7HNnj7qk5R1DqBGaYARC3uoZG8eF0A58xlCO/tfay" +
                "v133if0DuDkxf/QUb/0zk6jD9Eb/1Z2RjPHBMIRhXTqMrXn9T+x3TctFbqNgY2Zk3nMi9BOfMY09r79bu" +
                "neb38vJvL4WvYrTaJPy1jZGQXC3P/i97y5ie6+7h+5H54f+lgRgoezedbHnAidwCe7imXaZb/SJPq0jJW" +
                "dUSDMXVa9m7aM++Ta+UKvOgFhrpWQEPKXB99Zp5Hi6TyCVcSkjp/Q9i3+UV33a69zT2D2K03HX/nf8r1" +
                "//0/cN4qt7IwCIRCqqPuRfWSthCwy8/DhJxlJVFLWO42SdN4nXlQMRubxtexHzkSflrGyM5rZ0U3HB6J" +
                "U14SeeeXJlTa+gmlF3C/HD3c59PUWgm/WF7xZzzPgETFYCQUTNiI8J0Lj705hPLLqmYnty1acIXIaZ67" +
                "5dWvlX18wdK9QZIfV6s6oKHiRc5r+/DM9FRd+UegF40z/nkD45lbtk+tuFvqHCZejOTdmTq6E2YVjN0b" +
                "Ry8nM+Ea+0iTytIzVnVEgHF0dlGhpli7t6/nT22T3zdcLfd0kEAogtHtllHl8zXilSfSGv9WdUSA8WQ0" +
                "GPqqXxjP3bLo3vh7dfdP1uEUhculzKjbv5ahZf6pXxsSRjkjrd+6q79v8LvfXJxivNKUHBoXeS7S6Mxp" +
                "2hNn4jBoBQgl9VhCqXhlFV7PM8Y0VLd0X10S+W8bszXzeFIYVoRk+IOStHPM4qZXQjs+MIp/rMsc3Xmn" +
                "Su05oexfe1TDw8SelPOkYuaHDE58dEzaEhfABoUwVjW6jBKGKlVH08TXjlabMsXm/W8bsNSiRNIYFIS8" +
                "+IBSpntyxShFawdi78e3o7ptvb9Tj/RrPaRmvNOlpnfB+t4zZC8E8xzNigo5QFB8QilSPgwhHYSR9jZS" +
                "lCn4r9sDOPUUil5WZY0RnnDN0n7D//Y+5vuaw0Nfo86QzqAhl8QEhT9Xkj7FlJRx7yGQFIb3N+TDKPL4" +
                "mevrZb1+Its/Ed73wckXHf/x3DY1ExB9tKXDAurvviJfNnCH8nTdWbtZbxQeEMlVk42fCwsMxx8gSCdJ" +
                "2z0NTjj77fFXhfuQirO6Mnl7hSSlJa0JvDMiNmK+VDMKmxx7smfSteceM+3x8RzKPwi0K+Qw6tBJmDzA" +
                "3RpnH10RO3erOaJAQxh5c0V5zw41c38DNk2Mg5MmSC58JCw9rNEaZx9cKH+NkhIqd0UAhfGBFe808IOS" +
                "tHzvjXFoJc6+M8Xe3TW1dsKQh0dY++pt9FWRAxc4oEOJyVEEpjunCIwhPjivRtre6ddGiy+PvbBd6Gp8" +
                "nMSp2RoEQCHlqTTTGUwhTx4+P27do0eyeDRvOET2RQvEqdkaBEAgL1ZnM3z2FkOk62X/HHbO61qyZJXM" +
                "y+dqo2hnNHMOTX/R065Ly3jdEfi4bnwlV15hsf55CmDmJQ088Mf3Qww9fJntCudrl+sJgK/17CSFlWjr" +
                "REY+3Lrq9um/zJu63UGLYmLFSAkrbeg7h8fXrJ++77bar2eCgspvhpedMH5i2auWB0rM/M+ZntmWy6QW" +
                "EGXxMj/ZSvaQ/0dERaV28oB4IZWbT/TaeQ9i3dWtlZnMmuX+/0E+m5Utl9rezWU27mwhH4stcFw9taHU" +
                "CodU5dbO95xAmDx4s3jN//mX9773H/e1phRJY9bfXdDU8dO/hSLT0VNkWapH/724gzIXPGCUQWptPt1t" +
                "7DqEej2v7Fi++4ERLywxVyTH7dV/Z/p1EmA8fEMrOoLfaeQ5hJj3tS5fO6HjqqYtUparx8fvbq2/4prJ" +
                "HtJxAyIMPCFVViLv9eBJh51NPNR1YunSuitSo3hnNjMlOhJREUiyd2XApHjA+8xXKAy5HC2XI23/3JEJ" +
                "1KUuWE9bXREmyWl2f9iCUwafynOzoS++mf6ZxcsSOvoPUZ8ARGlOlFqPKlTCI+IysAyHfPxUhQagWowq" +
                "EQcYHhHz4jKiQIVSD0QrCMOADQiAUyIDcZaoMwjDhA0KBEhz7I6G5f4tCrEs/RothFEEYRnxAKGYgpJe" +
                "jZkniw8iDMMz4gBAIxTKQMzo/xnwIge90QrE7yleKWAnz5ilZTlk8xmiihjJCjdBcCIFvbCKBEAj5MsA" +
                "RRVkqSkhfk4FxJELgM08gEHIUFzZm+JJkRA1jZIkY1YsqRB8vEzua/6OBkG8OcTnKl6dRUYylKrVEpI6" +
                "kSa1E89A0AcL8U80oGSBM37B9dt3a4X/oCQvrLQpJFzopIwnWTHRSPfyBUbKrIDYDwtyzauBLDo77/Ud" +
                "zJveOjKJAKEkhTSpIkjUC4+j8AeHofOTDh5VQ0t6YZsA4KiVAeDIdPPiAUBVCox9gHMpE2BGK4ANC1QiBM" +
                "dQIGSEJorPXk8lIS/ZnvkKlhs+EhTIk+/eQroxhWwkNfD2lJa/s+WLVCZlyAUKZrIm0CRnGsCBUgQ+Xoy" +
                "KQVMSGBGPQEarEB4QqYMn0kcGYIk00zWz7dWKZYalqE1SEduADQlVVJ9kPS7FKmqaxoGEMGkI78QGhJB7" +
                "VzYKGMSgIncAHhKo1WewvKBj9jtBJfEBoEY1dzf2O0bcIKUsRRt/oLi5eL3urQbYmcItCNnM2t/MrRt8h" +
                "PIUvSZL/8+Hs2DGbpzVn90DoRtYFjuk3jL5B6AF8uBwVgOCF0CGMKdpEdTbRC+MxG4PnEXoIHxB6uZLz" +
                "jI2myQSWJI1exehZhB7EB4Q+RTg8cR7F6DmEHsYHhD5H6FWMnkHoA3xAGBCEXsPoOkIf4QPCgCH0CkbX" +
                "EFKWJoS8lWSpF9261SBbUrhFIZs5j7dzawPHcYSn8Ok6ffm9i6f68sdJgdDjmKwO7xTGZqqzSqt98bR3D" +
                "GEA8OFylKeighSTopNIimVubdiK0XaEAcIHhEECJnIuNmO0DWEA8QGhSOEGMdYmjMoRBhgfEAYRlsw5Kca" +
                "oDGEI8AGhTMEGuY0ijFYRMsp0Sug7uk7W+XW3U7RMsDsqmrGgx1vEKIvQwDeYjKzfcWntwaCneeT5AWGYZ" +
                "lvkXCUxiiIMMz5cjooUZIhjaYpVsyRtpIxV8KSBFyHwnc4mVkKeykIM4cVYCCHwjS0mIAQwoQwUwmiGEPj" +
                "M0wyEQiWI4OHPMSaXqdkIga9wzQBh4RwhIk8GsldGAyHw8ZcNEPLnCpEcGNMnyAHax36fTI97IWy3GmQL" +
                "ZOhn12cePHgVYexKomlR2Y7QDhmglG4b+DTx6o4vxHYhG/wZGEKY+a+hrS1aVVR0uabrXwFG/gQikpAMP" +
                "j2RWLc9FmtHPsQzMIwQGMWTF/YWwKemAsYgBEY1iQ1qL4wxnTK2laXTLVj51MyyKUJgVJPgoPRi4EtFIu" +
                "s+mDLlcFDOywvnURAhMHphmtwbA/DZn3tuhMBo/2R46QjA59xsCCMERucmx40jAZ/zWZdGCIzOT5adRwQ" +
                "+O7Obv2/LCIHRvclTcWTgU5FFa30oQzgKYyTyJUrpFZRSrnfQrJ0CWstkAPhksmZPG+UIjWFO37WrpKy8" +
                "fA4w2jNxsr0Cn2zm7GtnG0JgtG/SZHoGPpmsOdPGdoTA6MxEmh0F+NzNP8/RHUMIjDzToS5mCB8hb6U07" +
                "WU84aIur3b05DhCYLRjGk/3aeCLM/bSzrq6TnuPht5VZMA1hMCoYvqAT20W3enNdYTAaG3isfJZy58XWn" +
                "sGITCKlQPwieXLy9GeQwiM+csF+LzMSW5snkUIjKMnFPjkCtwPrTyPMOwYgc8PjKyN0TcIR2EcP/4SjbG" +
                "5hNIJ1k7fu62Bz7tzo3pkvkNoJGDWli1FyYaGS4OGEfhUl7j3+/MtwqBhBD7vY7FrhL5H6HuMjKWIpm2M" +
                "p9Ov4AkXu8rc2/0GBqHvMJ7Clx4YaHm/qanL22WC0dmZgcAh9DxG4LOznn3Zd2AReg4j8PkSiBODDjxC1" +
                "zECnxN17OtjhAah4xiBz9cwnBx86BDajhH4nKzfQBwrtAiVYwS+QIBw4yRCj9AyRuBzo24DdUwgzJrOzO" +
                "NwicbGi7RU6mtU06pMZxv4AgXBzZMBQrPsMxaZ0dFx8RiMwOdmvQby2EBYaFoNjLqe+Ubxj/GES6GE4e+" +
                "iGQBC0YwhHhlQnAEgVJxQdIcMiGYACEUzhnhkQHEG/h+ia087sht5bQAAAABJRU5ErkJggg==\" width=\"150px\" height=\"150px\" />\n" +
                "        </div>\n" +
                "    </div>";

        return html;
    }

    public static String getMiddleHtml(Kasticket kasticket){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

        String middle = "<hr class=\"first\" />" +
                "<div class=\"middle\">\n" +
                "        <div class=\"factuur_aan one_half first\">\n" +
                "            <h2 class=\"title\">Factuur</h2>\n" +
                "            <h3>Aan</h3>" +
                "            <p>" + kasticket.getKlant().toString() + "</p>\n";

        if (!kasticket.getKlant().getBtw_nummer().equals("")){
            middle += "            <p>BTW BE" + kasticket.getKlant().getBtw_nummer() + "</p>\n";
        }

        Calendar vervalDatum = Calendar.getInstance();
        vervalDatum.setTime(kasticket.getDatum());
        vervalDatum.add(Calendar.DATE, 14);

        middle += "            <p>" + kasticket.getKlant().getAdres() + "</p>\n" +
                "            <p>" + kasticket.getKlant().getPostcode() + " " + kasticket.getKlant().getWoonplaats() + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"factuur_info one_half\">\n" +
                "            <h2 class=\"title\"></h2>\n" +
                "            <h3 class=\"one_half first title\">Datum</h3>\n" +
                "            <p class=\"one_half title\">" + simpleDateFormat.format(kasticket.getDatum()) + "</p>\n" +
                "            <h3 class=\"one_half first title\">Vervaldatum</h3>\n" +
                "            <p class=\"one_half title\">" + simpleDateFormat.format(vervalDatum.getTime()) + "</p>" +
                "            <h3 class=\"one_half first title\">Nummer</h3>\n" +
                "            <p class=\"one_half title\">K" + String.format("%06d", kasticket.getId()) + "</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"artikels first\">\n" +
                "            <table style=\"width: 100%;text-align:left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                    <th style=\"width: 55%\">Omschrijving</th>\n" +
                "                    <th style=\"width: 15%\">Aantal</th>\n" +
                "                    <th style=\"width: 15%\">Prijs</th>\n" +
                "                    <th style=\"width: 15%\">Subtotaal</th>\n" +
                "                </tr>\n";

        double totaal = 0f;
        for (KasticketArtikel kasticketArtikel : kasticket.getKasticketArtikels()){
            double prijs = kasticketArtikel.getHuidige_prijs();
            totaal += prijs * kasticketArtikel.getAantal();

            middle += "                <tr>\n" +
                    "                    <td style=\"width: 55%\">" + (kasticketArtikel.getOmschrijving() == null ? kasticketArtikel.getArtikel().getOmschrijving() : kasticketArtikel.getOmschrijving()) + "</td>\n" +
                    "                    <td style=\"width: 15%\">" + kasticketArtikel.getAantal() + kasticketArtikel.getArtikel().getEenheid().getVerkort() + "</td>\n" +
                    "                    <td style=\"width: 15%\">" + String.format("€ %.2f", prijs) + "</td>\n" +
                    "                    <td style=\"width: 15%\">" + String.format("€ %.2f", prijs * kasticketArtikel.getAantal()) + "</td>\n" +
                    "                </tr>";
        }

        middle += "         </table>\n" +
                "        </div>\n" +
                "        <div class=\"prijzen\">\n" +
                "            <h3 class=\"three_fourth first title\">Totaal excl. BTW:</h3>\n" +
                "            <p class=\"one_fourth title\">" + String.format("€ %.2f", totaal) + "</p>\n" +
                "            <h3 class=\"three_fourth first title\">Btw (0%):</h3>\n" +
                "            <p class=\"one_fourth title\">€ 0,00</p>\n" +
                "            <p class=\"first\">Vrijstellingsregeling btw voor kleine ondernemingen</p>\n" +
                "            <h2 class=\"three_fourth first title\">Totaal:</h2>\n" +
                "            <h2 class=\"one_fourth title\">" + String.format("€ %.2f", totaal) + "</h2>\n" +
                "        </div>\n" +
                "        <div class=\"betaalinfo first\">\n" +
                "            <p>\n" +
                "                Wij verzoeken u het verschuldigde bedrag ten laatste voor de vervaldatum over te maken onder vermelding van de referentie K" + String.format("%06d", kasticket.getId()) + ".\n" +
                "            </p>\n" +
                "            <p>\n" +
                "                Gelieve het bedrag te storten op de volgende rekening BE06 7360 5628 4922 met BIC KREDBEBB.\n" +
                "            </p>\n" +
                "        </div>" +
                "    </div>";

        return middle;
    }

    public static String getEndHtml() {
        return "\n" +
                "    <div class=\"footer\">\n" +
                "        <p class=\"one_third first\">Vermolen-IT</p>\n" +
                "        <p class=\"one_third\">vermolen-it@hotmail.com</p>\n" +
                "        <p class=\"one_third\">0467/03.18.52</p>\n" +
                "    </div></body>\n" +
                "</html>\n";
    }

    public static void printText(Context context, WebView webView, String title){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PrintManager printer = (PrintManager) context.getSystemService(PRINT_SERVICE);

            PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter(title);

            PrintAttributes.Builder attr = new PrintAttributes.Builder();
            attr.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            attr.setColorMode(PrintAttributes.COLOR_MODE_COLOR);

            printer.getPrintJobs().add(printer.print(title, adapter, attr.build()));
        }else{
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
