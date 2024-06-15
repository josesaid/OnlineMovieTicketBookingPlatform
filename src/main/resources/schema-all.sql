/*MySQL Syntax*/
CREATE TABLE RESERVATIONS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  clientFullName VARCHAR(200) UNIQUE,
  roomNumber INT,
  reservationDates JSON
);

select * from RESERVATIONS;


/*
CREATE TABLE RESERVATIONS (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 200, INCREMENT BY 1) PRIMARY KEY,
    clientFullName VARCHAR(200),
    roomNumber int,
    reservationDates varchar(200)
);
*/