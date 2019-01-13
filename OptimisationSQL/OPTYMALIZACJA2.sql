--Create excercise tables
CREATE TABLE opt_pracownicy AS SELECT * FROM sbd_tools.opt_pracownicy;
CREATE TABLE opt_zespoly AS SELECT * FROM sbd_tools.opt_zespoly;
CREATE TABLE opt_etaty AS SELECT * FROM sbd_tools.opt_etaty;

SELECT table_name
FROM user_tables; 

--Basic exercise query
SELECT nazwisko, nazwa
FROM opt_pracownicy JOIN opt_zespoly USING(id_zesp); 

EXPLAIN PLAN FOR
SELECT nazwisko, nazwa
FROM opt_pracownicy JOIN opt_zespoly USING(id_zesp); 

--Show plan
SELECT * FROM TABLE(dbms_xplan.display()); 

--Multiple plans for queries
EXPLAIN PLAN
SET STATEMENT_ID = 'zap_1_12345' FOR
SELECT nazwisko, nazwa
FROM opt_pracownicy JOIN opt_zespoly USING(id_zesp); 

EXPLAIN PLAN
SET STATEMENT_ID = 'zap_2_12345' FOR
SELECT etat, ROUND(AVG(placa),2)
FROM opt_pracownicy
GROUP BY etat;

--Show plan
SELECT *
FROM TABLE(dbms_xplan.display(statement_id => 'zap_2_12345'));

--Basic show plan
SELECT *
FROM TABLE(dbms_xplan.display(
 statement_id => 'zap_2_12345',
 format => 'BASIC'));

--Full plan show
SELECT *
FROM TABLE(dbms_xplan.display(
 statement_id => 'zap_2_12345',
 format => 'ALL'));

--AUTOTRACE
--Query
SELECT etat, ROUND(AVG(placa),2)
FROM opt_pracownicy
GROUP BY etat;

--Enable autotrace explain
SET AUTOTRACE ON EXPLAIN
--Enable auttrace statistics
SET AUTOTRACE ON STATISTICS
--Both
SET AUTOTRACE ON
--Disable
SET AUTOTRACE OFF

--Real exec plan
SELECT nazwa, COUNT(*)
FROM opt_pracownicy JOIN opt_zespoly USING(id_zesp)
GROUP BY nazwa
ORDER BY nazwa;

SELECT * FROM TABLE(dbms_xplan.display_cursor());

SELECT sql_text, sql_id,
 to_char(last_active_time, 'yyyy.mm.dd hh24:mi:ss')
 as last_active_time,
 parsing_schema_name
FROM v$sql
WHERE sql_text LIKE
 'SELECT nazwa%opt_pracownicy JOIN opt_zespoly%ORDER BY nazwa'; 
 
SELECT *
FROM TABLE(dbms_xplan.display_cursor(sql_id => '0zztcjcvpjzmd'));

SELECT /* MOJE_ZAPYTANIE_132302*/ nazwa, count(*)
FROM opt_pracownicy JOIN opt_zespoly USING(id_zesp)
GROUP BY nazwa
ORDER BY nazwa;

SELECT sql_id
FROM v$sql
WHERE sql_text LIKE 'SELECT /* MOJE_ZAPYTANIE_132302*/%';

SELECT *
FROM TABLE(dbms_xplan.display_cursor(sql_id => 'a04m4ptg01r65'));

--DOSTEP DO DANYCH
--Aktualizacja statystyk
BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_PRACOWNICY');
 DBMS_STATS.GATHER_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_ZESPOLY');
 DBMS_STATS.GATHER_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_ETATY');
END;
--check indexes
SELECT index_name, index_type
FROM user_indexes
WHERE table_name = 'OPT_PRACOWNICY';

--full table scan
SELECT nazwisko, placa
FROM opt_pracownicy WHERE id_prac = 10;
--rowid
SELECT nazwisko, placa, ROWID
FROM opt_pracownicy WHERE id_prac = 10;

SELECT nazwisko, placa
FROM opt_pracownicy WHERE rowid='AAA932AAeAAAMxsAAz';

--INDEXES
CREATE INDEX op_id_prac_idx
 ON opt_pracownicy(id_prac); 

--Check
SELECT index_name, index_type, uniqueness
FROM user_indexes
WHERE table_name = 'OPT_PRACOWNICY';

SELECT column_name, column_position
FROM user_ind_columns
WHERE index_name = 'OP_ID_PRAC_IDX'
ORDER BY column_position;

--Find by index
SELECT nazwisko, placa
FROM opt_pracownicy WHERE id_prac = 10;

DROP INDEX op_id_prac_idx;

ALTER TABLE opt_pracownicy ADD CONSTRAINT op_pk PRIMARY KEY(id_prac);

SELECT index_name, index_type, uniqueness
FROM user_indexes
WHERE table_name = 'OPT_PRACOWNICY';

SELECT column_name, column_position
FROM user_ind_columns
WHERE index_name = 'OP_PK'
ORDER BY column_position; 

CREATE INDEX op_nazwisko_idx ON opt_pracownicy(nazwisko);

BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_PRACOWNICY');
END;

--Test Index
SELECT * FROM opt_pracownicy WHERE nazwisko = 'Prac155';
SELECT * FROM opt_pracownicy WHERE nazwisko LIKE 'Prac155%';
SELECT * FROM opt_pracownicy WHERE nazwisko LIKE '%Prac155%'; 

SELECT *
FROM opt_pracownicy
WHERE nazwisko LIKE 'Prac155%' OR nazwisko LIKE 'Prac255%'; 

SELECT * FROM opt_pracownicy
WHERE nazwisko IN ('Prac155','Prac255'); 

DROP INDEX op_nazwisko_idx;

--Multi index
CREATE INDEX op_nazw_placa_idx ON
 opt_pracownicy(nazwisko, placa);

SELECT index_name, table_name, index_type, uniqueness
FROM user_indexes
WHERE index_name = 'OP_NAZW_PLACA_IDX';

SELECT column_name, column_position
FROM user_ind_columns
WHERE index_name = 'OP_NAZW_PLACA_IDX'
ORDER BY column_position; 

SELECT nazwisko, placa, id_prac
FROM opt_pracownicy
WHERE nazwisko LIKE 'Prac155%' AND placa > 1000;

--part use
SELECT nazwisko, placa, id_prac
FROM opt_pracownicy WHERE nazwisko LIKE 'Prac155%';

SELECT nazwisko, placa, id_prac
FROM opt_pracownicy WHERE placa < 200; 

SELECT nazwisko, placa
FROM opt_pracownicy
WHERE nazwisko LIKE 'Prac155%'; 

SELECT nazwisko FROM opt_pracownicy WHERE placa < 1000; 

--B-Tree index
CREATE INDEX op_plec_placa_idx ON opt_pracownicy (plec, placa); 

--test
SELECT /*+ INDEX_SS(p) */ plec, nazwisko FROM opt_pracownicy p
WHERE placa > 1500; 

SELECT placa, etat FROM opt_pracownicy
WHERE nazwisko = 'Prac155';

SELECT placa, etat FROM opt_pracownicy
WHERE UPPER(nazwisko) = 'PRAC155'; 

--index funkcyjny
CREATE INDEX op_fun_idx ON opt_pracownicy(UPPER(nazwisko)); 

--test
SELECT placa, etat FROM opt_pracownicy
WHERE UPPER(nazwisko) = 'PRAC155';

--index join
SELECT nazwisko
FROM opt_pracownicy
WHERE id_prac < 500 AND nazwisko LIKE 'Prac15%'; 


--INDEKSY BITMAPOWE
SET AUTOTRACE OFF

SELECT DISTINCT plec FROM opt_pracownicy;
SELECT DISTINCT etat FROM opt_pracownicy;

CREATE BITMAP INDEX op_etat_bmp_idx ON opt_pracownicy(etat);
CREATE BITMAP INDEX op_plec_bmp_idx ON opt_pracownicy(plec); 

SELECT index_name, table_name, index_type, uniqueness
FROM user_indexes
WHERE index_name IN ('OP_ETAT_BMP_IDX', 'OP_PLEC_BMP_IDX');

SELECT index_name, column_name, column_position
FROM user_ind_columns
WHERE index_name IN ('OP_ETAT_BMP_IDX', 'OP_PLEC_BMP_IDX')
ORDER BY index_name, column_position; 

BEGIN
 DBMS_STATS.GATHER_TABLE_STATS(ownname => 'inf132302',
 tabname => 'OPT_PRACOWNICY');
END;

--test
SET AUTOTRACE ON

SELECT COUNT(*) FROM opt_pracownicy
WHERE plec = 'K' AND etat = 'DYREKTOR'; 


SELECT COUNT(*) FROM opt_pracownicy
WHERE plec = 'K' AND (etat = 'DYREKTOR' OR etat = 'PROFESOR'); 

SELECT nazwisko FROM opt_pracownicy
WHERE plec = 'K' AND etat = 'DYREKTOR'; 


SELECT nazwisko FROM opt_pracownicy
WHERE plec = 'K' AND etat = NULL; 