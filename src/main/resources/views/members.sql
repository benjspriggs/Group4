CREATE VIEW `member_view` AS
  SELECT
    `members`.`NUMBER`           AS `NUMBER`,
    `members`.`IS_SUSPENDED`     AS `IS_SUSPENDED`,
    `member_info`.`NAME`         AS `NAME`,
    `locations`.`CITY`           AS `CITY`,
    `locations`.`STATE`          AS `STATE`,
    `locations`.`STREET_ADDRESS` AS `STREET_ADDRESS`,
    `locations`.`ZIPCODE`        AS `ZIPCODE`
  FROM `members`
    JOIN `member_info`
      ON `members`.`NUMBER` = `member_info`.`NUMBER`
    JOIN `entity_lookup`
      ON `members`.`NUMBER` = `entity_lookup`.`MEMBER_NUMBER`
    JOIN `locations_lookup`
      ON `locations_lookup`.`ENTITY_ID` = `entity_lookup`.`ID`
    JOIN `locations`
      ON `locations`.`ID` = `locations_lookup`.`LOCATION_ID`