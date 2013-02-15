use POSystem
go


if object_id('DepartmentTreeInsert', 'TR') is not null begin 
	drop trigger DepartmentTreeInsert
end
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('root',			0,			0,			NULL)
go
insert into DepartmentTree(Child, Parent)
values(1, NULL)
go
create trigger DepartmentTreeInsert
on Department after insert
as	insert into DepartmentTree
		select Parent, ID 
		from DepartmentTree, inserted
		where Child = ParentDepartment and Parent is not NULL
	insert into DepartmentTree
		select ParentDepartment, ID
		from inserted
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Executive',		1000000,	1000000,	1)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Administration',	990000,		99000,		2)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Accounting',		5000,		5000,		3)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Engineering',		25000,		25000,		3)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('IT',				10000,		10000,		3)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Manufacturing',	30000,		30000,		3)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Security',		15000,		15000,		3)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Mechanical',		10000,		10000,		5)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Electrical',		10000,		10000,		5)
go
insert into Department(Name, Budget, TotalBudget, ParentDepartment)
values	('Software',		5000,		5000,		5)
go

insert into Users(FirstName, LastName, Username, Email, hashedPass, isDeleted)
values	('System',	'Administrator',	'sysAdmin',		'donotemailme@dontdoit.com',	convert(varbinary(50), ''),0),
		('Alex',	'Memering',			'memeriaj',		'memeriaj@rose-hulman.edu',		convert(varbinary(50), ''),0),
		('Jordan',	'Moore',			'moorejm',		'moorejm@rose-hulman.edu',		convert(varbinary(50), ''),0),
		('Devon',	'Timaeus',			'timaeudg',		'person1@hotmail.com',			convert(varbinary(50), ''),0),
		('Bob',		'Rockwell',			'RockingBob',	'person2@hotmail.com',			convert(varbinary(50), ''),0),
		('Evan',	'Tomeson',			'Tome',			'person3@hotmail.com',			convert(varbinary(50), ''),0),
		('Dean',	'Evanson',			'Elvis',		'person4@hotmail.com',			convert(varbinary(50), ''),0),
		('Greg',	'Burke',			'Pirate95',		'person5@hotmail.com',			convert(varbinary(50), ''),0),
		('Francis',	'Smith',			'42isBest',		'person6@hotmail.com',			convert(varbinary(50), ''),0),
		('Larua',	'Gant',				'Charting',		'person7@hotmail.com',			convert(varbinary(50), ''),0),
		('Haley',	'Rogers',			'FlowerPower',	'person8@hotmail.com',			convert(varbinary(50), ''),0),
		('Anne',	'Koch',				'CookingKoch',	'person9@hotmail.com',			convert(varbinary(50), ''),0),
		('Harry',	'Wellington',		'SurfDude',		'person10@hotmail.com',			convert(varbinary(50), ''),0),
		('Tom',		'Howe',				'TechyTom',		'person11@hotmail.com',			convert(varbinary(50), ''),0)
go
update Users
set hashedPass = [dbo].[fn_GetHash](Username, 'password')
go

insert into UserInDepartment([UID], DID)
values((select ID from Users where Username = 'sysAdmin'),	(select ID from Department where Name = 'root'))
insert into Chair([UID], DID)
values ((select ID from Users where Username = 'sysAdmin'),	(select ID from Department where Name = 'root'))
exec AddChairToDepartment 'memeriaj',	'Executive'
go
exec AddChairToDepartment 'moorejm',	'Executive'
go
exec AddChairToDepartment 'timaeudg',	'Executive'
go
exec AddChairToDepartment 'TechyTom',	'Administration'
go
exec AddChairToDepartment 'Tome',		'Accounting'
go
exec AddChairToDepartment '42isBest',	'Engineering'
go
exec AddChairToDepartment 'SurfDude',	'IT'
go
exec AddChairToDepartment 'RockingBob',	'Manufacturing'
go
exec AddChairToDepartment 'Pirate95',	'Security'
go
exec AddChairToDepartment 'CookingKoch','Mechanical'
go
exec AddChairToDepartment 'Elvis',		'Electrical'
go
exec AddChairToDepartment 'Charting',	'Software'
go