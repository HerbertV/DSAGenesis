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
	"c_priorty" INTEGER DEFAULT '0' NOT NULL,
	"c_acronym" VARCHAR(5) NOT NULL,
	"c_name" TEXT NOT NULL,
	"c_ref_cg_ID" VARCHAR(10) REFERENCES CharacteristicGroups(ID) NOT NULL,
	"c_is_used_by_hero" BOOLEAN DEFAULT '1' NOT NULL,
	"c_h_cp_cost" INTEGER DEFAULT '0' NOT NULL,
	"c_h_min_value" INTEGER NULL,
	"c_h_max_value" INTEGER NULL,
	"c_h_can_increase" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_can_decrease" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_skt_column" VARCHAR(10) NULL,
	"c_h_has_Formular" BOOLEAN DEFAULT '0' NOT NULL,
	"c_h_formular" TEXT NULL,
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
	'c_mu',
	0,
	'MU',
	'Mut',
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
	1,
	'KL',
	'Klugheit',
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
	2,
	'IN',
	'Intuition',
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
	3,
	'CH',
	'Charisma',
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
	4,
	'FF',
	'Fingerfertigkeit',
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
	5,
	'GE',
	'Gewandtheit',
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
	6,
	'KO',
	'Konstitution',
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
	7,
	'KK',
	'Körperkraft',
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
	0,
	'LE',
	'Lebensenergie',
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
	1,
	'AU',
	'Ausdauer',
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
	2,
	'AE',
	'Astralenergie',
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
	3,
	'KE',
	'Karmanergie',
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
	0,
	'AT',
	'Attacke',
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
	1,
	'PA',
	'Parade',
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
	2,
	'AW',
	'Ausweichen',
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
	3,
	'INI',
	'Initiative',
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
	0,
	'RS',
	'Rüstungsschutz',
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
	1,
	'RS Kopf',
	'RS Kopf',
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
	2,
	'RS Brust',
	'RS Brust',
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
	3,
	'RS Rücken',
	'RS Rücken',
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
	4,
	'RS Bauch',
	'RS Bauch',
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
	5,
	'RS Arm L.',
	'RS Arm Links',
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
	6,
	'RS Arm R.', 	
	'RS Arm Rechts',
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
	7,
	'RS Bein L.', 	
	'RS Bein Links',
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
	8,
	'RS Bein R.',
	'RS Bein Rechts',
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
	0,
	'SO',
	'Sozialstatus',
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
	1,
	'GS',
	'Geschwindigkeit',
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
	2,
	'BE',
	'Behinderung',
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
	3,
	'MR',
	'Magieresistenz',
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
	0,
	'LO',
	'Loyalitiät',
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
	5,
	'TP',
	'Trefferpunkte',
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