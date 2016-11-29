CREATE TABLE locations
(
  ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  STREET_ADDRESS VARCHAR(50),
  CITY VARCHAR(50),
  STATE VARCHAR(2),
  ZIPCODE VARCHAR(32)
);
CREATE TABLE locations_lookup
(
  ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  MEMBER_NUMBER INT(11),
  PROVIDER_NUMBER INT(11),
  location_id INT(11) NOT NULL
);
CREATE TABLE member_info
(
  NUMBER INT(11) PRIMARY KEY NOT NULL,
  NAME VARCHAR(140) NOT NULL
);
CREATE TABLE members
(
  NUMBER INT(11) PRIMARY KEY NOT NULL,
  IS_SUSPENDED TINYINT(1) DEFAULT '0'
);
CREATE TABLE performed_services
(
  SERVICE_ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  DATE_SERVICE DATE NOT NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  COMMENTS VARCHAR(140)
);
CREATE TABLE providers
(
  NUMBER INT(11) PRIMARY KEY NOT NULL,
  NAME VARCHAR(40) NOT NULL
);
CREATE TABLE service_info
(
  SERVICE_CODE INT(11) PRIMARY KEY NOT NULL,
  NAME VARCHAR(140) NOT NULL,
  FEE DECIMAL(19,4) NOT NULL,
  DESCRIPTION VARCHAR(100)
);
CREATE TABLE services_lookup
(
  ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  MEMBER_NUMBER INT(11) NOT NULL,
  PROVIDER_NUMBER INT(11) NOT NULL,
  SERVICE_CODE INT(11) NOT NULL
);
CREATE TABLE users
(
  ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  USERNAME VARCHAR(40) NOT NULL,
  LAST_LOGIN TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
ALTER TABLE locations_lookup ADD FOREIGN KEY (MEMBER_NUMBER) REFERENCES members (NUMBER) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE locations_lookup ADD FOREIGN KEY (PROVIDER_NUMBER) REFERENCES providers (NUMBER) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE locations_lookup ADD FOREIGN KEY (location_id) REFERENCES locations (ID);
CREATE UNIQUE INDEX ENTITY_LOOKUP_MEMBERS_NUMBER_FK ON locations_lookup (MEMBER_NUMBER);
CREATE UNIQUE INDEX ENTITY_LOOKUP_PROVIDERS_NUMBER_FK ON locations_lookup (PROVIDER_NUMBER);
CREATE INDEX locations_lookup_locations_ID_fk ON locations_lookup (location_id);
ALTER TABLE member_info ADD FOREIGN KEY (NUMBER) REFERENCES members (NUMBER) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE performed_services ADD FOREIGN KEY (SERVICE_ID) REFERENCES services_lookup (ID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE services_lookup ADD FOREIGN KEY (MEMBER_NUMBER) REFERENCES members (NUMBER) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE services_lookup ADD FOREIGN KEY (SERVICE_CODE) REFERENCES service_info (SERVICE_CODE) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX SERVICES_MEMBERS_NUMBER_FK ON services_lookup (MEMBER_NUMBER);
CREATE INDEX SERVICE_CODE_FK ON services_lookup (SERVICE_CODE);