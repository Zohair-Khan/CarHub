drop schema carhub;
create schema carhub;
use carhub;

create table users (
	id INT AUTO_INCREMENT,
	name TEXT,
	email TEXT,
	phone TEXT,
	password TEXT,
	image TEXT,
    primary key (id)
);

create table listings (
	id INT AUTO_INCREMENT,
	make TEXT,
	model TEXT,
	year TEXT,
	price TEXT,
	mileage TEXT,
	driveTerrain TEXT,
	exteriorColor TEXT,
	interiorColor TEXT,
	image TEXT,
	imgfile BLOB,
	seller INT,
	buyer INT,
	sellerPhone TEXT,
	primary key (id),
	foreign key (seller) references users(id),
	foreign key (buyer) references users(id),
	foreign key (sellerPhone) references users(phone)
);