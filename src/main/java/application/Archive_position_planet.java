package application;

import dbController.dbQuery;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Archive_position_planet {

    dbQuery dbQuery = new dbQuery();

    private int id;
    private Timestamp data;
    private String declination;
    private int planetId_FK;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getDeclination() {
        return declination;
    }

    public void setDeclination(String declination) {
        this.declination = declination;
    }

    public int getPlanetId_FK() {
        return planetId_FK;
    }

    public void setPlanetId_FK(int planetId_FK) {
        this.planetId_FK = planetId_FK;
    }

    public String getName()throws SQLException { return dbQuery.getNamePl(planetId_FK); }

    public String getType()throws SQLException{return dbQuery.getTypePl(planetId_FK);}

    public String getTemperature() throws SQLException { return dbQuery.getTemperaturePl(planetId_FK); }

    public String getUrl() throws SQLException{ return dbQuery.getUrlPl(planetId_FK); }

    public String getDistanceFromEarth() throws SQLException { return dbQuery.getDistancePl(planetId_FK); }

    public String getMass()throws SQLException { return dbQuery.getMassPl(planetId_FK); }

    public String getCenterStar()throws SQLException { return dbQuery.getCenterStarPl(planetId_FK); }

    public int getIdPlanets(){return planetId_FK;}

    public Archive_position_planet(int id, Timestamp data, String declination, int planetId_FK){

        this.id = id;
        this.data = data;
        this.declination = declination;
        this.planetId_FK = planetId_FK;
    }
}
