DELIMITER $$
CREATE PROCEDURE perform_rand_services()
BEGIN
  DECLARE x INT DEFAULT 0;

  WHILE x < 1000 DO
    -- select a member
    SET @member_number = (SELECT MEMBER_NUMBER from members ORDER BY RAND() LIMIT 1);
    -- select a provider
    SET @provider_number = (SELECT PROVIDER_NUMBER from providers ORDER BY RAND() LIMIT 1);
    -- select a service
    SET @service_code = (SELECT SERVICE_CODE FROM service_info ORDER BY RAND() LIMIT 1);
    -- set a date
    SET @date_service = (SELECT NOW() - INTERVAL FLOOR(RAND() * 14) DAY);
    -- insert it randomly
    INSERT INTO services_lookup(MEMBER_NUMBER, PROVIDER_NUMBER, SERVICE_CODE)
    VALUES (@member_number, @provider_number, @service_code);
    INSERT INTO performed_services(DATE_SERVICE, COMMENTS)
    VALUES (@date_service, 'This was a randomly generated string');
    SET x = x + 1;
  END WHILE;
END $$

