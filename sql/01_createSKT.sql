/*
-----------------------------------------------------------------
	SKT
-----------------------------------------------------------------
	SKT short for "Steigerungskosten-Tabelle".
	
	see: http://www.wiki-aventurica.de/wiki/SKT
	
	A*, A** and beyond:
	for every * cost is reduced by 2 point but minimum is 1.
	
	used by:
	dsagenesis.core.model.SKTMatrix
	dsagenesis.core.model.sql.SKT
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
	"skts_is_down_shift" BOOLEAN DEFAULT '1' NOT NULL,
	"skts_is_up_shift" BOOLEAN DEFAULT '0' NOT NULL,
	"skts_shift_factor" INTEGER DEFAULT '0' NOT NULL,
	"skts_is_absolute_shift" BOOLEAN DEFAULT 'false' NOT NULL,
	"skts_skt_column" VARCHAR(10) NULL
);

/*
	SKT Index
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	( 	'SKT', 
		0,
		'skt_', 
		'SKT', 
		'Steigerungskosten-Tabelle',
		0,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_a', 'A');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_b', 'B');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_c', 'C');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_d', 'D');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_e', 'E');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_f', 'F');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_g', 'G');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKT', 'skt_h', 'H');
	

/*
	SKTShifts Index
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'SKTShifts', 
		0,
		'skts_', 
		'SKT Verschiebungen', 
		'Hier werden die Verschiebungen der SKT Spalten durch Vor-/Nachteile und Sonderfähigkeiten verwaltet.',
		1,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_ref_source_ID', 'Quelle');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_target_table_name', 'Ziel Tabelle');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_target_column_name', 'Ziel Spalte');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_target_value', 'Ziel Wert');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_is_down_shift', 'Shift Links');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_is_up_shift', 'Shift Rechts');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_shift_factor', 'Faktor');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_is_absolute_shift', 'Ist Absolut');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_skt_column', 'SKT Spalte');	

/*
-----------------------------------------------------------------
	SKT Entries
-----------------------------------------------------------------
*/
INSERT INTO "SKT" VALUES(0, 5, 10, 15, 20, 25, 40, 50, 100);
INSERT INTO "SKT" VALUES(1, 1, 2, 2, 3, 4, 6, 8, 16);
INSERT INTO "SKT" VALUES(2, 2, 4, 6, 7, 9, 14, 18, 35);
INSERT INTO "SKT" VALUES(3, 3, 6, 9, 12, 15, 22, 30, 60);
INSERT INTO "SKT" VALUES(4, 4, 8, 13, 17, 21, 32, 42, 85);
INSERT INTO "SKT" VALUES(5, 6, 11, 17, 22, 28, 41, 55, 110);
INSERT INTO "SKT" VALUES(6, 7, 14, 21, 27, 34, 50, 70, 140);
INSERT INTO "SKT" VALUES(7, 8, 17, 25, 33, 41, 60, 85, 165);
INSERT INTO "SKT" VALUES(8, 10, 19, 29, 39, 48, 75, 95, 195);
INSERT INTO "SKT" VALUES(9, 11, 22, 34, 45, 55, 85, 110, 220);
INSERT INTO "SKT" VALUES(10, 13, 25, 38, 50, 65, 95, 125, 250);
INSERT INTO "SKT" VALUES(11, 14, 28, 43, 55, 70, 105, 140, 280);
INSERT INTO "SKT" VALUES(12, 16, 32, 47, 65, 80, 120, 160, 320);
INSERT INTO "SKT" VALUES(13, 17, 35, 51, 70, 85, 130, 175, 350);
INSERT INTO "SKT" VALUES(14, 19, 38, 55, 75, 95, 140, 190, 380);
INSERT INTO "SKT" VALUES(15, 21, 41, 60, 85, 105, 155, 210, 410);
INSERT INTO "SKT" VALUES(16, 22, 45, 65, 90, 110, 165, 220, 450);
INSERT INTO "SKT" VALUES(17, 24, 48, 70, 95, 120, 180, 240, 480);
INSERT INTO "SKT" VALUES(18, 26, 51, 75, 105, 130, 195, 260, 510);
INSERT INTO "SKT" VALUES(19, 27, 55, 80, 110, 135, 210, 270, 550);
INSERT INTO "SKT" VALUES(20, 29, 58, 85, 115, 145, 220, 290, 580);
INSERT INTO "SKT" VALUES(21, 31, 62, 95, 125, 155, 230, 310, 620);
INSERT INTO "SKT" VALUES(22, 33, 65, 100, 130, 165, 250, 330, 650);
INSERT INTO "SKT" VALUES(23, 34, 69, 105, 140, 170, 260, 340, 690);
INSERT INTO "SKT" VALUES(24, 36, 73, 110, 145, 180, 270, 360, 720);
INSERT INTO "SKT" VALUES(25, 38, 76, 115, 150, 190, 290, 380, 760);
INSERT INTO "SKT" VALUES(26, 40, 80, 120, 160, 200, 300, 400, 800);
INSERT INTO "SKT" VALUES(27, 42, 84, 125, 165, 210, 310, 420, 830);
INSERT INTO "SKT" VALUES(28, 44, 87, 130, 170, 220, 330, 440, 870);
INSERT INTO "SKT" VALUES(29, 45, 91, 135, 180, 230, 340, 460, 910);
INSERT INTO "SKT" VALUES(30, 47, 95, 140, 190, 240, 350, 480, 950);
INSERT INTO "SKT" VALUES(31, 50, 100, 150, 200, 250, 375, 500, 1000);
	