# General SQL Translation

This is a limited description of how Interactive Mode statements
are translated into SQL.

Also, for each of these blocks, the general form for each of the
SQL inserts etc will be given, and each are wrapped in a ``BEGIN;`` 
and ``COMMIT;``, so all this stuff is transactional.

As well, each of the inserting arguments is prepended with an `@` symbol.
## `create`
- ### `user`
```sql
INSERT INTO users 
(USERNAME) 
    VALUES (
    @username
);
```
- ### `member`
Because locational arguments are optional, those can be replaced
with ``NULL`` if they are not specified.
```sql
CALL create_member(
  @`number`,
  @is_suspended,
  @`name`,
  @street_address,
  @city,
  @state,
  @zipcode
);
```
- ### `provider`
Similar to [create member](##`create`), locational arguments are optional, 
and can be replaced with ``NULL``.
```sql
CALL create_provider(
  @`number`,
  @`name`,
  @street_address,
  @city,
  @state,
  @zipcode
);
```
- ### `service`
So here's some issues. Here's where 
some conditional setups are going to be needed.
Does the user want to insert a service description,
the general form? Or do they want to say that a particular
service has been performed?

We're going to break that down into required fields.
#### Service Description
Required fields:
- Service code
- Name
- Fee
- Description

The SQL is going to look something like this:
```sql
INSERT INTO service_info 
(service_code, name, fee, description)
VALUES (
    @service_code,
    @`name`,
    @fee,
    @description
);
```

#### Performed Service Event
Required fields:
- Member number
- Provider number
- Service code
- Date of service (as recorded by provider)

Non-required fields:
- Comments (can be ``NULL``-able)

The magic of foreign keys will make sure that each service, 
member, and provider exists before inserting.
```sql
INSERT INTO services_lookup 
(MEMBER_NUMBER, PROVIDER_NUMBER, SERVICE_CODE) 
VALUES (
  @member_number,
  @provider_number,
  @service_code
);
insert into performed_services
(SERVICE_ID, DATE_SERVICE, COMMENTS) 
VALUES (
  LAST_INSERT_ID(),
  @date_service,
  @comments
);
```

## `show`
- ### `user`, `user report`
```sql
SELECT * FROM users WHERE USERNAME=@username;
```
- ### `member`, `member report`
This is another area that may require some fine-tuning of the grammar
that we don't have time to do. For now, select a row from the member view,
and pass in any provided arguments to the `WHERE` clause (member_number is guaranteed to be unique):
```sql
SELECT NUMBER, NAME, STREET_ADDRESS, CITY, STATE, ZIPCODE
 FROM member_view 
 WHERE NUMBER=@member_number 
 LIMIT 1;
```

- ### `provider`
Same as `member`.
```sql
SELECT NUMBER, NAME, STREET_ADDRESS, CITY, STATE, ZIPCODE
 FROM provider_view 
 WHERE NUMBER=@provider_number 
 LIMIT 1;
```
- ### `service`
Another area for clarification. For now, default giving lots of information about the service.
```sql
SELECT service_info.NAME, service_info.DESCRIPTION, service_info.SERVICE_CODE
 FROM service_info 
 WHERE SERVICE_CODE=@service_code;
```
- ### `service report`
Now for the fun bits.
```sql
SELECT service_info.NAME, service_info.SERVICE_CODE,
performed_services.TIMESTAMP, performed_services.DATE_SERVICE, performed_services.COMMENTS, 
members.NUMBER, providers.NUMBER
 FROM services_lookup -- TODO: Make joins more specific
 JOIN service_info using (SERVICE_CODE)
 JOIN performed_services ON services_lookup.ID = performed_services.SERVICE_ID
 JOIN members ON services_lookup.MEMBER_NUMBER = members.NUMBER
 JOIN providers ON services_lookup.PROVIDER_NUMBER = providers.NUMBER
```
## `update`
- ### `user`
Because usernames are guaranteed to be unique, that can be used as an identifier in leiu of the userid.
```sql
UPDATE users
 SET username=@new_username
 WHERE username=@old_username
```
- ### `member`
If all that's being updated is the member status:
```sql
UPDATE members
 SET IS_SUSPENDED=@suspended_status
 WHERE NUMBER=@number
```
If you want to update the member's name:
```sql
UPDATE members
 JOIN member_info USING (NUMBER)
 SET member_info.NAME = @new_name
 -- SET members.is_suspended = @new_status
 WHERE NUMBER = @number
```

If you want to update just the member's address:
```sql
UPDATE locations
 JOIN locations_lookup ON locations.ID = locations_lookup.location_id
 JOIN members ON locations_lookup.MEMBER_NUMBER = members.NUMBER
 SET STREET_ADDRESS=@street_address,
 CITY=@city,
 STATE=@state,
 ZIPCODE=@zipcode
 WHERE members.number = @number AND 
 locations_lookup.location_id = locations.ID
```
- ### `provider`
- ### `service`
## `delete`
- ### `user`
- ### `member`
- ### `provider`
- ### `service`
## `write`
- ### `user`
- ### `member`
- ### `provider`
- ### `service`
