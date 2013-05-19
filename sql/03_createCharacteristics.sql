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
		'Eigenschaften und sonstige Werte die berechnet werden oder gesteigert werden dÃ¼rfen.',
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
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_cp_cost', 'GP');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_min_value', 'Min');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_max_value', 'Max');		
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_can_increase', 'Steigerbar');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_can_decrease', 'Senkbar');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_skt_column', 'SKT Spalte');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_has_Formular', 'hat Formel');
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_formular', 'Berechnungsformel');

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
 	c_cp_cost, 
 	c_min_value,
 	c_max_value,
 	c_can_increase,
 	c_can_decrease,
 	c_skt_column,
 	c_has_Formular,
 	c_formular,
*/
INSERT INTO "Characteristics" VALUES(
	'c_mu', 0, 'MU', 'Mut', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_kl', 1, 'KL', 'Klugheit', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_in', 2, 'IN', 'Intuition', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ch', 3, 'CH', 'Charisma', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ff', 4, 'FF', 'Fingerfertigkeit', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ge', 5, 'GE', 'Gewandtheit', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ko', 6, 'KO', 'Konstitution', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_kk', 7, 'KK', 'Körperkraft', 'cg_0', 1, 0, 30, 'true', 'false', 'skt_h', 'false', NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_le', 0, 'LE', 'Lebensenergie', 'cg_1', 1, 0, 50, 'true', 'false', 'skt_h', 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_au', 0, 'AU', 'Ausdauer', 'cg_1', 1, 0, 50, 'true', 'true', 'skt_e', 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ae', 0, 'AE', 'Astralenergie', 'cg_1', 1, 0, 50, 'true', 'true', 'skt_g', 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ke', 0, 'KE', 'Karmanergie', 'cg_1', 1, 0, 50, 'true', 'false', NULL, 'false', NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_at', 0, 'AT', 'Attacke-Basis', 'cg_2', 0, 0, 50, 'false', 'false', NULL, 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_pa', 1, 'PA', 'Parade-Basis', 'cg_2', 0, 0, 50, 'false', 'false', NULL, 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_aw', 2, 'AW', 'Ausweichen', 'cg_2', 0, 0, 50, 'false', 'false', NULL, 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ini', 3, 'INI', 'INI-Basis', 'cg_2', 0, 0, 50, 'false', 'false', NULL, 'true', NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_rs', 0, 'RS', 'Rüstungsschutz', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z1', 1, 'RS Kopf', 'RS Kopf', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z2', 2, 'RS Brust', 'RS Brust', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z3', 3, 'RS Rücken', 'RS Rücken', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z4', 4, 'RS Bauch', 'RS Bauch', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z5', 5, 'RS Arm L.', 'RS Arm Links', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z6', 6, 'RS Arm R.', 'RS Arm Rechts', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z7', 7, 'RS Bein L.', 'RS Bein Links', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z7', 8, 'RS Bein R.', 'RS Bein Rechts', 'cg_3', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);


INSERT INTO "Characteristics" VALUES(
	'c_so', 0, 'SO', 'Sozialstatus', 'cg_4', 1, 0, 21, 'true', 'true', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_gs', 0, 'GS', 'Geschwindigkeit', 'cg_4', 0, 0, 50, 'true', 'true', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_be', 0, 'BE', 'Behinderung', 'cg_4', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_mr', 0, 'MR', 'Magieresistenz', 'cg_4', 0, 0, 50, 'true', 'false', 'skt_h', 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_mr', 0, 'MR', 'Magieresistenz', 'cg_4', 0, 0, 50, 'true', 'false', 'skt_h', 'true', NULL
);
-- for companions/familiars
INSERT INTO "Characteristics" VALUES(
	'c_lo', 0, 'LO', 'Loyalitiät', 'cg_4', 0, 0, 50, 'true', 'true', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_tp', 0, 'TP', 'trefferpunkte', 'cg_4', 0, 0, 50, 'false', 'false', NULL, 'false', NULL
);
