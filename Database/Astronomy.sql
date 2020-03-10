Create table Satellities(
id NUMERIC primary key,
name varchar2(80),
intl VARCHAR2(80),
type VARCHAR2(80),
url VARCHAR2(80),
apogee VARCHAR2(80),
perigee VARCHAR2(80),
inclination VARCHAR2(80)
);

Create table Planets(
id NUMERIC primary key,
name varchar2(80),
type VARCHAR2(80),
temperature VARCHAR2(80),
url VARCHAR2(80),
distancefromEarth VARCHAR2(80),
mass VARCHAR2(80),
centerStar VARCHAR2(80)
);

Create table Archive_position_sat(
id Numeric PRIMARY KEY,
data Timestamp,
longitude VARCHAR2(80),
satelitId_FK Numeric REFERENCES Satellities(id)
);

Create table Archive_position_planet(
id Numeric PRIMARY KEY,
data Timestamp,
declination VARCHAR2(80),
planetId_FK Numeric REFERENCES Planets(id)
);




DROP TABLE Archive_position_sat;
Drop table Satellities;

DROP TABLE Archive_position_planet;
Drop table Planets;