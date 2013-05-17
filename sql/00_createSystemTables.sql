/*
-----------------------------------------------------------------
	CoreDataVersion
-----------------------------------------------------------------
	Contains always only one entry with ID=0
	
		ver_major: major version 
		ver_minor: minor version
		ver_language: locale string e.g. "de_DE"
*/
DROP TABLE IF EXISTS "CoreDataVersion";

CREATE TABLE "CoreDataVersion" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ver_major" INTEGER NOT NULL,
	"ver_minor" INTEGER NOT NULL,
	"ver_language" VARCHAR (5)
);

INSERT INTO "CoreDataVersion" VALUES(0, 1, 0, 'de_DE');

/*
-----------------------------------------------------------------
	CoreDataTableIndex
-----------------------------------------------------------------
	contains an entry for each table.
	
		ti_name: internal name of the table 
	
	for generating a db wide unique id:
		ti_uses_prefix: if false the id is a pure integer.
		ti_prefix: prefix for the ID of the referenced table
		ti_last_index_num: last index used for this table
	
		ti_label: the label used by JTabPane from core editor
		ti_note: an optional note also displayed in the core editor
		ti_is_internal: if true the table is displayed in Internal section.
		ti_tab_index: the index postion for the tab panel.
		ti_editable: to flag a table read only used for internal tables
			that only contain cross references.
*/
DROP TABLE IF EXISTS "CoreDataTableIndex";

CREATE TABLE "CoreDataTableIndex" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ti_table_name" VARCHAR(50) NOT NULL,
	"ti_uses_prefix" BOOLEAN DEFAULT 'true' NOT NULL,
	"ti_prefix" VARCHAR(5),
	"ti_last_index_num" INTEGER DEFAULT -1 NOT NULL,
	"ti_label" TEXT NOT NULL,
	"ti_note" TEXT NULL,
	"ti_is_internal" BOOLEAN DEFAULT 'false' NOT NULL,
	"ti_tab_index" INTEGER DEFAULT '0' NOT NULL,
	"ti_editable" BOOLEAN DEFAULT 'true' NOT NULL
);

/*
-----------------------------------------------------------------
	TableColumnLabels
-----------------------------------------------------------------
	contains the labels shown in core editor for each column.
	internal tables not allways use colum labels
	
		tcl_ref_ti_ID: references the id to CoreDataTableIndex table
		tcl_column_name: internal DB name of the column.
		tcl_label: the label shown in core editor
*/
DROP TABLE IF EXISTS "TableColumnLabels";

CREATE TABLE "TableColumnLabels" (
	"ID" INTEGER  PRIMARY KEY NOT NULL,
	"tcl_table_name" VARCHAR(50) NOT NULL,
	"tcl_column_name" VARCHAR(50) NOT NULL,
	"tcl_label" TEXT  NOT NULL
);
