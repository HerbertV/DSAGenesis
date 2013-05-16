/*
-----------------------------------------------------------------
	inserts the basic data entries for DSA Genesis
-----------------------------------------------------------------
*/

/*
-----------------------------------------------------------------
	"SKT" Entries
-----------------------------------------------------------------
*/
DELETE FROM "SKT";

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


/*
-----------------------------------------------------------------
	CharacteristicGroups Entries
-----------------------------------------------------------------
*/
DELETE FROM "CharacteristicGroups";

INSERT INTO "CharacteristicGroups" VALUES('cg_0', 'Eigenschaften');
INSERT INTO "CharacteristicGroups" VALUES('cg_1', 'Energien');
INSERT INTO "CharacteristicGroups" VALUES('cg_2', 'Kampfwerte');
INSERT INTO "CharacteristicGroups" VALUES('cg_3', 'Rüstungswerte');
INSERT INTO "CharacteristicGroups" VALUES('cg_4', 'Sonstige');

UPDATE "CoreDataTableIndex" 
	SET "ti_last_index_num"=5 
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
DELETE FROM "Characteristics";

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
	'c_so', 0, 'SO', 'Sozialstatus', 'cg_4', 1, 0, 21, 'true', 'true', NULL, 'false', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_le', 0, 'LE', 'Lebensenergie', 'cg_1', 1, 0, 50, 'true', 'false', 'skt_h', 'true', NULL
);
INSERT INTO "Characteristics" VALUES(
	'c_ae', 0, 'AE', 'Astralenergie', 'cg_1', 1, 0, 50, 'true', 'true', 'skt_g', 'true', NULL
);


--TODO
