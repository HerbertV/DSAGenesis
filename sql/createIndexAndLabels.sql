/*
	SKT
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_prefix, ti_label, ti_note, ti_editable )
	VALUES
	( 	'SKT', 
		'skt_', 
		'SKT', 
		'Steigerungskosten-Tabelle',
		'true'
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
	SKTShifts
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_prefix, ti_label, ti_note, ti_editable )
	VALUES
	(	'SKTShifts', 
		'skts_', 
		'SKT Verschiebungen', 
		'<html>Hier werden die Verschiebungen der SKT Spalten durch Vor-/Nachteile Sonderfähigkeiten verwaltet.<br></html>',
		'true'
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_ref_source_ID', 'Quelle');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_ref_target_ID', 'Ziel');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_is_down_shift', 'Ist Links');	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'SKTShifts', 'skts_is_up_shift', 'Ist Rechts');	
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
	CharacteristicGroups
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_prefix, ti_label, ti_note, ti_editable )
	VALUES
	(	'CharacteristicGroups', 
		'cg_', 
		'Grundwerte Gruppen', 
		'Die Grundwerte sind zur besseren Unterscheidung in Gruppen unterteilt',
		'false'
	);
	
INSERT INTO TableColumnLabels
	( tcl_table_name, tcl_column_name, tcl_label )
	VALUES
	( 'CharacteristicGroups', 'cg_name', 'Name');	
	
/*
	Characteristics
*/		

-- TODO needs update for the new fields
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_prefix, ti_label, ti_note, ti_editable )
	VALUES
	( 	'Characteristics', 
		'c_', 
		'Grundwerte', 
		'Eigenschaften und sonstige Werte die berechnet werden oder gesteigert werden dürfen.',
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
