package dbController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import Planets.Planets;
import Satellities.Satellities;
import application.Archive_position_planet;
import application.Archive_position_sat;


public class dbQuery {
    Connection connection;

    //SATELLITIES///////////////////////////////////////

    public ArrayList<String> GetSatName() throws SQLException {
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
ArrayList<String> stringArrayList = new ArrayList<>();

        String sql = "SELECT Name FROM Satellities";

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

       while(rs.next()){
           stringArrayList.add(rs.getString("Name"));
       }

        rs.close();
        ps.close();

        return stringArrayList;
    }

    public ArrayList<Integer> GetSatId(String name) throws SQLException {
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        String sql = "SELECT id FROM Satellities WHERE name = ? ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);
        ps.setString(1, name);

        rs = ps.executeQuery();

        while (rs.next()){
            integerArrayList.add(rs.getInt("id"));
        }

        rs.close();
        ps.close();

        return integerArrayList;
    }

    public void DeleteSat(int zmId) throws SQLException{
        this.connection = DbConnection.getConnection();

        String sql = "DELETE FROM Satellities WHERE Id = ?";

            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1,zmId);
            ps.executeQuery();

        ps.close();
    }

    public int getNewestIDSat()throws SQLException{

        String sql = "SELECT * FROM Satellities";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id + 1;
    }

    public int getSatIdbyName(String name)throws SQLException{
        String sql = "SELECT * FROM Satellities WHERE name = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setString(1,name);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id;
    }

    public ArrayList<Integer> getAllSatIdbyName(String name)throws SQLException{
        String sql = "SELECT * FROM Satellities WHERE name = ? ORDER BY id ASC";

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setString(1,name);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
           integerArrayList.add(rs.getInt("id"));
        }

        rs.close();
        ps.close();

        return integerArrayList;
    }

    public boolean AddSatellities(Satellities satellities)throws SQLException{

        String sql = "INSERT INTO Satellities(id, name, intl, type, url, apogee, perigee, inclination) VALUES(?,?,?,?,?,?,?,?)";


        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1,getNewestIDSat());
        ps.setString(2,satellities.getName());
        ps.setString(3,satellities.getIntlDesignator());
        ps.setString(4,satellities.getType());
        ps.setString(5,satellities.getUrl());
        ps.setString(6,satellities.getApogee());
        ps.setString(7,satellities.getPerigee());
        ps.setString(8,satellities.getInclination());
        ps.execute();

        ps.close();

        return true;
    }

    public boolean editSatellities(Satellities satellities)throws SQLException{

        String sql = "UPDATE Satellities SET name = ?, intl = ?, type = ?, url = ?, apogee = ?, perigee = ?, inclination = ? WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setString(1, satellities.getName());
        ps.setString(2, satellities.getIntlDesignator());
        ps.setString(3, satellities.getType());
        ps.setString(4, satellities.getUrl());
        ps.setString(5, satellities.getApogee());
        ps.setString(6, satellities.getPerigee());
        ps.setString(7, satellities.getInclination());
        ps.setInt(8, satellities.getIdSat());
        ps.execute();

        ps.close();

        return true;
    }

    //PLANETS//////////////////////////////////////////////////////////

    public ArrayList<String> GetPlanetName() throws SQLException {
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<String> stringArrayList = new ArrayList<>();

        String sql = "SELECT name FROM Planets";

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while(rs.next()){
            stringArrayList.add(rs.getString("name"));
        }

        rs.close();
        ps.close();

        return stringArrayList;
    }

    public int getNewestIDPlan()throws SQLException{

        String sql = "SELECT * FROM Planets";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id + 1;
    }

    public ArrayList<Integer> GetPlanetId(String name) throws SQLException {
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        String sql = "SELECT id FROM Planets WHERE name = ? ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);
        ps.setString(1, name);

        rs = ps.executeQuery();


        while (rs.next()){
            integerArrayList.add(rs.getInt("id"));
        }


        rs.close();
        ps.close();

        return integerArrayList;
    }

    public int getPlanetIdbyName(String name)throws SQLException{
        String sql = "SELECT * FROM Planets WHERE name = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setString(1,name);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id;
    }

    public void DeletePlanet(int zmId) throws SQLException{
        this.connection = DbConnection.getConnection();

        String sql = "DELETE FROM Planets WHERE Id = ?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1,zmId);
        ps.executeQuery();

        ps.close();
    }

    public boolean AddPlanets(Planets planets)throws SQLException{

        String sql = "INSERT INTO Planets(id, name, type, temperature, url, distancefromEarth, mass, centerStar) VALUES(?,?,?,?,?,?,?,?)";


        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1,getNewestIDPlan());
        ps.setString(2,planets.getName());
        ps.setString(3,planets.getType());
        ps.setString(4,planets.getTemperature());
        ps.setString(5,planets.getUrl());
        ps.setString(6,planets.getDistancefromEarth());
        ps.setString(7,planets.getMass());
        ps.setString(8,planets.getCenterStar());
        ps.execute();

        ps.close();

        return true;
    }

    public boolean editPlanets(Planets planets)throws SQLException{

        String sql = "UPDATE Planets SET name = ?, type = ?, temperature = ?, url = ?, distancefromEarth = ?, mass = ?, centerStar = ? WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setString(1, planets.getName());
        ps.setString(2, planets.getType());
        ps.setString(3, planets.getTemperature());
        ps.setString(4, planets.getUrl());
        ps.setString(5, planets.getDistancefromEarth());
        ps.setString(6, planets.getMass());
        ps.setString(7, planets.getCenterStar());
        ps.setInt(8, planets.getIdPlanets());
        ps.execute();

        ps.close();

        return true;
    }

    public ArrayList<Integer> getAllPlanetIdbyName(String name)throws SQLException{
        String sql = "SELECT * FROM Planets WHERE name = ? ORDER BY id ASC";

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setString(1,name);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            integerArrayList.add(rs.getInt("id"));
        }

        rs.close();
        ps.close();

        return integerArrayList;
    }

    //Archive_posiion_sat/////////////////////////////////////////////

    public  ArrayList<Archive_position_sat> GetAllArchDate()throws SQLException{
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_sat> archive_position_sats_array  = new ArrayList<>();

        String sql = "SELECT * FROM Archive_position_sat ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            archive_position_sats_array.add(
                    new Archive_position_sat(rs.getInt("id"),
                            rs.getTimestamp("data"),
                            rs.getString("longitude"),
                            rs.getInt("satelitId_FK")
                            ));
        }

        rs.close();
        ps.close();

        return archive_position_sats_array;

    }

    public  ArrayList<Archive_position_sat> GetUniqArchDate()throws SQLException{
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_sat> archive_position_sats_array  = new ArrayList<>();

        String sql = "SELECT Satellities.name, Archive_position_sat.id, Archive_position_sat.data, Archive_position_sat.longitude, Satellities.id\n" +
                "FROM Archive_position_sat\n" +
                "JOIN Satellities ON Satellities.id = Archive_position_sat.SATELITID_FK";

        ArrayList<String> names = new ArrayList<>();

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            if(!names.contains(rs.getString(1))){
                archive_position_sats_array.add(
                        new Archive_position_sat(rs.getInt(2),
                                rs.getTimestamp(3),
                                rs.getString(4),
                                rs.getInt(5)

                        ));
                names.add(rs.getString(1));
            }
        }

        rs.close();
        ps.close();

        return archive_position_sats_array;

    }

    public ArrayList<Archive_position_sat> GetArchWhereId(int id) throws SQLException {

        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_sat> archive_position_satsArrayList = new ArrayList<>();

        String sql = "SELECT * FROM Archive_position_sat WHERE satelitId_FK = ? ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        rs = ps.executeQuery();

        while (rs.next()) {
            archive_position_satsArrayList.add(new Archive_position_sat(rs.getInt("id"),
                    rs.getTimestamp("data"),
                    rs.getString("longitude"),
                    rs.getInt("satelitId_FK")
            ));

        }
        rs.close();
        ps.close();

        return archive_position_satsArrayList;
    }

    public int getIdArchPos(int idzm)throws SQLException{

        String sql = "SELECT * FROM Archive_position_sat WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setInt(1,idzm);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id;
    }

    public void deleteArchPos(int idzm)throws SQLException{
        this.connection = DbConnection.getConnection();

        String sql = "DELETE FROM Archive_position_sat WHERE satelitId_FK = ?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1,idzm);
        ps.executeQuery();

        ps.close();
    }


    public int getNewestIdArch() throws SQLException{

        String sql = "SELECT * FROM Archive_position_sat";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id + 1;
    }

    public boolean AddArchPos(Archive_position_sat archive_position_sat, int id)throws SQLException{

        String sql = "INSERT INTO Archive_position_sat(id, data, longitude, satelitId_FK) VALUES(?,?,?,?)";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1,getNewestIdArch());
        ps.setTimestamp(2,archive_position_sat.getData());
        ps.setString(3,archive_position_sat.getLongitude());
        ps.setInt(4,id);

        ps.execute();

        ps.close();

        return true;
    }

    public boolean editArchPos(Archive_position_sat archive_position_sat)throws SQLException{

        String sql = "UPDATE Archive_position_sat SET data = ?, longitude = ?  WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setTimestamp(1, archive_position_sat.getData());
        ps.setString(2, archive_position_sat.getLongitude());
        ps.setInt(3, archive_position_sat.getId());

        ps.execute();

        ps.close();

        return true;
    }


    /////GETERY Archi_sat//////////////////////

        public String getName(int zmId)throws SQLException{

        String name = null;

            String sql = "SELECT name FROM Satellities WHERE id = ?";

            PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
            ps.setInt(1, zmId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                name = rs.getString(1);
            }

            ps.close();


        return name;
        }

        public String getType(int zmid)throws SQLException{
            String type = null;

            String sql = "SELECT type FROM Satellities WHERE id = ?";

            PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
            ps.setInt(1, zmid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                type = rs.getString(1);
            }

            ps.close();


            return type;
        }

    public String getIntlDesignator(int zmid)throws SQLException{
        String intl = null;

        String sql = "SELECT intl FROM Satellities WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            intl = rs.getString(1);
        }

        ps.close();

        return intl;
    }

    public String getUrl(int zmid)throws SQLException{
        String url = null;

        String sql = "SELECT url FROM Satellities WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            url = rs.getString(1);
        }

        ps.close();

        return url;
    }

    public String getApogee(int zmid)throws SQLException{
        String apo = null;

        String sql = "SELECT apogee FROM Satellities WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            apo = rs.getString(1);
        }

        ps.close();

        return apo;
    }

    public String getPerigee(int zmid)throws SQLException{
        String per = null;

        String sql = "SELECT perigee FROM Satellities WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            per = rs.getString(1);
        }

        ps.close();

        return per;
    }

    public String getInclination(int zmid)throws SQLException{
        String inc = null;

        String sql = "SELECT inclination FROM Satellities WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            inc = rs.getString(1);
        }

        ps.close();

        return inc;
    }

    //////Arci_planet///////////////////////////////////////

    public ArrayList<Archive_position_planet> GetArchPlanetWhereId(int id) throws SQLException {

        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_planet> archive_position_planetsArrayList = new ArrayList<>();

        String sql = "SELECT * FROM Archive_position_planet WHERE planetId_FK = ? ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        rs = ps.executeQuery();

        while (rs.next()) {
            archive_position_planetsArrayList.add(new Archive_position_planet(rs.getInt("id"),
                    rs.getTimestamp("data"),
                    rs.getString("declination"),
                    rs.getInt("planetId_FK")
            ));

        }
        rs.close();
        ps.close();

        return archive_position_planetsArrayList;
    }

    public  ArrayList<Archive_position_planet> GetAllArchDatePlanet()throws SQLException{
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_planet> archive_position_planets_array  = new ArrayList<>();

        String sql = "SELECT * FROM Archive_position_planet ORDER BY id ASC";

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            archive_position_planets_array.add(
                    new Archive_position_planet(rs.getInt("id"),
                            rs.getTimestamp("data"),
                            rs.getString("declination"),
                            rs.getInt("planetId_FK")
                    ));
        }


        rs.close();
        ps.close();

        return archive_position_planets_array;

    }

    public  ArrayList<Archive_position_planet> GetUniqueArchDatePlanet()throws SQLException{
        this.connection = DbConnection.getConnection();

        PreparedStatement ps;
        ResultSet rs;

        if (connection == null) {
            return null;
        }
        ArrayList<Archive_position_planet> archive_position_planets_array  = new ArrayList<>();

        String sql = "SELECT Planets.name, Archive_position_planet.id, Archive_position_planet.data, Archive_position_planet.declination, Planets.id\n" +
                "FROM Archive_position_planet\n" +
                "JOIN Planets ON Planets.id = Archive_position_planet.PLANETID_FK";

        ArrayList<String> names = new ArrayList<>();

        ps = this.connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            if (!names.contains(rs.getString(1))) {
                archive_position_planets_array.add(
                        new Archive_position_planet(rs.getInt(2),
                                rs.getTimestamp(3),
                                rs.getString(4),
                                rs.getInt(5)

                        ));
                names.add(rs.getString(1));
            }
        }


        rs.close();
        ps.close();

        return archive_position_planets_array;

    }

    public void deleteArchPlanet(int idzm)throws SQLException{
        this.connection = DbConnection.getConnection();

        String sql = "DELETE FROM Archive_position_planet WHERE planetId_FK = ?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1,idzm);
        ps.executeQuery();

        ps.close();
    }

    public boolean AddArchPlanet(Archive_position_planet archive_position_planet, int id)throws SQLException{

        String sql = "INSERT INTO Archive_position_planet(id, data, declination, planetId_FK) VALUES(?,?,?,?)";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1,getNewestIDPlanArch());
        ps.setTimestamp(2,archive_position_planet.getData());
        ps.setString(3,archive_position_planet.getDeclination());
        ps.setInt(4,id);

        ps.execute();

        ps.close();

        return true;
    }

    public boolean editArchPlanet(Archive_position_planet archive_position_planet)throws SQLException{

        String sql = "UPDATE Archive_position_planet SET data = ?, declination = ?  WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setTimestamp(1, archive_position_planet.getData());
        ps.setString(2, archive_position_planet.getDeclination());
        ps.setInt(3, archive_position_planet.getId());

        ps.execute();

        ps.close();

        return true;
    }

    public int getIdArchPlanet(int idzm)throws SQLException{

        String sql = "SELECT * FROM Archive_position_planet WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);
        ps.setInt(1,idzm);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id;
    }

    public int getNewestIDPlanArch()throws SQLException{

        String sql = "SELECT * FROM Archive_position_planet";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection()).prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int id = 0;

        while (rs.next()) {
            if (id < rs.getInt(1))
                id = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return id + 1;
    }

    /////GETERY Archi_planet//////////////////////

    public String getNamePl(int zmId)throws SQLException{

        String name = null;

        String sql = "SELECT name FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            name = rs.getString(1);
        }

        ps.close();


        return name;
    }

    public String getTypePl(int zmid)throws SQLException{
        String type = null;

        String sql = "SELECT type FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            type = rs.getString(1);
        }

        ps.close();


        return type;
    }

    public String getTemperaturePl(int zmid)throws SQLException{
        String temp = null;

        String sql = "SELECT temperature FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            temp = rs.getString(1);
        }

        ps.close();

        return temp;
    }

    public String getUrlPl(int zmid)throws SQLException{
        String url = null;

        String sql = "SELECT url FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            url = rs.getString(1);
        }

        ps.close();

        return url;
    }

    public String getDistancePl(int zmid)throws SQLException{
        String dist = null;

        String sql = "SELECT distancefromEarth FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            dist = rs.getString(1);
        }

        ps.close();

        return dist;
    }

    public String getMassPl(int zmid)throws SQLException{
        String mass = null;

        String sql = "SELECT mass FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            mass = rs.getString(1);
        }

        ps.close();

        return mass;
    }

    public String getCenterStarPl(int zmid)throws SQLException{
        String cent = null;

        String sql = "SELECT centerStar FROM Planets WHERE id = ?";

        PreparedStatement ps = Objects.requireNonNull(DbConnection.getConnection().prepareStatement(sql));
        ps.setInt(1, zmid);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            cent = rs.getString(1);
        }

        ps.close();

        return cent;
    }

}



