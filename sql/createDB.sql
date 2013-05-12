/*
-----------------------------------------------------------------
	creates all tables for DSA Genesis
-----------------------------------------------------------------
*/

/*
-----------------------------------------------------------------
	CoreDataTableIndex
-----------------------------------------------------------------
	contains an entry for each table.
	
		ti_name: internal name of the table 
	
	for generating a db wide unique id:
		ti_prefix: prefix for the ID of the referenced table
		ti_last_index_num: last index used for this table
	
		ti_label: the label used by JTabPane from core editor
		ti_note: an optional note also displayed in the core editor
		ti_editable: to flag a table read only used for internal tables
			that only contain cross references.
*/
DROP TABLE IF EXISTS [CoreDataTableIndex];

CREATE TABLE [CoreDataTableIndex] (
	[ID] INTEGER  PRIMARY KEY AUTOINCREMENT NULL,
	[ti_table_name] VARCHAR(50)  NOT NULL,
	[ti_prefix] VARCHAR(4),
	[ti_last_index_num] INTEGER DEFAULT -1 NOT NULL,
	[ti_label] TEXT  NOT NULL,
	[ti_note] TEXT  NULL,
	[ti_editable] BOOLEAN DEFAULT 'true' NOT NULL
);

/*
-----------------------------------------------------------------
	TableColumnLabels
-----------------------------------------------------------------
	contains the labels shown in core editor for each column.
	
		tcl_ref_ti_ID: references the id to CoreDataTableIndex table
		tcl_column_name: internal DB name of the column.
		tcl_label: the label shown in core editor
*/
DROP TABLE IF EXISTS [TableColumnLabels];

CREATE TABLE [TableColumnLabels] (
	[ID] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,
	[tcl_table_name] VARCHAR(50)  NOT NULL,
	[tcl_column_name] VARCHAR(50)  NOT NULL,
	[tcl_label] TEXT  NOT NULL
);

/*
-----------------------------------------------------------------
	SKT
-----------------------------------------------------------------
	SKT short for "Steigerungskosten-Tabelle".
	
	see: http://www.wiki-aventurica.de/wiki/SKT
*/
DROP TABLE IF EXISTS [SKT];

CREATE TABLE [SKT] (
	[ID] INTEGER  PRIMARY KEY NOT NULL,
	[skt_a_star] INTEGER NOT NULL,
	[skt_a] INTEGER NOT NULL,
	[skt_b] INTEGER NOT NULL,
	[skt_c] INTEGER NOT NULL,
	[skt_d] INTEGER NOT NULL,
	[skt_e] INTEGER NOT NULL,
	[skt_f] INTEGER NOT NULL,
	[skt_g] INTEGER NOT NULL,
	[skt_h] INTEGER NOT NULL
);



