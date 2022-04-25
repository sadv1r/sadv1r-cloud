create table links
(
    id        varchar(8) primary key,
    url       text        not null,
    hits      integer     not null default 0,
    createdAt timestamptz not null default now()
-- ,
--     updatedAt timestamptz
);
