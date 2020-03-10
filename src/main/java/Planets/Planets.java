package Planets;

import java.io.Serializable;

public class Planets implements Serializable {

    private int idPlanets;
    private String name;
    private String type;
    private String temperature;
    private String url;
    private String distancefromEarth;
    private String mass;
    private String centerStar;


    public int getIdPlanets() {
        return idPlanets;
    }

    public void setIdPlanets(int idPlanets) { this.idPlanets = idPlanets; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDistancefromEarth() {
        return distancefromEarth;
    }

    public void setDistancefromEarth(String distancefromEarth) { this.distancefromEarth = distancefromEarth; }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getCenterStar() {
        return centerStar;
    }

    public void setCenterStar(String centerStar) {
        this.centerStar = centerStar;
    }

    public Planets(
            int id,
            String name,
            String type,
            String temperature,
            String url,
            String distancefromEarth,
            String mass,
            String centerStar
    ){
        this.idPlanets = id;
        this.name = name;
        this.type = type;
        this.temperature = temperature;
        this.url = url;
        this.distancefromEarth = distancefromEarth;
        this.mass = mass;
        this.centerStar = centerStar;

    }


}
