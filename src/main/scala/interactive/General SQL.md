# General SQL Translation

This is a limited description of how Interactive Mode statements
are translated into SQL.

Also, for each of these blocks, the general form for each of the
SQL inserts etc will be given, and each are wrapped in a ``BEGIN;`` 
and ``COMMIT;``, so all this stuff is transactional.
## `create`
- ### `user`
```sql
INSERT INTO app.users (username) 
    VALUES ('username1'), ('username2');
-- The rest of the users that are to be created
```
- ### `member`
```sql
INSERT INTO app.members (number, is_suspended)
    VALUES (1, false), (2, true);
```

And if there's additional info:
```sql
INSERT INTO app.member_info (member_number, 
            `name`, street_address,
            city, state, zip_code)
  VALUES (...);
--- The rest of the members that are to be created
```
- ### `provider`
```sql
INSERT INTO providers (provider_number, name)
    VALUES (...);
INSERT INTO provider_info (provider_number,
            city, state, zip_code)
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
INSERT INTO service_info (service_code, `name`, fee, description)
    VALUES (...);
--- The rest of the service descriptions to insert
```

#### Service Performance
Required fields:
- Member number
- Provider number
- Service code
- Date of service (recorded by provider)

Non-required fields:
- Comments

The SQL is going to be a little more hairy for this one.
```sql
WITH to_ins (member_number, provider_number,
             service_code, date_service, is_suspended)
AS (
VALUES 
--- all of the members to insert
)
INSERT INTO members (member_number, is_suspended)
SELECT
    to_ins.member_number, member_info.number
FROM
    member_info
    JOIN to_ins on to_ins.member_number = member_info.number
```
