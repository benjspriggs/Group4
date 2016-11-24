CREATE TABLE USERS
(
  ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  USERNAME VARCHAR(40) NOT NULL,
  LAST_LOGIN TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX "users_username_uindex" ON USERS (USERNAME);
CREATE TABLE MEMBERS
(
  NUMBER INT PRIMARY KEY NOT NULL,
  IS_SUSPENDED BOOLEAN DEFAULT false
);
CREATE TABLE MEMBER_INFO
(
  NUMBER INT PRIMARY KEY NOT NULL,
  NAME VARCHAR(140) NOT NULL,
  CONSTRAINT NUMBER FOREIGN KEY (NUMBER) REFERENCES MEMBERS (NUMBER)
);
CREATE TABLE PROVIDERS
(
  NUMBER INT PRIMARY KEY NOT NULL,
  NAME VARCHAR(40) NOT NULL
);
CREATE TABLE SERVICE_INFO
(
  SERVICE_CODE INT PRIMARY KEY NOT NULL,
  NAME VARCHAR(140) NOT NULL,
  FEE DECIMAL(19) NOT NULL,
  DESCRIPTION VARCHAR(100)
);
CREATE TABLE SERVICES_LOOKUP
(
  ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  MEMBER_NUMBER INT NOT NULL,
  PROVIDER_NUMBER INT NOT NULL,
  SERVICE_CODE INT NOT NULL,
  CONSTRAINT SERVICES_MEMBERS_NUMBER_FK FOREIGN KEY (MEMBER_NUMBER) REFERENCES MEMBERS (NUMBER),
  CONSTRAINT SERVICE_CODE_FK FOREIGN KEY (SERVICE_CODE) REFERENCES SERVICE_INFO (SERVICE_CODE)
);
CREATE TABLE PERFORMED_SERVICES
(
  SERVICE_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  DATE_SERVICE DATE DEFAULT CURRENT DATE NOT NULL,
  TIMESTAMP TIMESTAMP DEFAULT current timestamp,
  COMMENTS VARCHAR(140),
  CONSTRAINT PERFORMED_SERVICES_SERVICES_ID_FK FOREIGN KEY (SERVICE_ID) REFERENCES SERVICES_LOOKUP (ID)
);
CREATE TABLE LOCATIONS
(
  ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  STREET_ADDRESS VARCHAR(50),
  CITY VARCHAR(50),
  STATE VARCHAR(2),
  ZIPCODE VARCHAR(32)
);
CREATE TABLE ENTITY_LOOKUP
(
  ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  MEMBER_NUMBER INT,
  PROVIDER_NUMBER INT,
  CONSTRAINT ENTITY_LOOKUP_MEMBERS_NUMBER_FK FOREIGN KEY (MEMBER_NUMBER) REFERENCES MEMBERS (NUMBER),
  CONSTRAINT ENTITY_LOOKUP_PROVIDERS_NUMBER_FK FOREIGN KEY (PROVIDER_NUMBER) REFERENCES PROVIDERS (NUMBER),
  CONSTRAINT PersistentIdentity CHECK
  (
  (CASE WHEN MEMBER_NUMBER IS NOT NULL AND PROVIDER_NUMBER IS NULL THEN 1 ELSE 0 END
  + CASE WHEN MEMBER_NUMBER IS NULL AND PROVIDER_NUMBER IS NOT NULL THEN 1 ELSE 0 END) = 1)
);
CREATE TABLE LOCATIONS_LOOKUP
(
  ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
  LOCATION_ID INT NOT NULL,
  ENTITY_ID INT NOT NULL,
  CONSTRAINT LOCATIONS_LOOKUP_LOCATIONS_ID_FK FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS (ID),
  CONSTRAINT LOCATIONS_LOOKUP_ENTITY_LOOKUP_ID_FK FOREIGN KEY (ENTITY_ID) REFERENCES ENTITY_LOOKUP (ID)
);
