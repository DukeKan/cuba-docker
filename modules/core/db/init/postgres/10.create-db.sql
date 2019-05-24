-- begin CUBADOCKER_CONNECTION_PARAMS
create table CUBADOCKER_CONNECTION_PARAMS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    HOST varchar(500),
    PORT integer,
    --
    primary key (ID)
)^
-- end CUBADOCKER_CONNECTION_PARAMS
