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
		'true',
		'rcg_', 
		'Volks-/Kulturgruppen', 
		'<html>Die einzelnen Rassen sind gruppiert um sie in Unterverzeichnisse aufzuteilen.<br>Die Vezeichnisse gelten sowohl für die Rassen als auch für Kulturen.</html>',
		'true',
		0,
		'true'
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

--TODO

-- TODO ProfessionCategories ( Normal, Vollzauberer, Halbzauberer ...)
-- TODO ProfessionTypes ( Normal, zeitaufwändig, erstproffesion... )

