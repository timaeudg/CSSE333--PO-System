use master
go

if db_id('POSystem') is not null begin 
	drop database POSystem
end
Create Database [POSystem] ON
	Primary(Name = [POSystem],
	FileName = 'C:\Program Files\Microsoft SQL Server\MSSQL10_50.WHALE\MSSQL\Data\POSystem.mdf',
	Size = 5MB,
	MaxSize=100MB,
	FileGrowth=20%)
	LOG ON
		(Name = [POSystemLog],
		FileName = 'C:\Program Files\Microsoft SQL Server\MSSQL10_50.WHALE\MSSQL\Data\POSystemLog.ldf',
		Size = 1MB,
		MaxSize=75MB,
		FileGrowth=10%)
	COLLATE SQL_Latin1_General_Cp1_CI_AS
go

sp_adduser timaeudg, timaeudg, [db_owner];
go
sp_adduser moorejm, moorejm, [db_owner];
go


use [POSystem]
go

grant exec on AcceptPaymentOrder to [333POS]
grant exec on AddChairToDepartment to [333POS]
grant exec on AddLineItem to [333POS]
grant exec on AddPaymentOrder to [333POS]
grant exec on AddReceipt to [333POS]
grant exec on AddUserToDepartment to [333POS]
grant exec on AllDepartmentInfo to [333POS]
grant exec on AttemptLogin to [333POS]
grant exec on ChairList to [333POS]
grant exec on DeleteDepartment to [333POS]
grant exec on DeleteUser to [333POS]
grant exec on DepartmentInfo to [333POS]
grant exec on EditDepartment to [333POS]
grant exec on GetDepartmentPaymentOrders to [333POS]
grant exec on GetUserPaymentOrders to [333POS]
grant exec on IsRoot to [333POS]
grant exec on LookUpUser to [333POS]
grant exec on ModifyUser to [333POS]
grant exec on NewDepartment to [333POS]
grant exec on NewUser to [333POS]
grant exec on RejectPaymentOrder to [333POS]
grant exec on RemoveChairFromDepartment to [333POS]
grant exec on RemoveUserFromDepartment to [333POS]
go

if object_id('Users', 'U') is not null begin 
	drop table Users
end
create table Users (
	ID int not null primary key identity,
	FirstName varchar(20),
	LastName varchar(20),
	Username varchar(20) unique,
	Email varchar(100),
	hashedPass varbinary(50) NOT NULL,
	isDeleted bit)
go
if object_id('Department', 'U') is not null begin 
	drop table Department
end
go
create table Department (
	ID int not null primary key identity,
	Name varchar(100),
	Budget decimal(12, 2),
	TotalBudget decimal(12, 2),
	ParentDepartment int)
go
if object_id('PaymentOrder', 'U') is not null begin 
	drop table PaymentOrder
end
go
create table PaymentOrder (
	ID int not null primary key identity,
	Reason text,
	Reimburse varchar(20),
	[Date] Date,
	Department int not null foreign key references Department(ID),
	Creator int not null foreign key references Users(ID))
go
if object_id('Receipt', 'U') is not null begin 
	drop table Receipt
end
go
create table Receipt (
	ID int not null primary key identity,
	[Timestamp] DateTime,
	StoreName varchar(100),
	Storage text,
	PaymentOrder int not null foreign key references PaymentOrder(ID))
go
if object_id('LineItem', 'U') is not null begin 
	drop table LineItem
end
go
create table LineItem (
	ID int not null primary key identity,
	Cost decimal(10, 2),
	Name varchar(100),
	Receipt int not null foreign key references Receipt(ID))
go
if object_id('UserInDepartment', 'U') is not null begin 
	drop table UserInDepartment
end
go
create table UserInDepartment (
	[UID] int not null foreign key references Users(ID),
	DID int not null foreign key references Department(ID))
go
if object_id('Chair', 'U') is not null begin 
	drop table Chair
end
go
create table Chair (
	[UID] int not null foreign key references Users(ID),
	DID int not null foreign key references Department(ID))
go
if object_id('SignsOff', 'U') is not null begin 
	drop table SignsOff
end
go
create table SignsOff (
	[UID] int not null foreign key references Users(ID),
	POID int not null foreign key references PaymentOrder(ID),
	DID int not null foreign key references Department(ID),
	[Status] varchar(20))
go
if object_id('MayHave', 'U') is not null begin 
	drop table MayHave
end
go
create table MayHave (
	LIID int not null foreign key references LineItem(ID),
	RID int not null foreign key references Receipt(ID))
go


if object_id('DepartmentTree', 'U') is not null begin 
	drop table DepartmentTree
end
go
create table DepartmentTree (
	Parent int foreign key references Department(ID),
	Child int not null foreign key references Department(ID))
go
if object_id('DepartmentTreeInsert', 'TR') is not null begin 
	drop trigger DepartmentTreeInsert
end
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
if object_id('DepartmentTreeDelete', 'TR') is not null begin 
	drop trigger DepartmentTreeDelete
end
go
create trigger DepartmentTreeDelete
on Department after delete
as	delete from DepartmentTree
	where	Parent in (select ID from deleted) or
			Child in (select ID from  deleted)
go
if object_id('DepartmentTreeUpdate', 'TR') is not null begin 
	drop trigger DepartmentTreeUpdate
end
go
create trigger DepartmentTreeUpdate
on Department after update
as	delete from DepartmentTree
		where Child in (select ID from  deleted)
	insert into DepartmentTree
		select Parent, ID 
		from DepartmentTree, inserted
		where Child = ParentDepartment and Parent is not NULL
	insert into DepartmentTree
		select ParentDepartment, ID
		from inserted
go