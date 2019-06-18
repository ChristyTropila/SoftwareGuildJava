DROP DATABASE IF EXISTS superherosightings;

CREATE DATABASE superherosightings;

USE superherosightings;

CREATE TABLE superpowers(
superPowerId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
powerName VARCHAR(45) NOT NULL,
descrip VARCHAR(45) NOT NULL
);

CREATE TABLE superVillains(
villainID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
villainName VARCHAR(30) NOT NULL,
descrip VARCHAR(45) NOT NULL,
superPowerId INT NOT NULL,
FOREIGN KEY (superPowerId) REFERENCES superpowers(superPowerId)
);


CREATE TABLE organizations(
organizationId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
`description` VARCHAR(45) NOT NULL,
phoneNum VARCHAR(10) NOT NULL,
locationId INT NOT NULL
);


CREATE TABLE superVillains_organizations(
villainID INT NOT NULL,
organizationId INT NOT NULL
);

ALTER TABLE superVillains_organizations ADD CONSTRAINT fk_superVillains_organizations_superVillains
FOREIGN KEY (villainID) REFERENCES superVillains(villainID);
ALTER TABLE superVillains_organizations ADD CONSTRAINT fk_superVillains_organizations_organizations
FOREIGN KEY (organizationId) REFERENCES organizations(organizationId);


CREATE TABLE locations(
locationId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`description` VARCHAR(45),
streetName VARCHAR(30) NOT NULL,
city VARCHAR(20) NOT NULL,
state VARCHAR(20) NOT NULL,
zipCode VARCHAR(10) NOT NULL,
longitude DECIMAL(9,7),
latitude DECIMAL(9,7)
);

ALTER TABLE organizations ADD CONSTRAINT fk_organizations_locations
FOREIGN KEY (locationId) REFERENCES locations(locationId);

CREATE TABLE sightings(
sightingId INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
sightingDate DATE,
locationId INT NOT NULL
);

ALTER TABLE sightings ADD CONSTRAINT fk_sightings_locations
FOREIGN KEY (locationId) REFERENCES locations(locationId);

CREATE TABLE superVillains_sightings(
sightingId INT NOT NULL,
villainID INT NOT NULL  
);

ALTER TABLE superVillains_sightings ADD CONSTRAINT fk_superVillains_sightings_superVillains
FOREIGN KEY (villainID) REFERENCES superVillains(villainID);

ALTER TABLE superVillains_sightings ADD CONSTRAINT fk_superVillains_sightings_sightings
FOREIGN KEY (sightingId) REFERENCES sightings(sightingId);



create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "password", true),
        (2,"sidekick","password",true);
        
insert into `role`(`id`,`role`)
    values(1,"ROLE_ADMIN"), (2,"ROLE_SIDEKICK");
       
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);

UPDATE user SET password = '$2a$10$gQYs8LQURcbNNfvpNRGFhuC/TLiSt.9mqSfMw43e5FtcTEN8CdrLu' WHERE id = 1;
UPDATE user SET password = '$2a$10$gQYs8LQURcbNNfvpNRGFhuC/TLiSt.9mqSfMw43e5FtcTEN8CdrLu' WHERE id = 2;
