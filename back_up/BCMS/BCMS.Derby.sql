create table Crisis(
crisis_id INT not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), -- Auto id
fire_truck_number integer,
police_truck_number integer,
constraint Crisis_key primary key(crisis_id));

-- create table Event(
-- event_id INT not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), -- Auto id
-- event_occurrence_time time,
-- event_name varchar(50),
-- execution_trace varchar(500),
-- crisis_id INT,
-- constraint Event_key primary key(event_id),
-- constraint Crisis_foreign_key foreign key(crisis_id) references Crisis(crisis_id) on delete cascade);

create table Fire_truck(
fire_truck_name varchar(30),
constraint Fire_truck_key primary key(fire_truck_name));

create table Police_vehicle(
police_vehicle_name varchar(30),
constraint Police_vehicle_key primary key(police_vehicle_name));

create table Route(
route_name varchar(30),
constraint Route_key primary key(route_name));

create table Crisis_Fire_truck(
crisis_id INT,
fire_truck_name varchar(30),
fire_truck_status varchar(10) CONSTRAINT fire_truck_status_check CHECK (fire_truck_status IN ('Dispatched','Arrived','Blocked','Breakdown')),
constraint Crisis_Fire_truck_key primary key(crisis_id,fire_truck_name),
constraint Crisis_foreign_key2 foreign key(crisis_id) references Crisis(crisis_id) on delete cascade,
constraint Fire_truck_foreign_key foreign key(fire_truck_name) references Fire_truck(fire_truck_name) on delete cascade);

create table Crisis_Police_vehicle(
crisis_id INT,
police_vehicle_name varchar(30),
police_vehicle_status varchar(10) CONSTRAINT police_vehicle_status_check CHECK (police_vehicle_status IN ('Dispatched','Arrived','Blocked','Breakdown')),
constraint Crisis_Police_vehicle_key primary key(crisis_id,police_vehicle_name),
constraint Crisis_foreign_key3 foreign key(crisis_id) references Crisis(crisis_id) on delete cascade,
constraint Police_vehicle_foreign_key foreign key(police_vehicle_name) references Police_vehicle(police_vehicle_name) on delete cascade);
