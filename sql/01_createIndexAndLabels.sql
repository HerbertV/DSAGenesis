/*
	SKT
*/
INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	( 	'SKT', 
		'false',
		'skt_', 
		'SKT', 
		'Steigerungskosten-Tabelle',
		'true',
		0,
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
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_is_internal, ti_tab_index,  ti_editable )
	VALUES
	(	'SKTShifts', 
		'false',
		'skts_', 
		'SKT Verschiebungen', 
		'<html>Hier werden die Verschiebungen der SKT Spalten durch Vor-/Nachteile Sonderf‰higkeiten verwaltet.<br></html>',
		'false',
		0,
		'true'
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
	CharacteristicGroups
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
	Characteristics
*/		

INSERT INTO CoreDataTableIndex
	( ti_table_name, ti_uses_prefix, ti_prefix, ti_label, ti_note, ti_is_internal, ti_tab_index, ti_editable )
	VALUES
	( 	'Characteristics', 
		'true',
		'c_', 
		'Grundwerte', 
		'Eigenschaften und sonstige Werte die berechnet werden oder gesteigert werden d√ºrfen.',
		'false',
		2,
		'true'
	);

-- TODO needs update for the new fields
/*

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
*/
