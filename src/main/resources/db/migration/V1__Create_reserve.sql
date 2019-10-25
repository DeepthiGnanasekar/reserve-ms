create table reserve_list(reserve_id int  Primary Key auto_increment,
user_id int,
user_name varchar(30),
reserved_cans int,
reserved_ordercans int,
stock_status varchar(30),
reserved_date datetime default current_timestamp);