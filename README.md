# ChocAn Data Center
[![Build Status](https://travis-ci.org/Gilmore-PDX-CS/Group4.svg?branch=master)](https://travis-ci.org/Gilmore-PDX-CS/Group4)


An implementation of a data center to track users, members, providers, etc
for a chocolate addiction rehab service.

## TODO
- [ ] Add userdocs


## Creating Test Data
1. Create a MySQL data source or connect to a MySQL server
(See [Setup MySQL Data Source in Intellij](https://github.com/Gilmore-PDX-CS/Group4/wiki/Setup-MySQL-Data-Source-in-Intellij))
1. Run the following files against this server in `test/resources/mock-data`:
     - `create_member.sql`
     - `create_provider.sql`
     - `service_info.sql`
     - `perform_services.sql`
1. Run ``CALL perform_rand_services();``
