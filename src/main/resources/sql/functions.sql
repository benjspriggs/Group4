-- BEGIN CREATE SECTION
DELIMITER $$
CREATE PROCEDURE `create_member` (
  IN number INT(11),
  IN is_suspended TINYINT(1),
  IN `name` VARCHAR(140),
  IN `street_address` VARCHAR(50),
  IN `city` VARCHAR(50),
  IN `state` VARCHAR(2),
  IN `zipcode` VARCHAR(32)
)
  BEGIN
    insert into members (NUMBER, IS_SUSPENDED) VALUES (`number`, `is_suspended`);
    insert into member_info (NUMBER, NAME) VALUES (`number`, `name`);
    insert into locations (STREET_ADDRESS, CITY, STATE, ZIPCODE) VALUES
      (`street_address`, `city`, `state`, `zipcode`);
    insert into locations_lookup(MEMBER_NUMBER, location_id) VALUES (`number`,
                                                                     LAST_INSERT_ID());
  END$$

DELIMITER $$
CREATE PROCEDURE `create_provider` (
  IN number INT(11),
  IN `name` VARCHAR(140),
  IN `street_address` VARCHAR(50),
  IN `city` VARCHAR(50),
  IN `state` VARCHAR(2),
  IN `zipcode` VARCHAR(32)
)
  BEGIN
    insert into providers (NUMBER, NAME) VALUES (`number`, `name`);
    insert into locations (STREET_ADDRESS, CITY, STATE, ZIPCODE) VALUES
      (`street_address`, `city`, `state`, `zipcode`);
    insert into locations_lookup(PROVIDER_NUMBER, location_id) VALUES (`number`,
                                                                       LAST_INSERT_ID());
  END$$

DELIMITER $$
CREATE PROCEDURE `create_performed_service` (
  IN member_number INT(11),
  IN provider_number INT(11),
  IN service_code INT(11),
  IN `comments` VARCHAR(140),
  IN `date_service` DATE
)
  BEGIN
    INSERT INTO services_lookup(member_number, provider_number, service_code)
    VALUES (`member_number`, `provider_number`, `service_code`);
    INSERT INTO performed_services (SERVICE_ID, DATE_SERVICE, COMMENTS)
    VALUES (last_insert_id(), `date_service`, `comments`);
  END$$

-- BEGIN UPDATE SECTION
DELIMITER $$
CREATE PROCEDURE `update_member` (
  IN number INT(11),
  IN is_suspended TINYINT(1),
  IN `name` VARCHAR(140),
  IN `street_address` VARCHAR(50),
  IN `city` VARCHAR(50),
  IN `state` VARCHAR(2),
  IN `zipcode` VARCHAR(32)
)
  BEGIN
    UPDATE members SET
      IS_SUSPENDED = `is_suspended`
      WHERE number = `number`;
    UPDATE member_info SET
      NAME = `name`
      WHERE number = `number`;

    UPDATE locations SET
      STREET_ADDRESS = `street_address`,
      CITY = `city`,
      STATE = `state`,
      ZIPCODE = `zipcode`
    WHERE ID =
          (SELECT location_id from locations_lookup
            WHERE MEMBER_NUMBER = `number` LIMIT 1);

  END$$


DELIMITER $$
CREATE PROCEDURE `update_provider` (
  IN number INT(11),
  IN `name` VARCHAR(140),
  IN `street_address` VARCHAR(50),
  IN `city` VARCHAR(50),
  IN `state` VARCHAR(2),
  IN `zipcode` VARCHAR(32)
)
  BEGIN
    UPDATE providers SET
      NAME = `name`
    WHERE number = `number`;

    UPDATE locations SET
      STREET_ADDRESS = `street_address`,
      CITY = `city`,
      STATE = `state`,
      ZIPCODE = `zipcode`
    WHERE ID =
          (SELECT location_id from locations_lookup
          WHERE PROVIDER_NUMBER = `number` LIMIT 1);

  END$$
