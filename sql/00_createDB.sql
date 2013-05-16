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

/*
-----------------------------------------------------------------
	SKT
-----------------------------------------------------------------
	SKT short for "Steigerungskosten-Tabelle".
	
	see: http://www.wiki-aventurica.de/wiki/SKT
	
	A*, A** and beyond:
	for every * cost is reduced by 2 point but minimum is 1.
	
*/
DROP TABLE IF EXISTS "SKT";

CREATE TABLE "SKT" (
	"ID" INTEGER  PRIMARY KEY NOT NULL,
	"skt_a" INTEGER NOT NULL,
	"skt_b" INTEGER NOT NULL,
	"skt_c" INTEGER NOT NULL,
	"skt_d" INTEGER NOT NULL,
	"skt_e" INTEGER NOT NULL,
	"skt_f" INTEGER NOT NULL,
	"skt_g" INTEGER NOT NULL,
	"skt_h" INTEGER NOT NULL
);

/*
-----------------------------------------------------------------
	SKTShifts
-----------------------------------------------------------------
	Advantages/Disadvantages special abities can modify the
	SKT column for a talent/spell etc. 
	This is called skt shift.
	
	If a single source shifts more than one targets
	you will need multiple entries here.
	
		skts_ref_source_ID: the source DB entry that triggers the shift
		skts_target_table_name: the targets table
		skts_target_column_name: any column that references an ID.
			e.g. "ID" itself or "c_ref_cg_ID"
		skts_target_value: the ID 
		
	shifting options:
		
		skts_is_down_shift: 
		skts_is_up_shift:
		skts_shift_factor: determinates how many times a shift
			is applied to the target in most cases 1
		skts_is_absolute_shift:
		skts_skt_column: if the shift is absolute you can reference 
			the SKT column here
	
*/
DROP TABLE IF EXISTS "SKTShifts";

CREATE TABLE "SKTShifts" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"skts_ref_source_ID" VARCHAR(10) NOT NULL,
	"skts_target_table_name" VARCHAR(50) NOT NULL,
	"skts_target_column_name" VARCHAR(50) NULL,
	"skts_target_value" VARCHAR(10) NULL,
	"skts_is_down_shift" BOOLEAN DEFAULT 'true' NOT NULL,
	"skts_is_up_shift" BOOLEAN DEFAULT 'false' NOT NULL,
	"skts_shift_factor" INTEGER DEFAULT '0' NOT NULL,
	"skts_is_absolute_shift" BOOLEAN DEFAULT 'false' NOT NULL,
	"skts_skt_column" VARCHAR(10) DEFAULT '' NULL
);

/*
-----------------------------------------------------------------
	CharacteristicGroups
-----------------------------------------------------------------
			
*/
DROP TABLE IF EXISTS "CharacteristicGroups";

CREATE TABLE "CharacteristicGroups" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"cg_name" TEXT NOT NULL
);

/*
-----------------------------------------------------------------
	Characteristics
-----------------------------------------------------------------
	contains Attributes and anything else that is leved or calculated
		
		c_priorty: is the sort order for displaying the entries.
		c_acronym: short name
		c_name: name
		c_ref_cg_ID: reference to CharacteristicGroups
		c_cp_cost: creation point cost per characteristic point
			during the hero creation.
	
	for characteristics the can leveled:
		
		c_min_value: minimum value 
		c_max_value: maximum value
		c_can_increase: is allowed to increase (all attributes)
		c_can_decrease: is allowed to decrease (e.g. Sozialstatus)
		c_skt_column: reference to skt column 
			if null the cost is free or calculated otherwise.
		c_has_Formular:
		c_formular:		
*/
DROP TABLE IF EXISTS "Characteristics";

CREATE TABLE "Characteristics" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"c_priorty" INTEGER DEFAULT '0' NOT NULL,
	"c_acronym" VARCHAR(5) NOT NULL,
	"c_name" TEXT NOT NULL,
	"c_ref_cg_ID" VARCHAR(10) REFERENCES CharacteristicGroups(ID) NOT NULL,
	"c_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"c_min_value" INTEGER NULL,
	"c_max_value" INTEGER NULL,
	"c_can_increase" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_can_decrease" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_skt_column" VARCHAR(10) DEFAULT '' NULL,
	"c_has_Formular" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_formular" TEXT NULL
);



