CREATE EXTENSION postgis;

CREATE TABLE states_geojson_polygon
(
    name VARCHAR(255),
    geom GEOMETRY
);

CREATE TABLE no_fly_geometry
(
    zone_name    VARCHAR(255),
    geometry     GEOMETRY,
    max_altitude FLOAT,
    min_altitude FLOAT
);

CREATE TABLE rectangle_no_fly_zone
(
    zone_name        VARCHAR(255),
    west_long_degree FLOAT,
    east_long_degree FLOAT,
    south_lat_degree FLOAT,
    north_lat_degree FLOAT,
    rotation_degree  FLOAT,
    max_altitude     FLOAT,
    min_altitude     FLOAT
);

CREATE TABLE polygon_no_fly_zone
(
    zone_name         VARCHAR(255),
    vertex_one_long   FLOAT,
    vertex_one_lat    FLOAT,
    vertex_two_long   FLOAT,
    vertex_two_lat    FLOAT,
    vertex_three_long FLOAT,
    vertex_three_lat  FLOAT,
    vertex_four_long  FLOAT,
    vertex_four_lat   FLOAT,
    max_altitude      FLOAT,
    min_altitude      FLOAT
);

CREATE TABLE ellipsoid_no_fly_zone
(
    zone_name  VARCHAR(255),
    longitude  FLOAT,
    latitude   FLOAT,
    altitude   FLOAT,
    long_radius FLOAT,
    latitude_radius  FLOAT,
    altitude_radius  FLOAT
);

CREATE TABLE us_military_geojson
(
    geometry   GEOMETRY,
    installation VARCHAR(255)
);
