----------------------------------------
-- Author: Afonso Gomes              --
-- Since: 06/11/2024                  --
----------------------------------------
-- Process table login_register  --
----------------------------------------

CREATE TABLE [dbo].[dw_production_unit](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](20) NOT NULL,
	[code] [varchar](14) NOT NULL,
	[abbreviation] [varchar](12) NOT NULL,
	[production_unit_id_parent] [int] NULL,
	[user_id_insert] [bigint] NOT NULL,
	[date_insert] DATETIMEOFFSET(7) NOT NULL,
	[user_id_update] [bigint] NULL,
	[date_update] DATETIMEOFFSET(7) NULL,
	[access_key] [varchar](36) UNIQUE NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]