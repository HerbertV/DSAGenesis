/*
-----------------------------------------------------------------
	CharacteristicGroups
-----------------------------------------------------------------	
	The Groups are used to group the Characteristics in
	the edit Charactersitics dialog.		
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
	there are columns for heroes and familiars:
	heroes only columns start with c_h_
	familiar only columns statr with c_f_	
		
		c_priorty: is the sort order for displaying the entries.
		c_acronym: short name
		c_name: name
		c_ref_cg_ID: reference to CharacteristicGroups
		c_cp_cost: creation point cost per characteristic point
			during the hero creation.
	
	for characteristics the can leveled:
		
		...min_value: minimum value 
		...max_value: maximum value
		...can_increase: is allowed to increase (all attributes)
		...can_decrease: is allowed to decrease (e.g. Sozialstatus)
		...skt_column: reference to skt column 
			if null the cost is free or calculated otherwise.
		...has_Formular:
		...formular:		
*/
DROP TABLE IF EXISTS "Characteristics";

CREATE TABLE "Characteristics" (
	"ID" VARCHAR(10) PRIMARY KEY NOT NULL,
	"c_priorty" INTEGER DEFAULT '0' NOT NULL,
	"c_acronym" VARCHAR(5) NOT NULL,
	"c_name" TEXT NOT NULL,
	"c_ref_cg_ID" VARCHAR(10) REFERENCES CharacteristicGroups(ID) NOT NULL,
	-- HERO specific columns
	"c_is_used_by_hero" BOOLEAN DEFAULT 'true' NOT NULL,
	"c_h_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"c_h_min_value" INTEGER NULL,
	"c_h_max_value" INTEGER NULL,
	"c_h_can_increase" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_h_can_decrease" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_h_skt_column" VARCHAR(10) DEFAULT '' NULL,
	"c_h_has_Formular" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_h_formular" TEXT NULL,
	-- Familiar specific columns
	"c_is_used_by_familiar" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_f_min_value" INTEGER NULL,
	"c_f_max_value" INTEGER NULL,
	"c_f_can_increase" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_f_can_decrease" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_f_skt_column" VARCHAR(10) DEFAULT '' NULL
);

/*
	CharacteristicGroups Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note,  ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	(	'CharacteristicGroups', 
		'true',
		'cg_', 
		'Grundwerte Gruppen', 
		'Die Grundwerte sind zur besseren Unterscheidung in Gruppen unterteilt',
		'true',
		1,
		'false'
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'CharacteristicGroups', 'cg_name', 'Name');	
	
/*
	Characteristics Index and Labels
*/		

INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	( 	'Characteristics', 
		'true',
		'c_', 
		'Grundwerte', 
		'<html>Eigenschaften und sonstige Werte die berechnet werden oder gesteigert werden dürfen.<br>Spalten die für Helden verwendet werden sind mit (H) gekennzeichnet.<br>Spalten für Vertraute sind mit (V) gekennzeichnet.</html>',
		'false',
		2,
		'true'
	);

INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_priorty', 'Reihenfolge');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_acronym', 'kz.');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_name', 'Name');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_ref_cg_ID', 'Gruppe');
-- Hero only	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_is_used_by_hero', 'für Helden');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_cp_cost', 'GP(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_min_value', 'Min(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_max_value', 'Max(H)');		
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_can_increase', 'Steigerbar(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_can_decrease', 'Senkbar(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_skt_column', 'SKT Spalte(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_has_Formular', 'hat Formel(H)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_h_formular', 'Berechnungsformel(H)');
-- Famliliar only	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_is_used_by_familiar', 'für Vertraute');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_f_min_value', 'Min(V)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_f_max_value', 'Max(V)');		
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_f_can_increase', 'Steigerbar(V)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_f_can_decrease', 'Senkbar(V)');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_f_skt_column', 'SKT Spalte(V)');

/*
-----------------------------------------------------------------
	CharacteristicGroups Entries
-----------------------------------------------------------------
*/
INSERT INTO "CharacteristicGroups" VALUES('cg_0', 'Eigenschaften');
INSERT INTO "CharacteristicGroups" VALUES('cg_1', 'Energien');
INSERT INTO "CharacteristicGroups" VALUES('cg_2', 'Kampfwerte');
INSERT INTO "CharacteristicGroups" VALUES('cg_3', 'Rüstungswerte');
INSERT INTO "CharacteristicGroups" VALUES('cg_4', 'Sonstige');

UPDATE "CoreDataTableIndex" 
	SET "ti_last_index_num"=4 
	WHERE "ti_table_name"='CharacteristicGroups';

/*
-----------------------------------------------------------------
	Characteristics Entries
-----------------------------------------------------------------
 	ID,  
 	c_priorty, 
 	c_acronym, 
 	c_name,
 	c_ref_cg_ID, 
 	
 	Hero columns:
 	c_is_used_by_hero,
 	c_h_cp_cost, 
 	c_h_min_value,
 	c_h_max_value,
 	c_h_can_increase,
 	c_h_can_decrease,
 	c_h_skt_column,
 	c_h_has_Formular,
 	c_h_formular
 	
 	Familiar columns:
 	c_is_used_by_familiar,
 	c_f_min_value,
 	c_f_max_value,
 	c_f_can_increase,
 	c_f_can_decrease,
 	c_f_skt_column 	
*/
	
INSERT INTO "Characteristics" VALUES(
	'c_mu', 		-- ID
	0, 				-- c_priorty
	'MU', 			-- c_acronym
	'Mut', 			-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_kl', 		-- ID
	1, 				-- c_priorty
	'KL', 			-- c_acronym
	'Klugheit', 	-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_in', 		-- ID
	2, 				-- c_priorty
	'IN', 			-- c_acronym
	'Intuition', 	-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ch', 		-- ID
	3, 				-- c_priorty
	'CH', 			-- c_acronym
	'Charisma', 	-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ff', 		-- ID
	4, 				-- c_priorty
	'FF', 			-- c_acronym
	'Fingerfertigkeit', 	-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ge', 		-- ID
	5, 				-- c_priorty
	'GE', 			-- c_acronym
	'Gewandtheit', 	-- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ko', 		-- ID
	6, 				-- c_priorty
	'KO', 			-- c_acronym
	'Konstitution', -- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_kk', 		-- ID
	7, 				-- c_priorty
	'KK', 			-- c_acronym
	'Körperkraft', -- c_name
	'cg_0', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	30, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_le', 		-- ID
	0, 				-- c_priorty
	'LE', 			-- c_acronym
	'Lebensenergie', -- c_name
	'cg_1', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	50, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h', 		-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_au', 		-- ID
	1, 				-- c_priorty
	'AU', 			-- c_acronym
	'Ausdauer', 	-- c_name
	'cg_1', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	50, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_e', 		-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ae', 		-- ID
	2, 				-- c_priorty
	'AE', 			-- c_acronym
	'Astralenergie', -- c_name
	'cg_1', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	50, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_g', 		-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ke', 		-- ID
	3, 				-- c_priorty
	'KE', 			-- c_acronym
	'Karmanergie', 	-- c_name
	'cg_1', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	50, 			-- c_h_max_value
	'true', 		-- c_h_can_increase
	'true', 		-- c_h_can_decrease
	NULL, 		-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL	 		-- c_f_skt_column
);

INSERT INTO "Characteristics" VALUES(
	'c_at', 		-- ID
	0, 				-- c_priorty
	'AT', 			-- c_acronym
	'Attacke', 		-- c_name
	'cg_2', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_pa', 		-- ID
	1, 				-- c_priorty
	'PA', 			-- c_acronym
	'Parade', 		-- c_name
	'cg_2', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f' 		-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_aw', 		-- ID
	2, 				-- c_priorty
	'AW', 			-- c_acronym
	'Ausweichen', 	-- c_name
	'cg_2', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0,	 			-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_ini', 		-- ID
	3, 				-- c_priorty
	'INI', 			-- c_acronym
	'Initiative',	-- c_name
	'cg_2', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);

INSERT INTO "Characteristics" VALUES(
	'c_rs', 		-- ID
	0, 				-- c_priorty
	'RS', 			-- c_acronym
	'Rüstungsschutz',	-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z1', 		-- ID
	1, 				-- c_priorty
	'RS Kopf', 		-- c_acronym
	'RS Kopf',		-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z2', 		-- ID
	2, 				-- c_priorty
	'RS Brust', 		-- c_acronym
	'RS Brust',		-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z3', 		-- ID
	3, 				-- c_priorty
	'RS Rücken', 		-- c_acronym
	'RS Rücken',		-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z4', 		-- ID
	4, 				-- c_priorty
	'RS Bauch', 		-- c_acronym
	'RS Bauch',		-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z5', 		-- ID
	5, 				-- c_priorty
	'RS Arm L.', 		-- c_acronym
	'RS Arm Links',		-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z6', 		-- ID
	6, 				-- c_priorty
	'RS Arm R.', 	-- c_acronym
	'RS Arm Rechts',-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z7', 		-- ID
	7, 				-- c_priorty
	'RS Bein L.', 	-- c_acronym
	'RS Bein Links',-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z8', 		-- ID
	8, 				-- c_priorty
	'RS Bein R.', 	-- c_acronym
	'RS Bein Rechts',-- c_name
	'cg_3', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0, 				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);

INSERT INTO "Characteristics" VALUES(
	'c_so', 		-- ID
	0, 				-- c_priorty
	'SO', 			-- c_acronym
	'Sozialstatus',	-- c_name
	'cg_4', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	21,				-- c_h_max_value
	'true', 		-- c_h_can_increase
	'true', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0, 				-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_gs', 		-- ID
	1, 				-- c_priorty
	'GS', 			-- c_acronym
	'Geschwindigkeit',	-- c_name
	'cg_4', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	1, 				-- c_h_min_value
	50,				-- c_h_max_value
	'true', 		-- c_h_can_increase
	'true', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	1, 				-- c_f_min_value
	50, 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'true', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_be', 		-- ID
	2, 				-- c_priorty
	'BE', 			-- c_acronym
	'Behinderung',	-- c_name
	'cg_4', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0,				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL, 			-- c_h_skt_column
	'false', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'false',		-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0,	 			-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL 			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_mr', 		-- ID
	3, 				-- c_priorty
	'MR', 			-- c_acronym
	'Magieresistenz',	-- c_name
	'cg_4', 		-- c_ref_cg_ID
	'true',			-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	50,				-- c_h_max_value
	'true', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	'skt_h',		-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30,	 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	'skt_f'			-- c_f_skt_column
);

-- familiar only
INSERT INTO "Characteristics" VALUES(
	'c_lo', 		-- ID
	0, 				-- c_priorty
	'LO', 			-- c_acronym
	'Loyalitiät',	-- c_name
	'cg_4', 		-- c_ref_cg_ID
	'false',		-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0,				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL,			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	30,	 			-- c_f_max_value
	'true', 		-- c_f_can_increase
	'true', 		-- c_f_can_decrease
	'skt_f'			-- c_f_skt_column
);
INSERT INTO "Characteristics" VALUES(
	'c_tp', 		-- ID
	5, 				-- c_priorty
	'TP', 			-- c_acronym
	'Trefferpunkte',-- c_name
	'cg_2', 		-- c_ref_cg_ID
	'false',		-- c_is_used_by_hero
	-1,				-- c_h_cp_cost 				
	0, 				-- c_h_min_value
	0,				-- c_h_max_value
	'false', 		-- c_h_can_increase
	'false', 		-- c_h_can_decrease
	NULL,			-- c_h_skt_column
	'true', 		-- c_h_has_Formular
	NULL,			-- c_h_formular
	'true',			-- c_is_used_by_familiar
	0, 				-- c_f_min_value
	0,	 			-- c_f_max_value
	'false', 		-- c_f_can_increase
	'false', 		-- c_f_can_decrease
	NULL			-- c_f_skt_column
);

-- TODO insert formulars
