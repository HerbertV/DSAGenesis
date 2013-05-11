/*
	TableIndex
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
CREATE TABLE [TableIndex] (
	[ID] INTEGER  PRIMARY KEY NOT NULL,
	[ti_name] VARCHAR(20)  NOT NULL,
	[ti_prefix] VARCHAR(3)  NOT NULL,
	[ti_last_index_num] INTEGER DEFAULT '-1' NOT NULL,
	[ti_label] TEXT  NOT NULL,
	[ti_note] TEXT  NULL,
	[ti_editable] BOOLEAN DEFAULT 'true' NOT NULL
);