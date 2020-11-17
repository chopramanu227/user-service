DROP TABLE IF EXISTS UserAddress;
DROP TABLE IF EXISTS UserDetail;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS UserRole;


CREATE TABLE UserAddress (
    addressId INT AUTO_INCREMENT  PRIMARY KEY,
    street VARCHAR(250),
    city  VARCHAR(50),
    state VARCHAR(10),
    postcode VARCHAR(10)
);

CREATE TABLE UserDetail (
    userId INT AUTO_INCREMENT  PRIMARY KEY,
    title VARCHAR(4),
    firstName VARCHAR(25),
    lastName VARCHAR(25),
    gender VARCHAR(10),
    bCryptedPassword VARCHAR(250),
    addressId INT,
    constraint FK_Address_Id foreign key (addressId) references UserAddress(addressId)
);

CREATE TABLE Role (
    roleId INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(10)
);

CREATE TABLE UserRole (
    userId INT,
    roleId INT,
    constraint FK_User_Id foreign key (userId) references UserDetail(userId),
    constraint FK_Role_Id foreign key (roleId) references Role(roleId)
);
