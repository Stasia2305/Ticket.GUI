/*
    Ticket System database bootstrap script for Microsoft SQL Server.

    Run with:
    sqlcmd -S localhost,1433 -U sa -P <your_password> -i database/create-ticket-system.sql
*/

IF DB_ID(N'TicketSystem') IS NULL
BEGIN
    CREATE DATABASE TicketSystem;
END;
GO

USE TicketSystem;
GO

IF OBJECT_ID(N'dbo.tickets', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.tickets;
END;
GO

IF OBJECT_ID(N'dbo.vouchers', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.vouchers;
END;
GO

IF OBJECT_ID(N'dbo.events', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.events;
END;
GO

IF OBJECT_ID(N'dbo.users', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.users;
END;
GO

CREATE TABLE dbo.users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(100) NOT NULL,
    [password] NVARCHAR(255) NOT NULL,
    [role] NVARCHAR(50) NOT NULL,
    CONSTRAINT UQ_users_username UNIQUE (username),
    CONSTRAINT CHK_users_role CHECK ([role] IN (N'ADMIN', N'EVENT_COORDINATOR'))
);
GO

CREATE TABLE dbo.events (
    id INT IDENTITY(1,1) PRIMARY KEY,
    [name] NVARCHAR(200) NOT NULL,
    start_date_time DATETIME2 NOT NULL,
    end_date_time DATETIME2 NULL,
    [location] NVARCHAR(255) NOT NULL,
    location_guidance NVARCHAR(500) NULL,
    notes NVARCHAR(1000) NULL
);
GO

CREATE TABLE dbo.tickets (
    id INT IDENTITY(1,1) PRIMARY KEY,
    uuid UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
    customer_name NVARCHAR(200) NOT NULL,
    customer_email NVARCHAR(255) NOT NULL,
    event_id INT NOT NULL,
    [type] NVARCHAR(100) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT UQ_tickets_uuid UNIQUE (uuid),
    CONSTRAINT FK_tickets_events FOREIGN KEY (event_id) REFERENCES dbo.events(id)
);
GO

CREATE TABLE dbo.vouchers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    uuid UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
    title NVARCHAR(200) NOT NULL,
    [description] NVARCHAR(1000) NOT NULL,
    event_id INT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT UQ_vouchers_uuid UNIQUE (uuid),
    CONSTRAINT FK_vouchers_events FOREIGN KEY (event_id) REFERENCES dbo.events(id)
);
GO

INSERT INTO dbo.users (username, [password], [role])
VALUES
    (N'admin', N'admin', N'ADMIN'),
    (N'coordinator', N'coordinator', N'EVENT_COORDINATOR');
GO

INSERT INTO dbo.events ([name], start_date_time, end_date_time, [location], location_guidance, notes)
VALUES
    (
        N'EASV Party',
        DATEADD(DAY, 7, SYSDATETIME()),
        DATEADD(HOUR, 6, DATEADD(DAY, 7, SYSDATETIME())),
        N'EASV Campus',
        N'Main hall entrance',
        N'Initial seed event'
    ),
    (
        N'Wine Tasting',
        DATEADD(DAY, 14, SYSDATETIME()),
        DATEADD(HOUR, 3, DATEADD(DAY, 14, SYSDATETIME())),
        N'Esbjerg City Center',
        N'Check in at the front desk',
        N'Initial seed event'
    );
GO

INSERT INTO dbo.tickets (customer_name, customer_email, event_id, [type])
VALUES
    (N'Alice Example', N'alice@example.com', 1, N'STANDARD'),
    (N'Bob Example', N'bob@example.com', 2, N'VIP');
GO

INSERT INTO dbo.vouchers (title, [description], event_id)
VALUES
    (N'Free Beer', N'One-time use at any event', NULL),
    (N'50% Off Drink', N'Valid for Summer Festival', 1),
    (N'Earplugs', N'Safety first!', 2);
GO