package Net;

import java.io.*;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
public class NetController{

    public String ControllerNetSatelPerigee(String url) throws IOException {

        Document d;
        String b = "";
        String s = "";

        int i = 0, j =0;
            try {
                if(isUrlValid(url)){
                d = Jsoup.connect(url).userAgent("mozilla/17.0").get();
                Elements e = d.select("#satinfo");

                    for (Element tab : e) {

                        s = tab.getAllElements().toString();


                         i = s.lastIndexOf("<b>Perigee</b>:");
                         j = s.indexOf("km");

                        if (s.contains("Perigee")) {
                            break;
                        } else {
                            continue;
                        }

                    }

                b = s.substring(i+16, j+2);
                    }
            }catch (MalformedURLException o){
                o.printStackTrace();
            }
        return b;
    }

    public String ControllerNetSatelApogee(String url) throws IOException {

        Document d;
        String b = "";
        String s = null;
        int i = 0, j =0;
        try {
            if(isUrlValid(url)){
            d = Jsoup.connect(url).userAgent("mozilla/17.0").get();
            Elements e = d.select("#satinfo");

            for (Element tab : e) {

                s = tab.getAllElements().toString();


                i = s.lastIndexOf("<b>Apogee</b>:");
                j = s.indexOf("km",i);


                if (s.contains("Apogee")) {
                    break;
                } else {
                    continue;
                }

            }

                b = s.substring(i+15, j+2);

            }

        }catch (MalformedURLException o){
            o.printStackTrace();
        }
        return b;
    }

    public String ControllerNetSatelInc(String url) throws IOException {

        Document d;
        String b = "";
        String s = null;
        int i = 0, j =0;
        try {
            if(isUrlValid(url)){
            d = Jsoup.connect(url).userAgent("mozilla/17.0").get();
            Elements e = d.select("#satinfo");


            for (Element tab : e) {

                s = tab.getAllElements().toString();


                i = s.lastIndexOf("<b>Inclination</b>:");
                j = s.indexOf("°");

                if (s.contains("Inclination")) {
                    break;
                } else {
                    continue;
                }
            }

                b = s.substring(i+20, j+1);

            }

        }catch (MalformedURLException o){
            o.printStackTrace();
        }
        return b;
    }

    public String ControllerNetPlanetDec(String url) throws IOException {

        Document d;
        String b = "";
        String s = null;

        try {
            if(isUrlValid(url)){
            d = Jsoup.connect(url).userAgent("mozilla/17.0").get();
            Elements e = d.select("div.keyinfobox");


            for (Element tab : e) {

                s = tab.getAllElements().toString();

                    if(s.contains("Declination")){
                        break;
                    }else{
                        continue;
                    }
            }

            b = s.substring(63,75);

               }
        } catch (MalformedURLException o) {
            o.printStackTrace();
        }
        return b;
    }

    public String ControllerNetPlanetDist(String url) throws IOException {

        Document d;
        String s = null;
        String b = "";

            try {
                if(isUrlValid(url)){
                d = Jsoup.connect(url).userAgent("mozilla/17.0").get();
                Elements e = d.select("div.keyinfobox");

                for (Element tab : e) {

                    s = tab.getAllElements().toString();

                    if (s.contains("Distance Kilometers")) {
                        break;
                    } else {
                        continue;
                    }
                }

                b = s.substring(71, 82);

                   }
            } catch (MalformedURLException o) {
                    o.printStackTrace();
            }

        return b;
    }

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}



