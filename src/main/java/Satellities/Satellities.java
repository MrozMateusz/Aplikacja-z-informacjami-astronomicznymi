package Satellities;

import java.io.Serializable;

public class Satellities implements Serializable {

    private int idSat;
    private String name;
    private String type;
    private String intlDesignator;
    private String apogee;
    private String perigee;
    private String inclination;
    private String url;


    public int getIdSat() {
        return idSat;
    }

    public void setIdSat(int idSat) {
        this.idSat = idSat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public String getApogee() {
        return apogee;
    }

    public void setApogee(String apogee) {
        apogee = apogee;
    }

    public String getPerigee() {
        return perigee;
    }

    public void setPerigee(String perigee) {
        perigee = perigee;
    }

    public String getInclination() {
        return inclination;
    }

    public void setInclination(String inclination) {
        inclination = inclination;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntlDesignator() { return intlDesignator; }

    public void setIntlDesignator(String intlDesignator) { this.intlDesignator = intlDesignator; }

    public Satellities(int id, String name, String intlDesignator, String type, String url, String apogee, String perigee, String inclination){
        this.idSat = id;
        this.name = name;
        this.type = type;
        this.intlDesignator = intlDesignator;
        this.url = url;
        this.apogee = apogee;
        this.perigee = perigee;
        this.inclination = inclination;

    }
}
