use POSystem
go


if object_id('AcceptPaymentOrder', 'P') is not null begin 
	drop procedure AcceptPaymentOrder
end
go
create procedure AcceptPaymentOrder(
	@POID as int,
	@username as varchar(20))
as
declare @DID int
set @DID = (select DID from SignsOff where [UID] = (select ID from Users where Username = @username) and POID = @POID)
update SignsOff
set [Status] = 'Accepted'
where [UID] = (select ID from Users where Username = @username) and POID = @POID
delete from SignsOff
where [Status] = 'Pending' and POID = @POID
if exists (select D.ParentDepartment
			from SignsOff as S, Department as D
			where S.POID = @POID and S.DID = D.ID and D.ParentDepartment is not NULL)
begin
	insert into SignsOff
		select  C.[UID], S.POID, D.ParentDepartment, 'Pending'
		from SignsOff as S, Chair as C, Department as D
		where S.POID = @POID and S.DID = D.ID and D.ParentDepartment = C.DID and D.ID = @DID
end
go

if object_id('AddChairToDepartment', 'P') is not null begin 
	drop procedure AddChairToDepartment
end
go
create procedure AddChairToDepartment(
	@username varchar(20),
	@departmentID varchar(50))
as
DECLARE @DID int
SELECT @DID = Department.ID FROM Department WHERE Department.Name = @departmentID
if exists (select * from Department where ID = @DID and ParentDepartment is NULL)
begin
	Raiserror('Cannot add a chairperson to the root department', 14, 1)
	return
end
if not exists (select * from Chair where [UID] = (select ID from Users where Username = @username) and DID = @DID)
begin
	if (select ID from Users where Username = @username) not in (select [UID] from UserInDepartment where DID = @DID)
	begin
		insert into UserInDepartment([UID], DID)
		values((select ID from Users where Username = @username), @DID)
	end
	insert into Chair([UID], DID)
	values ((select ID from Users where Username = @username), @DID)
end
else begin
	Raiserror('No duplicates allowed', 14, 1)
end
go

if object_id('AddLineItem', 'P') is not null begin 
	drop procedure AddLineItem
end
go
create procedure AddLineItem(
	@cost decimal(10, 2),
	@name varchar(100),
	@receipt int)
as
insert into LineItem(Cost, Name, Receipt)
output inserted.ID
values(@cost, @name, @receipt)
go

if object_id('AddPaymentOrder', 'P') is not null begin 
	drop procedure AddPaymentOrder
end
go
create procedure AddPaymentOrder(
	@username varchar(20),
	@departmentName varchar(100),
	@reimburse varchar(20),
	@reason text,
	@date Date,
	@POID int output)
as
declare @POIDT table (ID int)
insert into PaymentOrder(Creator, Department, Reason, Reimburse, [Date])
output inserted.ID into @POIDT
values((select ID from Users where Username = @username), (select ID from Department where Name = @departmentName), @reason, @reimburse, @date)
SELECT @POID = (select ID from @POIDT)
if not exists (select * from Chair as C, Department as D where C.DID = D.ID and D.Name = @departmentName)
begin
	Raiserror('Cannot add a chairperson to the root department', 14, 1)
	return
end
insert into SignsOff([UID], POID, DID, [Status])
(
	select C.[UID], @POID as POID, D.ID, 'Pending' as [Status]
	from Chair as C, Department as D
	where C.DID = D.ID and D.Name = @departmentName
	)
go

if object_id('AddReceipt', 'P') is not null begin 
	drop procedure AddReceipt
end
go
create procedure AddReceipt(
	@time DateTime,
	@name varchar(100),
	@loc text,
	@paymentOrder int)
as
insert into Receipt([Timestamp], StoreName, Storage, PaymentOrder)
output inserted.ID
values(@time, @name, @loc, @paymentOrder)
go

if object_id('AddUserToDepartment', 'P') is not null begin 
	drop procedure AddUserToDepartment
end
go
create procedure AddUserToDepartment(
	@username varchar(20),
	@departmentID varchar(20))
as
DECLARE @DID INT
SELECT @DID = Department.ID FROM Department WHERE Department.Name = @departmentID
if exists (select * from Department where ID = @DID and ParentDepartment is NULL)
begin
	Raiserror('Cannot add a person to the root department', 14, 1)
	return
end
insert into UserInDepartment([UID], DID)
values ((select ID from Users where Username = @username), @DID)
go

if object_id('AllDepartmentInfo', 'P') is not null begin 
	drop procedure AllDepartmentInfo
end
go
create procedure AllDepartmentInfo
as
select *
from Department
go

if object_id('AttemptLogin', 'P') is not null begin 
	drop procedure AttemptLogin
end
go
create PROCEDURE AttemptLogin(
	@username varchar(20),
	@password varchar(50))
AS
IF @username NOT IN (SELECT Username FROM Users) BEGIN
	RAISERROR('The username does not exist', 14, 1)
	RETURN 0
END
IF (select isDeleted from Users where Username = @username) = 1 BEGIN
	RAISERROR('The username does not exist', 14, 1)
	RETURN 0
END
DECLARE @passwordHash AS varbinary(50)
SET @passwordHash = ( SELECT [hashedPass] 
						FROM Users 
						WHERE Username = @username)
IF @passwordHash = [dbo].fn_GetHash(@username, @password) BEGIN
	RETURN 1
END ELSE BEGIN
	RAISERROR('The password is not correct', 14, 1)
	RETURN 0
END
go

if object_id('ChairList', 'P') is not null begin 
	drop procedure ChairList
end
go
create procedure ChairList(
	@username varchar(20) = NULL)
as 
select D.Name
from Users as U, Chair as C, Department as D
where U.Username = @username and U.ID = C.[UID] and D.ID = C.DID
go

if object_id('DeleteDepartment', 'P') is not null begin 
	drop procedure DeleteDepartment
end
go
create procedure DeleteDepartment(
	@department varchar(50))
as
declare @id as int
set @id = (select ID from Department where Name = @department)
update PaymentOrder
set Department = (	select ParentDepartment
					from Department
					where ID = @id)
update SignsOff
set DID = (	select ParentDepartment
			from Department
			where ID = @id)
where DID = @id
delete from UserInDepartment
where DID = @id
delete from Chair
where DID = @id
delete from DepartmentTree
where Parent = @id or Child = @id
delete from Department
where ID = @id
go

if object_id('DeleteUser', 'P') is not null begin 
	drop procedure DeleteUser
end
go
create Procedure DeleteUser(
	@deleter varchar(20),
	@deletee varchar(20))
as
if @deletee = @deleter begin
	update Users
	set isDeleted = 1
	where Username = @deletee
end else begin
	if not exists (	select *
					from DepartmentTree
					where Parent in (	select ID
										from Users as U, UserInDepartment as UD
										where U.Username = @deletee and U.ID = UD.[UID])
						and Child in (	select ID
										from Users as U, UserInDepartment as UD
										where U.Username = @deleter and U.ID = UD.[UID]))
		and not exists (	select *
							from Users as U1, UserInDepartment as UD1
							where U1.username = @deletee and U1.ID = UD1.[UID] and
								UD1.DID in (	select DID
												from Users as U2, UserInDepartment as UD2
												where U2.username = @deleter and U2.ID = UD2.[UID])) begin
		update Users
		set isDeleted = 1
		where Username = @deletee
	end else begin
		RAISERROR('The deleter is in a lower Department than the deletee', 14, 1)
	end
end
go

if object_id('DepartmentInfo', 'P') is not null begin 
	drop procedure DepartmentInfo
end
go
create procedure DepartmentInfo(
	@DepartmentID int)
as
select *
from Department
where ID = @DepartmentID
go

if object_id('EditDepartment', 'P') is not null begin 
	drop procedure EditDepartment
end
go
create PROCEDURE EditDepartment 
	@toEdit varchar(100) = null,
	@newName varchar(100)= null,
	@newParent varchar(100) = null,
	@newBudget decimal (12,2) = null
AS
BEGIN
	DECLARE @CURRENTbudget decimal(12,2)
	DECLARE @totalBudget decimal (12,2)
	SET @CURRENTbudget = @newBudget
	SET @totalBudget = @newBudget
	if @toEdit is Null
		begin
		Raiserror('No department to edit', 14,1)
		end
	if @newBudget is null and @newName is null and @newParent is null
		begin
		raiserror('Nothing to change',14,1)
		end
	if @newBudget <0
		begin
		Select @totalBudget = Department.TotalBudget , @CURRENTbudget = Department.Budget From Department where Department.Name = @toEdit
		end
	Declare @DID int
	DECLARE @PARENTid int
	DECLARE @oldParent int
	select @DID = Department.ID from Department where Department.Name = @toEdit
	if @newName is not Null
		begin
		PRINT(@DID)
		print(@newName)
		update Department
		set Name = @newName
		where ID = @DID
		end
	if @newParent is not null
		begin
		SELECT @oldParent = Department.ParentDepartment FROM Department WHERE Department.Name = @toEdit
		SELECT @PARENTid = Department.ID FROM Department WHERE Department.Name = @newParent
		IF @oldParent<>@PARENTid
			BEGIN
			update Department
			set ParentDepartment = @PARENTid
			where Department.ID=@DID
			DECLARE @temporaryParents table(
				tempParents int,
				child int)
			insert into @temporaryParents (tempParents, child)
			(Select Parent, @DID from DepartmentTree where DepartmentTree.Child = @PARENTid)
			select * from @temporaryParents
			delete from DepartmentTree
			where DepartmentTree.Child = @DID
			insert into DepartmentTree (Parent,Child)
			((Select tempParents, child From @temporaryParents))
			update Department
			set ParentDepartment = @oldParent
			WHERE Department.ParentDepartment = @DID
			END
		end
	if @newBudget is not null
		begin
		update Department
		set Budget = @CURRENTbudget, TotalBudget=@totalBudget
		where ID = @DID
		end
END
go

if object_id('GetDepartmentPaymentOrders', 'P') is not null begin 
	drop procedure GetDepartmentPaymentOrders
end
go
create procedure GetDepartmentPaymentOrders(
	@DName varchar(50))
as
select PO.ID, PO.Reason, PO.Reimburse, PO.[Date], U.Username
from PaymentOrder as PO, SignsOff as SO, Users as U
where SO.DID = (select ID from Department where Name = @DName) and SO.POID = PO.ID and SO.[Status] = 'Pending' and U.ID = PO.Creator 
	and SO.[UID] = (select MAX(UID) from SignsOff where DID = (select ID from Department where Name = @DName) and [Status] = 'Pending')
go

if object_id('GetUserPaymentOrders', 'P') is not null begin 
	drop procedure GetUserPaymentOrders
end
go
create procedure GetUserPaymentOrders(
	@username varchar(20))
as
select distinct cast(PO.Reason as varchar(8000)), PO.Reimburse, PO.[Date], PO.Department, SO.[Status], SO.DID
from PaymentOrder as PO, SignsOff as SO
where PO.Creator = (select ID from Users where Username = @username) and PO.ID = SO.POID and (
	(SO.[Status] = 'Pending' and not exists(	select *
												from Department as D
												where D.ParentDepartment is null and D.ID = SO.DID))
	
	or (SO.[Status] = 'Accepted' and SO.DID in	(select ID
												from Department as D
												where D.ParentDepartment is null))
	or (SO.DID not in (	select ID
						from Department as D
						where D.ParentDepartment is null) and not exists(	select *
																			from SignsOff as S
																			where S.DID = SO.DID and S.POID = SO.POID 
																				and (S.[Status] = 'Pending' or S.[Status] = 'Accepted'))))
go

if object_id('IsRoot', 'P') is not null begin 
	drop procedure IsRoot
end
go
create procedure IsRoot
as
select D.Name
from DepartmentTree as DT, Department as D
where D.ID = DT.Child and DT.Parent = 'NULL'
go

if object_id('LookUpUser', 'P') is not null begin 
	drop procedure LookUpUser
end
go
create Procedure LookUpUser(
	@username varchar(20) = NULL,
	@firstname varchar(20) = NULL,
	@lastname varchar(20) = NULL,
	@email varchar(100) = NULL)
as
if @username is NULL
	SET @username = '%';
if @firstname is NULL
	SET @firstname = '%';
if @lastname is NULL
	SET @lastname = '%';
if @email is NULL
	SET @email = '%';
select Users.Username, Users.FirstName, Users.LastName, Users.Email
FROM Users
WHERE Users.FirstName LIKE @firstname AND Users.LastName LIKE @lastname AND Users.Username LIKE @username AND Users.Email LIKE @email and isDeleted = 0
go

if object_id('ModifyUser', 'P') is not null begin 
	drop procedure ModifyUser
end
go
create procedure ModifyUser(
	@oldUsername varchar(20) = NULL,
	@newUsername varchar(20) = NULL,
	@firstname varchar(20) = NULL,
	@lastname varchar(20) = NULL,
	@email varchar(100) = NULL,
	@password varchar(50) = NULL)
as
if not exists (select username from Users where Username = @oldUsername) 
begin
	RAISERROR ('User %s does not exist in database', 14, 1, @oldusername);
	RETURN 1;
end
declare @hash varbinary(50)
if @newUsername is NULL begin
	set @newUsername = @oldUsername
end
if @firstname is NULL begin
	set @firstname = (	select FirstName
						from Users
						where Username = @oldUsername)
end
if @lastname is NULL begin
	set @lastname = (	select LastName
						from Users
						where Username = @oldUsername)
end
if @email is NULL begin
	set @email = (	select Email
					from Users
					where Username = @oldUsername)
end
if @password is NULL begin
	set @hash =		(	select hashedPass
						from Users
						where Username = @oldUsername)
end else begin
	set @hash = [dbo].[fn_GetHash](@newUsername, @password)
end
update Users 
set Username = @newUsername, FirstName = @firstname, LastName = @lastname, Email = @email, hashedPass = @hash
where Username = @oldUsername
go

if object_id('NewDepartment', 'P') is not null begin 
	drop procedure NewDepartment
end
go
create procedure NewDepartment(
	@name varchar(100),
	@budget decimal(12,2),
	@totalBudget decimal(12,2),
	@parentDepartment varchar(100))
as
DECLARE @DID int
select @DID = Department.ID FROM Department WHERE Department.Name = @parentDepartment
if(@DID is NULL)
	begin
	RAISERROR('Department does not exist',6,4)
	end
insert into Department
values (@name, @budget, @totalBudget, @DID)
go

if object_id('NewUser', 'P') is not null begin 
	drop procedure NewUser
end
go
create Procedure NewUser(
	@firstName varchar(20),
	@lastName varchar(20),
	@username varchar(20),
	@password varchar(50),
	@email varchar(100))
as
if @username in (select Username from Users) begin
	RAISERROR('The Username is already in use.', 14, 1)
	return 1
end else begin
	declare @hash varbinary(50)
	set @hash = [dbo].[fn_GetHash](@username, @password)
	insert into Users (FirstName, LastName, Username, Email, [hashedPass], isDeleted)
	values (@firstName, @lastName, @username, @email, @hash, 0)
	return 0
end
go

if object_id('RejectPaymentOrder', 'P') is not null begin 
	drop procedure RejectPaymentOrder
end
go
create  procedure RejectPaymentOrder(
	@POID as int,
	@username as varchar(20))
as
update SignsOff
set [Status] = 'Rejected'
where [UID] = (select ID from Users where Username = @username) and POID = @POID
go

if object_id('RemoveChairFromDepartment', 'P') is not null begin 
	drop procedure RemoveChairFromDepartment
end
go
create procedure RemoveChairFromDepartment(
	@username varchar(20),
	@departmentID varchar(100))
as
IF NOT EXISTS (	SELECT * 
				FROM Chair 
				WHERE Chair.UID = (	SELECT Users.ID 
									FROM Users 
									where Users.Username = @username) 
					AND Chair.DID = (	SELECT Department.ID
										FROM Department 
										where Department.Name = @departmentID))
	BEGIN
	RAISERROR('User is not a chair of that department',14,1)
	RETURN 1
	END
DECLARE @DID int
SELECT @DID = Department.ID FROM Department WHERE @departmentID = Department.NAME
delete from Chair
where [UID] = (select ID from Users where Username = @username) and DID = @DID
delete from SignsOff
where [UID] = (select ID from Users where Username = @username) and [Status] = 'Pending'
go

if object_id('RemoveUserFromDepartment', 'P') is not null begin 
	drop procedure RemoveUserFromDepartment
end
go
create procedure RemoveUserFromDepartment(
	@username varchar(20),
	@departmentID VARCHAR(50))
as
DECLARE @DID INT
SELECT @DID = Department.ID FROM Department WHERE Department.Name= @departmentID
if (select ID from Users where Username = @username) in (select [UID] from Chair where DID = @DID)
begin
	delete from Chair
	where [UID] = (select ID from Users where Username = @username) and DID = @DID
end
delete from UserInDepartment
where [UID] = (select ID from Users where Username = @username) and DID = @DID
go