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
insert into performed_services(SERVICE_ID, DATE_SERVICE, COMMENTS) 
VALUES (
  LAST_INSERT_ID(),
  @date_service,
  @comments
);
```

## `show`

## `update`

## `delete`

## `write`