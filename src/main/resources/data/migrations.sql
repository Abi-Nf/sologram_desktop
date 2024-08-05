create table artist
(
    id       integer
        primary key autoincrement,
    name     text not null
        unique,
    bio      text,
    image    text,
    added_at text default current_timestamp
);

create table album
(
    id       integer
        primary key autoincrement,
    name     text,
    artist   integer
        references artist,
    art      text,
    year     integer,
    added_at text default current_timestamp
);

create table album_feat_artist
(
    id     integer
        primary key autoincrement,
    album  integer
        references album,
    artist integer
        references artist
);

create table folder
(
    id     integer
        primary key autoincrement,
    folder text
        unique
);

create table genre
(
    id       integer
        primary key autoincrement,
    name     text not null
        unique,
    image    text,
    added_at text default current_timestamp
);

create table album_genres
(
    id    integer
        primary key autoincrement,
    album integer
        references album,
    genre integer
        references genre
);

create table playlist
(
    id          integer
        primary key autoincrement,
    name        text,
    image       text,
    description text,
    created_at  text default current_timestamp
);

create table track
(
    id           integer
        primary key autoincrement,
    source       text not null
        unique,
    title        text,
    album        integer
        references album,
    duration     integer,
    track_number integer,
    track_of     integer,
    disc_number  integer,
    disc_of      integer,
    lyrics       text,
    added_at     text
);

create table likes
(
    id       integer
        primary key autoincrement,
    track    integer
        references track,
    liked_at text default current_timestamp
);

create table play_track_history
(
    id        integer
        primary key autoincrement,
    track     integer
        references track,
    played_at text default current_timestamp
);

create table playlist_track
(
    id       integer
        primary key autoincrement,
    track    integer
        references track,
    playlist integer
        references playlist
);

