use [POSystem]
go

create clustered index UserIndex
	on [User] (ID)
go
create clustered index DepartmentIdex
	on Department (ID)
go
create clustered index PaymentOrderIndex
	on PaymentOrder (ID)
go
create clustered index LineItemIndex
	on LineItem (ID)
go
create clustered index ReceiptIndex
	on Receipt (ID)
go

create clustered index UserInDepartmentIndex
	on UserInDepartment ([UID])
go
create clustered index ChairIndex
	on Chair ([UID])
go
create clustered index SignsOffIndex
	on SignsOff ([UID])
go
create clustered index MayHaveIndex
	on MayHave (LIID)
go