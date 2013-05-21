/*
-----------------------------------------------------------------
	Worlds
-----------------------------------------------------------------
	Contains the different Champaign Settings
*/
DROP TABLE IF EXISTS "Worlds";

CREATE TABLE "Worlds" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"w_name" TEXT NOT NULL
);

/*
	Worlds Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'Worlds', 
		1,
		'w_', 
		'Welten / Kampagnen', 
		'Hier sind die einzelenen Welten, Kontinente und Kampangen aufgelistet.',
		0,
		0,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Worlds', 'w_name', 'Name');	


/*
	World Entries
*/
INSERT INTO "Worlds" VALUES('w_av', 'Aventurien');
INSERT INTO "Worlds" VALUES('w_my', 'Myranor');
INSERT INTO "Worlds" VALUES('w_ut', 'Uthuria');
INSERT INTO "Worlds" VALUES('w_th', 'Tharun');
	