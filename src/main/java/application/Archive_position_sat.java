package application;

import dbController.dbQuery;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Archive_position_sat {

    private dbQuery dbQuery = new dbQuery();

    private int id;
    private Timestamp data;
    private String longitude;
    private int satelitId_FK;


    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getSatelitId_FK() {
        return satelitId_FK;
    }

    public void setSatelitId_FK(int satelitId_FK) {
        this.satelitId_FK = satelitId_FK;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getName()throws SQLException { return dbQuery.getName(satelitId_FK); }

    public String getType()throws SQLException{return dbQuery.getType(satelitId_FK);}

    public String getApogee() throws SQLException{ return dbQuery.getApogee(satelitId_FK); }

    public String getPerigee()throws SQLException { return dbQuery.getPerigee(satelitId_FK); }

    public String getInclination()throws SQLException { return dbQuery.getInclination(satelitId_FK); }

    public String getUrl() throws SQLException{ return dbQuery.getUrl(satelitId_FK); }

    public String getIntlDesignator()throws SQLException { return dbQuery.getIntlDesignator(satelitId_FK); }

    public int getIdSat(){return satelitId_FK;}

    public Archive_position_sat(int id, Timestamp data, String longitude, int satelitId_FK){

        this.id = id;
        this.data = data;
        this.longitude = longitude;
        this.satelitId_FK = satelitId_FK;
    }
}
