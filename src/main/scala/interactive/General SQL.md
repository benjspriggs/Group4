# General SQL Translation

This is a limited description of how Interactive Mode statements
are translated into SQL.

## `create`
- ### `user`
```SQL
BEGIN;
INSERT INTO users (id, username, last_login) 
    VALUES (...);
-- The rest of the users that are to be created
COMMIT;
```
- ### `member`
```sql
BEGIN;
INSERT INTO members (member_number, status)
    VALUES (...);
INSERT INTO member_info (member_number, 
            `name`, street_address,
            city, state, zip_code)
  VALUES (...);
--- The rest of the members that are to be created
COMMIT;
```
- ### `provider`
```sql
BEGIN;
INSERT INTO providers (provider_number, name)
    VALUES (...);
INSERT INTO provider_info (provider_number,
            city, state, zip_code)
COMMIT;
```
- ### `service`
