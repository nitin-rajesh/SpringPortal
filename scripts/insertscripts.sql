insert into student_filter(student_id,specialisation_id,domain_id) 
values (8,2,1),(8,1,1),(9,5,1),(9,1,1),(10,3,2),(10,1,2);

insert into organisations(name,address) 
values ('The CS Company','WTC Blore'),('The DS Company','WTC Blore');

insert into placements(organisation_id,profile,description,intake,min_grade) 
values (5,'Systems engineer','Develop and maintain software systems',4,7.8),
(5,'Full stack developer','Create web pages frontend and backend',6,8.2),
(5,'AI engineer','Work on AI model training and fine-tuning',2,8.0),
(6,'Data scientist','Build and analyse large data models',2,7.5);

insert into placement_filter(placement_id,specialisation_id,domain_id) 
values (7,5,1),(8,1,1),(9,2,1),(10,3,2);
