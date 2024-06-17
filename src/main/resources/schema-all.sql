/*MySQL Syntax*/
CREATE TABLE THEATRES (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) UNIQUE,
  address VARCHAR(200) ,
  city VARCHAR(40) ,
  state VARCHAR(40) ,
  country VARCHAR(40)
);

CREATE TABLE ROOMS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  idEvent int,
  status VARCHAR(60),
  seatsMap VARCHAR(200)
);

CREATE TABLE USERS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) ,
  email VARCHAR(70) UNIQUE,
  cellPhone VARCHAR(20) ,
  status VARCHAR(20)
);


CREATE TABLE PAYMENTS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  amount FLOAT(200) ,
  timestamp VARCHAR(70) ,
  paymentType VARCHAR(50) ,
  description VARCHAR(70),
  referenceDetails VARCHAR(70),
  status varchar(20)
);

CREATE TABLE EVENTS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  eventType VARCHAR(50) ,
  eventName VARCHAR(50) ,
  initTime VARCHAR(50) ,
  finishTime VARCHAR(50) ,
  eventAudioLanguage VARCHAR(50),
  eventSubtitleLanguage  VARCHAR(50),
  classification VARCHAR(50),
  comments VARCHAR(50)
);

CREATE TABLE BOOKINGS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  idTheatre INT,
  idRoom INT,
  idUser INT,
  idPayment INT,
  comments VARCHAR(50),
  status  VARCHAR(80)
);

