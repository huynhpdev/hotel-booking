USE [master]
GO
/****** Object:  Database [SE1612_HotelBooking]    Script Date: 10/6/2022 7:38:27 AM ******/
CREATE DATABASE [SE1612_HotelBooking]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Hotel_Booking', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SE130068\MSSQL\DATA\Hotel_Booking.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Hotel_Booking_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SE130068\MSSQL\DATA\Hotel_Booking_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [SE1612_HotelBooking] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SE1612_HotelBooking].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SE1612_HotelBooking] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET ARITHABORT OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [SE1612_HotelBooking] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [SE1612_HotelBooking] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET  DISABLE_BROKER 
GO
ALTER DATABASE [SE1612_HotelBooking] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [SE1612_HotelBooking] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [SE1612_HotelBooking] SET  MULTI_USER 
GO
ALTER DATABASE [SE1612_HotelBooking] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [SE1612_HotelBooking] SET DB_CHAINING OFF 
GO
ALTER DATABASE [SE1612_HotelBooking] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [SE1612_HotelBooking] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [SE1612_HotelBooking] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [SE1612_HotelBooking] SET QUERY_STORE = OFF
GO
USE [SE1612_HotelBooking]
GO
/****** Object:  Table [dbo].[tbl_Booking]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Booking](
	[bookingID] [int] IDENTITY(1,1) NOT NULL,
	[userInfoID] [int] NOT NULL,
	[roomID] [int] NOT NULL,
	[amount] [int] NOT NULL,
	[bookDate] [date] NOT NULL,
	[discountCodeID] [int] NULL,
	[total] [float] NOT NULL,
	[bookingStatus] [int] NOT NULL,
	[confirmStatus] [int] NOT NULL,
	[isFeedback] [int] NOT NULL,
 CONSTRAINT [PK_Booking] PRIMARY KEY CLUSTERED 
(
	[bookingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_DiscountCode]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_DiscountCode](
	[discountCodeID] [int] IDENTITY(1,1) NOT NULL,
	[discountCode] [varchar](100) NOT NULL,
	[discountPercent] [int] NOT NULL,
	[expiryDate] [date] NOT NULL,
 CONSTRAINT [PK_tbl_DiscountCode_1] PRIMARY KEY CLUSTERED 
(
	[discountCodeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Hotel]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Hotel](
	[hotelID] [int] IDENTITY(1,1) NOT NULL,
	[hotelName] [varchar](100) NOT NULL,
 CONSTRAINT [PK_tbl_Hotel] PRIMARY KEY CLUSTERED 
(
	[hotelID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_QualityRating]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_QualityRating](
	[ratingID] [int] IDENTITY(1,1) NOT NULL,
	[roomID] [int] NOT NULL,
	[userInfoID] [int] NOT NULL,
	[feedback] [varchar](100) NOT NULL,
	[ratingScore] [int] NOT NULL,
 CONSTRAINT [PK_tbl_QualityRating] PRIMARY KEY CLUSTERED 
(
	[ratingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Room]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Room](
	[roomID] [int] IDENTITY(1,1) NOT NULL,
	[hotelID] [int] NOT NULL,
	[roomTypeID] [int] NOT NULL,
	[checkInDate] [date] NOT NULL,
	[checkOutDate] [date] NOT NULL,
	[totalRoom] [int] NOT NULL,
	[availableRoom] [int] NOT NULL,
	[price] [float] NOT NULL,
 CONSTRAINT [PK_tbl_Room] PRIMARY KEY CLUSTERED 
(
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_RoomType]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_RoomType](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](100) NOT NULL,
 CONSTRAINT [PK_tbl_Category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_User]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_User](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[roleID] [int] NOT NULL,
	[statusID] [int] NOT NULL,
 CONSTRAINT [PK_tbl_User] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UserInfo]    Script Date: 10/6/2022 7:38:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UserInfo](
	[userInfoID] [int] IDENTITY(1,1) NOT NULL,
	[userID] [int] NOT NULL,
	[phone] [varchar](10) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[address] [nvarchar](100) NOT NULL,
	[createdDate] [date] NOT NULL,
 CONSTRAINT [PK_tbl_UserInfo] PRIMARY KEY CLUSTERED 
(
	[userInfoID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_Booking] ON 

INSERT [dbo].[tbl_Booking] ([bookingID], [userInfoID], [roomID], [amount], [bookDate], [discountCodeID], [total], [bookingStatus], [confirmStatus], [isFeedback]) VALUES (3, 1, 1, 5, CAST(N'2022-09-25' AS Date), NULL, 1000, 0, 1, 1)
INSERT [dbo].[tbl_Booking] ([bookingID], [userInfoID], [roomID], [amount], [bookDate], [discountCodeID], [total], [bookingStatus], [confirmStatus], [isFeedback]) VALUES (19, 1, 5, 5, CAST(N'2022-10-04' AS Date), 2, 600, 0, 0, 1)
INSERT [dbo].[tbl_Booking] ([bookingID], [userInfoID], [roomID], [amount], [bookDate], [discountCodeID], [total], [bookingStatus], [confirmStatus], [isFeedback]) VALUES (20, 1, 7, 5, CAST(N'2022-10-04' AS Date), 2, 1100, 0, 0, 0)
SET IDENTITY_INSERT [dbo].[tbl_Booking] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_DiscountCode] ON 

INSERT [dbo].[tbl_DiscountCode] ([discountCodeID], [discountCode], [discountPercent], [expiryDate]) VALUES (1, N'SE130068', 10, CAST(N'2022-10-10' AS Date))
INSERT [dbo].[tbl_DiscountCode] ([discountCodeID], [discountCode], [discountPercent], [expiryDate]) VALUES (2, N'SE130130', 20, CAST(N'2022-10-15' AS Date))
INSERT [dbo].[tbl_DiscountCode] ([discountCodeID], [discountCode], [discountPercent], [expiryDate]) VALUES (3, N'SE130150', 15, CAST(N'2022-09-30' AS Date))
SET IDENTITY_INSERT [dbo].[tbl_DiscountCode] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_Hotel] ON 

INSERT [dbo].[tbl_Hotel] ([hotelID], [hotelName]) VALUES (1, N'Yen Hoang Hotel')
INSERT [dbo].[tbl_Hotel] ([hotelID], [hotelName]) VALUES (2, N'Anh Ba Hotel')
INSERT [dbo].[tbl_Hotel] ([hotelID], [hotelName]) VALUES (3, N'Thu Huyen Hotel')
INSERT [dbo].[tbl_Hotel] ([hotelID], [hotelName]) VALUES (4, N'Balas Hotel')
SET IDENTITY_INSERT [dbo].[tbl_Hotel] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_QualityRating] ON 

INSERT [dbo].[tbl_QualityRating] ([ratingID], [roomID], [userInfoID], [feedback], [ratingScore]) VALUES (5, 3, 1, N'Good Room', 8)
INSERT [dbo].[tbl_QualityRating] ([ratingID], [roomID], [userInfoID], [feedback], [ratingScore]) VALUES (6, 7, 1, N'Nice room!', 7)
SET IDENTITY_INSERT [dbo].[tbl_QualityRating] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_Room] ON 

INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (1, 1, 1, CAST(N'2022-09-25' AS Date), CAST(N'2022-09-30' AS Date), 10, 10, 200)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (3, 1, 2, CAST(N'2022-09-20' AS Date), CAST(N'2022-09-25' AS Date), 10, 10, 350)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (4, 1, 3, CAST(N'2022-09-20' AS Date), CAST(N'2022-09-23' AS Date), 10, 10, 500)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (5, 2, 1, CAST(N'2022-09-27' AS Date), CAST(N'2022-10-05' AS Date), 20, 15, 150)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (7, 2, 2, CAST(N'2022-09-28' AS Date), CAST(N'2022-10-03' AS Date), 20, 15, 275)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (8, 2, 3, CAST(N'2022-09-29' AS Date), CAST(N'2022-10-05' AS Date), 20, 20, 450)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (9, 3, 1, CAST(N'2022-09-27' AS Date), CAST(N'2022-10-03' AS Date), 15, 15, 175)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (10, 3, 2, CAST(N'2022-09-30' AS Date), CAST(N'2022-10-10' AS Date), 15, 15, 300)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (11, 3, 3, CAST(N'2022-09-27' AS Date), CAST(N'2022-10-05' AS Date), 15, 15, 525)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (12, 4, 1, CAST(N'2022-10-06' AS Date), CAST(N'2022-10-13' AS Date), 5, 5, 250)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (13, 4, 2, CAST(N'2022-10-08' AS Date), CAST(N'2022-10-15' AS Date), 10, 10, 400)
INSERT [dbo].[tbl_Room] ([roomID], [hotelID], [roomTypeID], [checkInDate], [checkOutDate], [totalRoom], [availableRoom], [price]) VALUES (14, 4, 3, CAST(N'2022-10-10' AS Date), CAST(N'2022-10-20' AS Date), 10, 10, 550)
SET IDENTITY_INSERT [dbo].[tbl_Room] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_RoomType] ON 

INSERT [dbo].[tbl_RoomType] ([id], [name]) VALUES (1, N'single')
INSERT [dbo].[tbl_RoomType] ([id], [name]) VALUES (2, N'double')
INSERT [dbo].[tbl_RoomType] ([id], [name]) VALUES (3, N'family')
SET IDENTITY_INSERT [dbo].[tbl_RoomType] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_User] ON 

INSERT [dbo].[tbl_User] ([userID], [email], [password], [roleID], [statusID]) VALUES (2, N'huinguyen.edu@gmail.com', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 0)
INSERT [dbo].[tbl_User] ([userID], [email], [password], [roleID], [statusID]) VALUES (3, N'darkng@gmail.com', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 0, 0)
INSERT [dbo].[tbl_User] ([userID], [email], [password], [roleID], [statusID]) VALUES (4, N'huynhp@gmail.com', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 0)
INSERT [dbo].[tbl_User] ([userID], [email], [password], [roleID], [statusID]) VALUES (15, N'huynhp.pv@gmail.com', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 0)
INSERT [dbo].[tbl_User] ([userID], [email], [password], [roleID], [statusID]) VALUES (16, N'huynhpse130068@fpt.edu.vn', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 0)
SET IDENTITY_INSERT [dbo].[tbl_User] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_UserInfo] ON 

INSERT [dbo].[tbl_UserInfo] ([userInfoID], [userID], [phone], [name], [address], [createdDate]) VALUES (1, 2, N'0949724899', N'Huy Nguyen', N'35 TKX P7 QPN', CAST(N'2022-09-25' AS Date))
INSERT [dbo].[tbl_UserInfo] ([userInfoID], [userID], [phone], [name], [address], [createdDate]) VALUES (2, 3, N'0978243582', N'Admin', N'4 HHT P13 QBT', CAST(N'2022-09-28' AS Date))
INSERT [dbo].[tbl_UserInfo] ([userInfoID], [userID], [phone], [name], [address], [createdDate]) VALUES (3, 4, N'0949724899', N'Huy Nguyen', N'TKX P7 QPN', CAST(N'2022-09-28' AS Date))
INSERT [dbo].[tbl_UserInfo] ([userInfoID], [userID], [phone], [name], [address], [createdDate]) VALUES (14, 15, N'0949724899', N'Huy Nguyen', N'TKX P7 QPN', CAST(N'2022-09-28' AS Date))
INSERT [dbo].[tbl_UserInfo] ([userInfoID], [userID], [phone], [name], [address], [createdDate]) VALUES (15, 16, N'0949724899', N'HuyNHP', N'TKX P7 QPN', CAST(N'2022-10-04' AS Date))
SET IDENTITY_INSERT [dbo].[tbl_UserInfo] OFF
GO
ALTER TABLE [dbo].[tbl_Booking]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Booking_tbl_DiscountCode] FOREIGN KEY([discountCodeID])
REFERENCES [dbo].[tbl_DiscountCode] ([discountCodeID])
GO
ALTER TABLE [dbo].[tbl_Booking] CHECK CONSTRAINT [FK_tbl_Booking_tbl_DiscountCode]
GO
ALTER TABLE [dbo].[tbl_Booking]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Booking_tbl_Room] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_Booking] CHECK CONSTRAINT [FK_tbl_Booking_tbl_Room]
GO
ALTER TABLE [dbo].[tbl_Booking]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Booking_tbl_UserInfo] FOREIGN KEY([userInfoID])
REFERENCES [dbo].[tbl_UserInfo] ([userInfoID])
GO
ALTER TABLE [dbo].[tbl_Booking] CHECK CONSTRAINT [FK_tbl_Booking_tbl_UserInfo]
GO
ALTER TABLE [dbo].[tbl_QualityRating]  WITH CHECK ADD  CONSTRAINT [FK_tbl_QualityRating_tbl_Room] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_QualityRating] CHECK CONSTRAINT [FK_tbl_QualityRating_tbl_Room]
GO
ALTER TABLE [dbo].[tbl_QualityRating]  WITH CHECK ADD  CONSTRAINT [FK_tbl_QualityRating_tbl_UserInfo1] FOREIGN KEY([userInfoID])
REFERENCES [dbo].[tbl_UserInfo] ([userInfoID])
GO
ALTER TABLE [dbo].[tbl_QualityRating] CHECK CONSTRAINT [FK_tbl_QualityRating_tbl_UserInfo1]
GO
ALTER TABLE [dbo].[tbl_Room]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Room_tbl_Hotel] FOREIGN KEY([hotelID])
REFERENCES [dbo].[tbl_Hotel] ([hotelID])
GO
ALTER TABLE [dbo].[tbl_Room] CHECK CONSTRAINT [FK_tbl_Room_tbl_Hotel]
GO
ALTER TABLE [dbo].[tbl_Room]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Room_tbl_RoomType] FOREIGN KEY([roomTypeID])
REFERENCES [dbo].[tbl_RoomType] ([id])
GO
ALTER TABLE [dbo].[tbl_Room] CHECK CONSTRAINT [FK_tbl_Room_tbl_RoomType]
GO
ALTER TABLE [dbo].[tbl_UserInfo]  WITH CHECK ADD  CONSTRAINT [FK_tbl_UserInfo_tbl_User1] FOREIGN KEY([userID])
REFERENCES [dbo].[tbl_User] ([userID])
GO
ALTER TABLE [dbo].[tbl_UserInfo] CHECK CONSTRAINT [FK_tbl_UserInfo_tbl_User1]
GO
USE [master]
GO
ALTER DATABASE [SE1612_HotelBooking] SET  READ_WRITE 
GO
