SET AUTOTRACE OFF;

BEGIN
 DBMS_STATS.DELETE_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_PRACOWNICY');
END; 

SELECT num_rows, last_analyzed, avg_row_len, blocks
FROM user_tables
WHERE table_name = 'OPT_PRACOWNICY';
--NULL wszedzie
SELECT column_name, num_distinct, low_value, high_value
FROM user_tab_col_statistics
WHERE table_name = 'OPT_PRACOWNICY';

SELECT index_name, num_rows, leaf_blocks, last_analyzed
FROM user_indexes
WHERE table_name = 'OPT_PRACOWNICY';

--Tworzenie i dodawanie statystyk
BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(
 ownname=>'inf132302', tabname => 'OPT_PRACOWNICY');
END; 

--staty na podstawie probki podanej wielkosci
BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(
 ownname=>'inf132302', tabname => 'OPT_PRACOWNICY',
 estimate_percent => 40);
END; 

SELECT num_rows, last_analyzed, avg_row_len, blocks, sample_size
FROM user_tables
WHERE table_name = 'OPT_PRACOWNICY';

SET AUTOTRACE ON;
SELECT * FROM opt_pracownicy WHERE nazwisko LIKE 'Prac155%';

SELECT /*+ DYNAMIC_SAMPLING(0) */ *
FROM opt_pracownicy WHERE nazwisko LIKE 'Prac155%';

BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(
 ownname=>'inf132302', tabname => 'OPT_PRACOWNICY',
 cascade => TRUE);
END; 

--HISTOGRAMY
SELECT column_name, num_distinct, low_value, high_value,
 num_buckets, histogram
FROM user_tab_col_statistics
WHERE table_name = 'OPT_PRACOWNICY'; 

SELECT placa_dod, COUNT(*)
FROM opt_pracownicy
GROUP BY placa_dod; 

--usun histogram dla PLACA_POD
BEGIN
 DBMS_STATS.DELETE_COLUMN_STATS(
 ownname => 'inf132302', tabname => 'OPT_PRACOWNICY',
 colname => 'PLACA_DOD', col_stat_type => 'HISTOGRAM');
END;

--check
SELECT num_distinct, low_value,
 high_value, num_buckets, histogram
FROM user_tab_col_statistics
WHERE table_name = 'OPT_PRACOWNICY'
AND column_name = 'PLACA_DOD'; 

--nowy index
CREATE BITMAP INDEX op_bmp_placa_dod_idx ON opt_pracownicy(placa_dod); 

SELECT * FROM opt_pracownicy WHERE placa_dod = 100;

SELECT * FROM opt_pracownicy WHERE placa_dod = 999;

BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(
 ownname => 'inf132302', tabname => 'OPT_PRACOWNICY',
 method_opt => 'FOR COLUMNS placa_dod SIZE AUTO');
END; 
--Jeszcze raz powyzsze zapytania z PLACA_DOD

--WSKAZOWKI
SELECT * FROM opt_pracownicy WHERE id_prac = 900; 

SELECT /*+ FULL(opt_pracownicy) */ *
FROM opt_pracownicy WHERE id_prac = 900;

--ALIASY _ ZIGNOROWANA WSKAZÓWKA
SELECT /*+ FULL(opt_pracownicy) */ *
FROM opt_pracownicy p
WHERE id_prac = 900;

SELECT /*+ FULL(p) */ *
FROM opt_pracownicy p
WHERE id_prac = 900;

--ZABROŃ WSZYSTKICH INDEKSÓW
SELECT /*+ NO_INDEX(opt_pracownicy) */ *
FROM opt_pracownicy
WHERE id_prac = 900;

--ZABROŃ KONKRETNEGO INDEKSU
SELECT /*+ NO_INDEX(opt_pracownicy op_pk) */ *
FROM opt_pracownicy
WHERE id_prac = 900;

--"NAKAZ' KONKRETNY INDEKS
SELECT nazwisko, etat FROM opt_pracownicy;

SELECT /*+ INDEX(opt_pracownicy) */ nazwisko, etat
FROM opt_pracownicy;

SELECT /*+ INDEX_COMBINE(opt_pracownicy) */ nazwisko, etat
FROM opt_pracownicy;

--FAST FULL INDEX SCAN
SELECT placa
FROM opt_pracownicy
WHERE plec = 'K';

SELECT /*+ INDEX_FFS(opt_pracownicy) */ placa
FROM opt_pracownicy
WHERE plec = 'K';

--SCAN WITHOUT COLUMNS
SELECT /*+ INDEX_SS(opt_pracownicy) */ placa
FROM opt_pracownicy
WHERE plec = 'K';

--INDEX JOIN
SELECT /*+ INDEX_JOIN(opt_pracownicy op_pk op_nazw_placa_idx) */
 nazwisko
FROM opt_pracownicy
WHERE id_prac < 1000 AND placa = 1500; 

