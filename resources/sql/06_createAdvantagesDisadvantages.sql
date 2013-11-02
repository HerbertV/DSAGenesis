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
	"ada_has_selection" BOOLEAN DEFAULT '0' NOT NULL,
	"ada_selection" TEXT DEFAULT NULL,
	"ada_has_modifier" BOOLEAN DEFAULT '0' NOT NULL,
	"ada_modifier_formula" TEXT DEFAULT NULL,
	"ada_bookmark" TEXT DEFAULT NULL,
	"ada_note" TEXT DEFAULT NULL
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
		'Beinhaltet alle Vorteile (ausser Gaben) die bei der Heldenerschaffung gewählt werden können.<br>Ist ein Vorteil nicht frei Wählbar kann er nur über eine Rasse, Kultur oder Profession erhalten werden.',
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
	( 'Advantages', 'ada_is_arbitrary', 'frei Wählbar');	
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
	( 'Advantages', 'ada_has_selection', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_selection', 'Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_has_modifier', 'hat Modifikator');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_modifier_formula', 'Modifikator Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Advantages', 'ada_note', 'Bemerkung');	

/*
	Junction Table 
 */
DROP TABLE IF EXISTS "Junct_ProfessionCategories_Advantages";

CREATE TABLE "Junct_ProfessionCategories_Advantages" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL,
	"ref_ada_ID" VARCHAR(10) REFERENCES Advantages(ID) NOT NULL
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
	"add_is_removable" BOOLEAN DEFAULT '0' NOT NULL,
	"add_remove_ap_cost" INTEGER DEFAULT '0' NOT NULL,
	"add_has_level" BOOLEAN DEFAULT '0' NOT NULL,
	"add_step_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"add_min_level" INTEGER DEFAULT '0' NOT NULL,
	"add_max_level" INTEGER DEFAULT '0' NOT NULL,
	"add_has_selection" BOOLEAN DEFAULT '0' NOT NULL,
	"add_selection" TEXT DEFAULT NULL,
	"add_has_modifier" BOOLEAN DEFAULT '0' NOT NULL,
	"add_modifier_formula" TEXT DEFAULT NULL,
	"add_bookmark" TEXT DEFAULT NULL,
	"add_note" TEXT DEFAULT NULL
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
		'Beinhaltet alle Nachteile (ausser Schlechte Eigenschaften) die bei der Heldenerschaffung gewählt werden können.<br>Ist ein Nachteil nicht frei Wählbar kann er nur über eine Rasse, Kultur oder Profession erhalten werden.',
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
	( 'Disadvantages', 'add_is_arbitrary', 'frei Wählbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_cp_cost', 'GP (werden frei)');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_name_suffix', 'Name Anhang');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_is_removable', 'Entfernbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_remove_ap_cost', 'entf. AP Kosten');	
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
	( 'Disadvantages', 'add_has_selection', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_selection', 'Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_has_modifier', 'hat Modifikator');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_modifier_formula', 'Modifikator Formel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Disadvantages', 'add_note', 'Bemerkung');	

/*
	Junction Table 
 */
DROP TABLE IF EXISTS "Junct_ProfessionCategories_Disadvantages";

CREATE TABLE "Junct_ProfessionCategories_Disadvantage" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL,
	"ref_add_ID" VARCHAR(10) REFERENCES Disadvantages(ID) NOT NULL
);


/*
-----------------------------------------------------------------
	NegativeAttributes
-----------------------------------------------------------------
	adna_is_arbitrary: if true the negative attribute can be choosen 
			during the hero creation. If false it can be only
			aquired by meta data.
			
*/
DROP TABLE IF EXISTS "NegativeAttributes";

CREATE TABLE "NegativeAttributes" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"adna_name" TEXT NOT NULL,
	"adna_is_arbitrary" BOOLEAN DEFAULT '1' NOT NULL,
	"adna_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"adna_step_size" REAL DEFAULT '1.0' NOT NULL,
	"adna_min_level" INTEGER DEFAULT '0' NOT NULL,
	"adna_max_level" INTEGER DEFAULT '0' NOT NULL,
	"adna_is_reduceable" BOOLEAN DEFAULT '0' NOT NULL,
	"adna_reduce_ap_cost" INTEGER DEFAULT '0' NOT NULL,
	"adna_has_selection" BOOLEAN DEFAULT '0' NOT NULL,
	"adna_selection" TEXT DEFAULT NULL,
	"adna_bookmark" TEXT DEFAULT NULL,
	"adna_note" TEXT DEFAULT NULL
);

/*
	NegativeAttributes Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'NegativeAttributes', 
		1,
		'adna_', 
		'Schlechte Eigenschaften', 
		'Schlechte Eigenschaften die bei der Heldenerschaffung gewählt werden können.<br>Ist eine Eigenschaft nicht frei Wählbar kann sie nur über eine Rasse, Kultur oder Profession erhalten werden.',
		12,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_is_arbitrary', 'frei Wählbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_cp_cost', 'GP pro Punkt (werden frei)');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_step_size', 'Step');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_min_level', 'Min. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_max_level', 'Max. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_is_reduceable', 'Senkbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_reduce_ap_cost', 'Senkungskosten');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_has_selection', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_selection', 'Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'NegativeAttributes', 'adna_note', 'Bemerkung');	
	
/*
	Junction Table 
 */
DROP TABLE IF EXISTS "Junct_ProfessionCategories_NegativeAttributes";

CREATE TABLE "Junct_ProfessionCategories_NegativeAttributes" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL,
	"ref_adna_ID" VARCHAR(10) REFERENCES NegativeAttributes(ID) NOT NULL
);

/*
-----------------------------------------------------------------
	Gifts
-----------------------------------------------------------------
	adg_is_arbitrary: if true the gift can be choosen 
			during the hero creation. If false it can be only
			aquired by meta data.
			
*/
DROP TABLE IF EXISTS "Gifts";

CREATE TABLE "Gifts" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"adg_name" TEXT NOT NULL,
	"adg_is_arbitrary" BOOLEAN DEFAULT '1' NOT NULL,
	"adg_cp_cost" INTEGER DEFAULT '0' NOT NULL,	
	"adg_skt_column" VARCHAR(10) NULL,
	"adg_attribute1" VARCHAR(10) NULL,
	"adg_attribute2" VARCHAR(10) NULL,
	"adg_attribute3" VARCHAR(10) NULL,
	"adg_min_level" INTEGER DEFAULT '0' NOT NULL,
	"adg_has_selection" BOOLEAN DEFAULT '0' NOT NULL,
	"adg_selection" TEXT DEFAULT NULL,
	"adg_bookmark" TEXT DEFAULT NULL,
	"adg_note" TEXT DEFAULT NULL
);

/*
	Gifts Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'Gifts', 
		1,
		'adg_', 
		'Gaben', 
		'TODO',
		13,
		1
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_is_arbitrary', 'frei Wählbar');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_cp_cost', 'GP Kosten');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_skt_column', 'SKT Spalte');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_attribute1', 'Eigenschaft 1');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_attribute2', 'Eigenschaft 2');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_attribute3', 'Eigenschaft 3');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_min_level', 'Min. Stufe');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_has_selection', 'hat Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_selection', 'Auswahl');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_bookmark', 'Buch/Seite');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Gifts', 'adg_note', 'Bemerkung');	
	
/*
	Junction Table 
 */
DROP TABLE IF EXISTS "Junct_ProfessionCategories_Gifts";

CREATE TABLE "Junct_ProfessionCategories_Gifts" (
	"ID" INTEGER PRIMARY KEY NOT NULL,
	"ref_pc_ID"  VARCHAR(10) REFERENCES ProfessionCategories(ID) NOT NULL,
	"ref_adg_ID" VARCHAR(10) REFERENCES Gifts(ID) NOT NULL
);

