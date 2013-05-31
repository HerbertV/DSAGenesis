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
	contains Attributes and anything else that is leveled or calculated
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
	"c_name" TEXT NOT NULL,
	"c_acronym" VARCHAR(5) NOT NULL,
	"c_priorty" INTEGER DEFAULT '0' NOT NULL,
	"c_ref_cg_ID" VARCHAR(10) REFERENCES CharacteristicGroups(ID) NOT NULL,
	"c_is_used_by_hero" BOOLEAN DEFAULT '1' NOT NULL,
	"c_h_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"c_h_min_value" INTEGER NULL,
	"c_h_max_value" INTEGER NULL,
	"c_h_can_increase" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_can_decrease" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_skt_column" VARCHAR(10) NULL,
	"c_h_has_Formular" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_formular" TEXT DEFAULT NULL,
	"c_is_used_by_familiar" BOOLEAN DEFAULT 'false' NOT NULL,
	"c_f_min_value" INTEGER NULL,
	"c_f_max_value" INTEGER NULL,
	"c_f_can_increase" BOOLEAN DEFAULT '0' NOT NULL,
	"c_f_can_decrease" BOOLEAN DEFAULT '0' NOT NULL,
	"c_f_skt_column" VARCHAR(10) NULL
);

/*
	CharacteristicGroups Index and Labels
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	(	'CharacteristicGroups', 
		1,
		'cg_', 
		'Grundwerte Gruppen', 
		'Die Grundwerte sind zur besseren Unterscheidung in Gruppen unterteilt',
		3,
		0
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'CharacteristicGroups', 'cg_name', 'Name');	
	
/*
	Characteristics Index and Labels
*/		

INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_tab_index, ti_editable )
	VALUES
	( 	'Characteristics', 
		1,
		'c_', 
		'Grundwerte', 
		'Eigenschaften und sonstige Werte die berechnet werden oder gesteigert werden dürfen.<br>Spalten die für Helden verwendet werden sind mit (H) gekennzeichnet.<br>Spalten für Vertraute sind mit (V) gekennzeichnet.',
		4,
		1
	);

INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_priorty', 'Reihenfolge');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'Characteristics', 'c_acronym', 'Kürzel');
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
 	c_name,
 	c_acronym, 
 	c_priorty, 
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
	'c_mu',
	'Mut',
	'MU',
	0,
	'cg_0',
	'true',
	1,	
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_kl',
	'Klugheit',
	'KL',
	1,
	'cg_0',
	'true',
	1,
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_in',
	'Intuition',
	'IN',
	2,
	'cg_0',
	'true',
	1,
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ch',
	'Charisma',
	'CH',
	3,
	'cg_0',
	'true',
	1,
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ff',
	'Fingerfertigkeit',
	'FF',
	4,
	'cg_0',
	'true',
	1,		
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ge',
	'Gewandtheit',
	'GE',
	5,
	'cg_0',
	'true',
	1,	
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ko',
	'Konstitution',
	'KO',
	6,
	'cg_0',
	'true',
	1,	
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_kk',
	'Körperkraft',
	'KK',
	7,
	'cg_0',
	'true',
	1,		
	0,
	30,
	'true',
	'false',
	'skt_h',
	'false',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_le',
	'Lebensenergie',
	'LE',
	0,
	'cg_1',
	'true',
	-1,		
	0,
	50,
	'true',
	'false',
	'skt_h',
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_au',
	'Ausdauer',
	'AU',
	1,
	'cg_1',
	'true',
	-1,
	0,
	50,
	'true',
	'false',
	'skt_e',
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ae',
	'Astralenergie',
	'AE',
	2,
	'cg_1',
	'true',
	-1,		
	0,
	50,
	'true',
	'false',
	'skt_g',
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_ke',
	'Karmanergie',
	'KE',
	3,
	'cg_1',
	'true',
	-1,			
	0,
	50,
	'true',
	'true',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_at',
	'Attacke',
	'AT',
	0,
	'cg_2',
	'true',
	-1,	
	0,
	0,
	'false',
	'false',
	NULL,
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_pa',
	'Parade',
	'PA',
	1,
	'cg_2',
	'true',
	-1,			
	0,
	0,
	'false', 
	'false',
	NULL,
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_aw',
	'Ausweichen',
	'AW',
	2,
	'cg_2',
	'true',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'true',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ini', 
	'Initiative',
	'INI',
	3,
	'cg_2',
	'true',
	-1, 				
	0,
	0,
	'false',
	'false',
	NULL,
	'true',
	NULL,
	'true',
	0,
	0,
	'false',
	'false',
	NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_rs',
	'Rüstungsschutz',
	'RS',
	0,
	'cg_3',
	'true',
	-1,				
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'true',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z1',
	'RS Kopf',
	'RS Kopf',
	1,
	'cg_3',
	'true',
	-1,
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z2',
	'RS Brust',
	'RS Brust',
	2,
	'cg_3',
	'true',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z3',
	'RS Rücken',
	'RS Rücken',
	3,
	'cg_3',
	'true',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z4',
	'RS Bauch',
	'RS Bauch',
	4,
	'cg_3',
	'true',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z5',
	'RS Arm Links',
	'RS Arm L.',
	5,
	'cg_3',
	'true',
	-1,				
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z6',
	'RS Arm Rechts',
	'RS Arm R.', 	
	6,
	'cg_3',
	'true',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z7',
	'RS Bein Links',
	'RS Bein L.', 	
	7,
	'cg_3',
	'true',
	-1,		
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_rs_z8',
	'RS Bein Rechts',
	'RS Bein R.',
	8,
	'cg_3',
	'true',
	-1,		
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);

INSERT INTO "Characteristics" VALUES(
	'c_so',
	'Sozialstatus',
	'SO',
	0,
	'cg_4',
	'true',
	1,		
	0,
	21,
	'true',
	'true',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_gs',
	'Geschwindigkeit',
	'GS',
	1,
	'cg_4',
	'true',
	-1,
	1,
	50,
	'true',
	'true',
	NULL,
	'false',
	NULL,
	'true',
	1,
	50,
	'true',
	'true',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_be',
	'Behinderung',
	'BE',
	2,
	'cg_4',
	'true',
	-1,
	0,
	0,
	'false',
	'false',
	NULL,
	'false',
	NULL,
	'false',
	0,
	0,
	'false',
	'false',
	NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_mr',
	'Magieresistenz',
	'MR',
	3,
	'cg_4',
	'true',
	-1,		
	0,
	50,
	'true',
	'false',
	'skt_h',
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'false',
	'skt_f'
);

INSERT INTO "Characteristics" VALUES(
	'c_lo',
	'Loyalitiät',
	'LO',
	0,
	'cg_4',
	'false',
	-1,			
	0,
	0,
	'false',
	'false',
	NULL,
	'true',
	NULL,
	'true',
	0,
	30,
	'true',
	'true',
	'skt_f'
);
INSERT INTO "Characteristics" VALUES(
	'c_tp',
	'Trefferpunkte',
	'TP',
	5,
	'cg_2',
	'false',
	-1,		
	0,
	0,
	'false',
	'false',
	NULL,
	'true',
	NULL,
	'true',
	0,
	0,
	'false',
	'false',
	NULL
);

/*
 TODO insert formulars
*/