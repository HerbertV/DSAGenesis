/*
-----------------------------------------------------------------
	ScriptTriggers
-----------------------------------------------------------------
	Contains Script links for special events.
	such as adding/removing a specific item in the hero editor.
*/
DROP TABLE IF EXISTS "ScriptTriggers";

CREATE TABLE "ScriptTriggers" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"sctr_table_name" VARCHAR(50) NOT NULL,
	"sctr_ref_id" VARCHAR(10) NOT NULL,
	"sctr_event" VARCHAR(20) NOT NULL,
	"sctr_script" VARCHAR(50) NOT NULL
);

/*
	ScriptTriggers Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'ScriptTriggers', 
		0,
		'sctr_', 
		'Script Auslöser', 
		'Hier werden Auslöser definiert die externe scripts aufrufen.',
		9,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ScriptTriggers', 'sctr_table_name', 'Tabelle');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ScriptTriggers', 'sctr_ref_id', 'Datensatz');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ScriptTriggers', 'sctr_event', 'Ereignis');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ScriptTriggers', 'sctr_script', 'Skript');	
