CREATE VIEW `provider_view` AS
  SELECT
    `providers`.`NUMBER`         AS `NUMBER`,
    `providers`.`NAME`           AS `NAME`,
    `locations`.`CITY`           AS `CITY`,
    `locations`.`STATE`          AS `STATE`,
    `locations`.`STREET_ADDRESS` AS `STREET_ADDRESS`,
    `locations`.`ZIPCODE`        AS `ZIPCODE`
  FROM `providers`
    LEFT JOIN `entity_lookup`
      ON `providers`.`NUMBER` = `entity_lookup`.`PROVIDER_NUMBER`
    LEFT JOIN `locations_lookup`
      ON entity_lookup.ID = locations_lookup.ENTITY_ID
    LEFT JOIN `locations`
      ON locations_lookup.LOCATION_ID = locations.ID
