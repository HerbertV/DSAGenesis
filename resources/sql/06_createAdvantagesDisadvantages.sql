/*
-----------------------------------------------------------------
	Advantages
-----------------------------------------------------------------
	ada_is_arbitrary: if true the advantage can be choosen 
			during the hero creation. If false it can be only
			aquired by meta data.
	ada_name_suffix: is used if the advantage a choice.
			
*/
DROP TABLE IF EXISTS "Advantages";

CREATE TABLE "Advantages" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"ada_name" TEXT NOT NULL,
	"ada_is_arbitrary" BOOLEAN DEFAULT '1' NOT NULL,
	"ada_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"ada_name_suffix" TEXT DEFAULT NULL,
	"ada_has_level" BOOLEAN DEFAULT '0' NOT NULL,
	"ada_step_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"ada_min_level" INTEGER DEFAULT '0' NOT NULL,
	"ada_max_level" INTEGER DEFAULT '0' NOT NULL,
	"ada_has_choice" BOOLEAN DEFAULT '0' NOT NULL,
	"ada_choice_formular" TEXT DEFAULT NULL,
	"ada_has_modifier" BOOLEAN DEFAULT '0' NOT NULL,
	"ada_modifier_formular" TEXT DEFAULT NULL,
	"ada_bookmark" TEXT DEFAULT NULL,
	"ada_note" TEXT DEFAULT NULL,
);

/*
	Advantages Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'Advantages', 
		1,
		'ada_', 
		'Vorteile', 
		'Beinhaltet alle Vorteile (ausser Gaben) die bei der Heldenerschaffung gewählt werden können.<br>Ist ein Vorteil nicht frei Wählbar kann er nur über eine Rasse,Kultur oder Proffesion erhalten werden.',
		10,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_isArbitrary', 'frei Wählbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_cp_cost', 'GP Kosten');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_name_suffix', 'Name Anhang');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_has_level', 'hat Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_step_cp_cost', 'GP pro Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_min_level', 'Min. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_max_level', 'Max. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_has_choice', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_choice_formular', 'Auswahl Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_has_modifier', 'hat Modifikator');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_modifier_formular', 'Modifikator Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_note', 'Bemerkung');	

/*
	Crossreference Table 
 */
DROP TABLE IF EXISTS "CrossRef_Advantages_ProfessionCategories";

CREATE TABLE "CrossRef_Advantages_ProfessionCategories" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_ada_ID" VARCHAR(10) REFERENCES Advantages(ID) NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL
);


/*
-----------------------------------------------------------------
	Disadvantages
-----------------------------------------------------------------
	add_is_arbitrary: if true the advantage can be choosen 
			during the hero creation. If false it can be only
			aquired by meta data.
	add_name_suffix: is used if the advantage a choice.
			
*/
DROP TABLE IF EXISTS "Disadvantages";

CREATE TABLE "Disadvantages" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"add_name" TEXT NOT NULL,
	"add_is_arbitrary" BOOLEAN DEFAULT '1' NOT NULL,
	"add_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"add_name_suffix" TEXT DEFAULT NULL,
	"add_has_level" BOOLEAN DEFAULT '0' NOT NULL,
	"add_step_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"add_min_level" INTEGER DEFAULT '0' NOT NULL,
	"add_max_level" INTEGER DEFAULT '0' NOT NULL,
	"add_has_choice" BOOLEAN DEFAULT '0' NOT NULL,
	"add_choice_formular" TEXT DEFAULT NULL,
	"add_has_modifier" BOOLEAN DEFAULT '0' NOT NULL,
	"add_modifier_formular" TEXT DEFAULT NULL,
	"add_bookmark" TEXT DEFAULT NULL,
	"add_note" TEXT DEFAULT NULL,
);

/*
	Disadvantages Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'Disadvantages', 
		1,
		'add_', 
		'Nachteile', 
		'Beinhaltet alle Nachteile (ausser Schlechte Eigenschaften) die bei der Heldenerschaffung gewählt werden können.<br>Ist ein Nachteil nicht frei Wählbar kann er nur über eine Rasse,Kultur oder Proffesion erhalten werden.',
		11,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_isArbitrary', 'frei Wählbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_cp_cost', 'GP Kosten');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_name_suffix', 'Name Anhang');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_has_level', 'hat Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_step_cp_cost', 'GP pro Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_min_level', 'Min. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_max_level', 'Max. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_has_choice', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_choice_formular', 'Auswahl Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_has_modifier', 'hat Modifikator');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_modifier_formular', 'Modifikator Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_note', 'Bemerkung');	

/*
	Crossreference Table 
 */
DROP TABLE IF EXISTS "CrossRef_Disadvantages_ProfessionCategories";

CREATE TABLE "CrossRef_Disadvantages_ProfessionCategories" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_add_ID" VARCHAR(10) REFERENCES Disadvantages(ID) NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL
);