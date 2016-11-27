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