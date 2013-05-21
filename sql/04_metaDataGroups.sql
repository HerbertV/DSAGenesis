/*
-----------------------------------------------------------------
	RaceCultureGroups
-----------------------------------------------------------------	
	RaceCultureGroups is used for getting the sub folders
	for races and cultures.	
	
	It is also used by the filtering in the hero creation step.
		
	referenced in meta data by groupID
*/
DROP TABLE IF EXISTS "RaceCultureGroups";

CREATE TABLE "RaceCultureGroups" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"rcg_name" TEXT NOT NULL,
	"rcg_path" VARCHAR(50) NOT NULL
);

/*
	RaceCultureGroups Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'RaceCultureGroups', 
		1,
		'rcg_', 
		'Volks-/Kulturgruppen', 
		'Die einzelnen Rassen sind gruppiert um sie in Unterverzeichnisse aufzuteilen.<br>Die Vezeichnisse gelten sowohl für die Rassen als auch für Kulturen.',
		1,
		3,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'RaceCultureGroups', 'rcg_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'RaceCultureGroups', 'rcg_path', 'Unterverzeichnis');	

/*
	RaceCultureGroups Entries
*/
INSERT INTO "RaceCultureGroups" VALUES('rcg_0', 'Achaz', 'achaz');
INSERT INTO "RaceCultureGroups" VALUES('rcg_1', 'Elf', 'elfs');
INSERT INTO "RaceCultureGroups" VALUES('rcg_2', 'Goblin', 'goblins');
INSERT INTO "RaceCultureGroups" VALUES('rcg_3', 'Mensch', 'humans');
INSERT INTO "RaceCultureGroups" VALUES('rcg_4', 'Ork', 'orcs');
INSERT INTO "RaceCultureGroups" VALUES('rcg_5', 'Zwerg', 'dwarfs');


UPDATE "CoreDataTableIndex" 
	SET "ti_last_index_num"=5
	WHERE "ti_table_name"='RaceCultureGroups';
	
/*
-----------------------------------------------------------------
	ProffesionGroups
-----------------------------------------------------------------	
	ProffesionGroups is used for getting the sub folders
	for professions.
	
	It is also used by the filtering in the hero creation step.
	
	referenced in meta data by groupID
*/
DROP TABLE IF EXISTS "ProfessionGroups";

CREATE TABLE "ProfessionGroups" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"pg_name" TEXT NOT NULL,
	"pg_path" VARCHAR(50) NOT NULL
);	

/*
	ProffesionGroups Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'ProfessionGroups', 
		1,
		'pg_', 
		'Professionsgruppen', 
		'Analog zu den Volks-/Kulturgruppen, wird für Sortierung und Pfaderstellung benötigt.',
		1,
		4,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ProfessionGroups', 'pg_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ProfessionGroups', 'pg_path', 'Unterverzeichnis');	

/*
	ProfessionGroups Entries
*/
INSERT INTO "ProfessionGroups" VALUES('pg_0', 'Elfisch', 'elfs');
INSERT INTO "ProfessionGroups" VALUES('pg_1', 'Gesellschaft', 'society');
INSERT INTO "ProfessionGroups" VALUES('pg_2', 'Glaube', 'religion');
INSERT INTO "ProfessionGroups" VALUES('pg_3', 'Handwerk', 'crafting');
INSERT INTO "ProfessionGroups" VALUES('pg_4', 'Kampf', 'combat');
INSERT INTO "ProfessionGroups" VALUES('pg_5', 'Magie', 'magic');
INSERT INTO "ProfessionGroups" VALUES('pg_6', 'Reise & Wildnis', 'travel_wilderness');
INSERT INTO "ProfessionGroups" VALUES('pg_7', 'Zusatz', 'additional');

UPDATE "CoreDataTableIndex" 
	SET "ti_last_index_num"=7
	WHERE "ti_table_name"='ProfessionGroups';

/*
-----------------------------------------------------------------
	ProfessionCategories
-----------------------------------------------------------------	
	ProfessionCategories is used by several tables
	( e.g. Advantages/Disadvantages Special Skills)
	as crossreference to allow these item only for specific
	profession.
*/
DROP TABLE IF EXISTS "ProfessionCategories";

CREATE TABLE "ProfessionCategories" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"pc_name" TEXT NOT NULL
);	

/*
	RaceCultureGroups Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'ProfessionCategories', 
		1,
		'pc_', 
		'Professionskategorien', 
		'Hier wird die Kategorie der Profession unterschieden, also normal, magisch etc.',
		1,
		5,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ProfessionCategories', 'pc_name', 'Name');	

/*
	ProfessionCategories Entries
*/
INSERT INTO "ProfessionCategories" VALUES('pc_n', 'Normal');
INSERT INTO "ProfessionCategories" VALUES('pc_z', 'Vollzauberer');
INSERT INTO "ProfessionCategories" VALUES('pc_h', 'Halbzauberer');
INSERT INTO "ProfessionCategories" VALUES('pc_v', 'Viertelzauberer');
INSERT INTO "ProfessionCategories" VALUES('pc_s', 'Schamane');
INSERT INTO "ProfessionCategories" VALUES('pc_b', 'Borbaradianer');
INSERT INTO "ProfessionCategories" VALUES('pc_ga', 'Glaube Alvaran');
INSERT INTO "ProfessionCategories" VALUES('pc_gan', 'Glaube Animismus');
INSERT INTO "ProfessionCategories" VALUES('pc_gs', 'Glaube Sonstige');


/*
-----------------------------------------------------------------
	ProfessionTypes
-----------------------------------------------------------------	
	is used for the second profession to validate 
	which professions can be combined
*/
DROP TABLE IF EXISTS "ProfessionTypes";

CREATE TABLE "ProfessionTypes" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"pt_name" TEXT NOT NULL
);	

/*
	ProfessionTypes Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'ProfessionTypes', 
		1,
		'pt_', 
		'Professionstypen', 
		'Wird für die Mehrfachauswahl von Professionen bei der Erschaffung gebraucht.',
		1,
		6,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'ProfessionTypes', 'pt_name', 'Name');	

/*
	ProfessionTypes Entries
*/
INSERT INTO "ProfessionTypes" VALUES('pt_n', 'Normal');
INSERT INTO "ProfessionTypes" VALUES('pt_z', 'Zeitaufwendig');
INSERT INTO "ProfessionTypes" VALUES('pt_f', 'Erstprofession');

