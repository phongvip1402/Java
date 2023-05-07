
create database [asmjava4]
go

use asmjava4
go

create table user_
(
	id					int					primary key identity,
	username			varchar(50)			unique not null,
	[password]	        varchar(50)			not null,
	email				varchar(50)			unique not null,
	isAdmin				bit					not null default 0,
	isActive			bit					not null default 1
)
go

create table video
(
	id					int					primary key identity,
	title				nvarchar(255)		not null,
	href	            varchar(50)			unique not null,
	poster				varchar(255)		null,
	[views]			int						not null default 0,
	shares				int					not null default 0,
	[description]		nvarchar(255)		not null,		
	isActive			bit					not null default 1
)
go

create table history
(
	id					int					primary key identity,
	userId				int					foreign key references user_(id),
	videoId				int					foreign key references video(id),
	viewedDate			datetime			not null default getDate(),
	isLiked				bit					not null default 0,		
	likeDate			datetime			null
)
go

insert into user_ values
('duynt',		'111',   'nguyentranduy94@gmail.com',		1,		1),
('hangtran',	'222',	 'nguyentr.duy@gamil.com',			0,		1)
go

insert into video(title,href,[description]) values
(N' Ông Thầy Tào Lao - Trấn Thành, Trường Giang, Khả Như',		'iIaLwR98Ny4',	'Trấn Thành, Trường Giang, Khả Như'),
(N'Dân số già - Ai làm việc?',									'cekA_0oATqY',	'VTV24'),
(N'TỰ HỌC LẬP TRÌNH CƠ BẢN cho NGƯỜI MỚI BẮT ĐẦU | Vlog',		'PCQ-N7MApwA',	'Ông Dev')	
go

insert into history(userId, videoId, isLiked, likeDate) values
(2,	1,	1,	getDate()),
(2,	3,	0,	null)
go


