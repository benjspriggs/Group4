CREATE VIEW `provider_view` AS
  SELECT
    `providers`.`NUMBER`         AS `NUMBER`,
    `providers`.`NAME`           AS `NAME`,
    `locations`.`CITY`           AS `CITY`,
    `locations`.`STATE`          AS `STATE`,
    `locations`.`STREET_ADDRESS` AS `STREET_ADDRESS`,
    `locations`.`ZIPCODE`        AS `ZIPCODE`
  FROM `providers`
    JOIN `entity_lookup`
      ON `providers`.`NUMBER` = `entity_lookup`.`PROVIDER_NUMBER`
    JOIN `locations_lookup`
      ON entity_lookup.ID = locations_lookup.ENTITY_ID
    JOIN `locations`
      ON locations_lookup.LOCATION_ID = locations.ID
