create table COOKIE (
    ID uuid not null,
    VERSION int default 0,
    CREATED_DATE datetime,
    LAST_MODIFIED_DATE datetime,
    PRICE numeric(19, 2),
    NAME varchar(255),
    primary key (ID)
);