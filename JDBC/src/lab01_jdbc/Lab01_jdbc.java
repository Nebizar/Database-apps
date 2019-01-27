/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_jdbc;


import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger; 
/**
 *
 * @author student
 */
public class Lab01_jdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "inf132302");
        connectionProps.put("password", "inf132302");
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(Lab01_jdbc.class.getName()).log(Level.SEVERE,"nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }
        //odczyt
        Statement stmt = null;
        Statement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt5 = null;
        ResultSet rs = null;
        
        int [] zwolnienia={150, 200, 230};
        String [] zatrudnienia={"Kandefer", "Rygiel", "Boczar"};
		
		String [] nazwiska={"Woźniak", "Dąbrowski", "Kozłowski"};
		int [] place={1300, 1700, 1500};
		String []etaty={"ASYSTENT", "PROFESOR", "ADIUNKT"};
        
        try {
            stmt = conn.createStatement();
            //CWICZENIE
           /* rs = stmt.executeQuery("select RPAD(id_prac, 5)  , RPAD(nazwisko, 15) AS NAZWISKO , placa_pod " + "from pracownicy");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString("NAZWISKO") + " " +
                rs.getFloat(3));
            }*/
            //ZADANIE 1
            
            /*rs = stmt.executeQuery("SELECT COUNT(ID_PRAC) FROM PRACOWNICY");
            while (rs.next()) {
                System.out.println("Zatrudniono " + rs.getInt(1) + " pracowników w tym: ") ;
            }
            rs = stmt.executeQuery("SELECT COUNT(ID_PRAC) AS LICZBA, ID_ZESP FROM PRACOWNICY GROUP BY ID_ZESP");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " w zespole " + rs.getInt("ID_ZESP")) ;
            }*/
            
            //ZADANIE 2
            
            /*stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt2.executeQuery("SELECT ID_PRAC, NAZWISKO, ETAT, PLACA_POD FROM PRACOWNICY WHERE ETAT='ASYSTENT' ORDER BY PLACA_POD DESC  ");
            rs.last();
                System.out.println(rs.getInt(1) + " " + rs.getString("NAZWISKO") + " " + rs.getString("ETAT") + " " +
                rs.getFloat(4));
            rs.absolute(3);
                System.out.println(rs.getInt(1) + " " + rs.getString("NAZWISKO") + " " + rs.getString("ETAT") + " " +
                rs.getFloat(4));
            rs.last();
            rs.relative(-1);
                System.out.println(rs.getInt(1) + " " + rs.getString("NAZWISKO") + " " + rs.getString("ETAT") + " " +
                rs.getFloat(4));*/
            
            //ZADANIE 3
            /*int changes = 0;
            stmt3 = conn.prepareStatement("DELETE FROM pracownicy WHERE id_prac=?");
            for(int i : zwolnienia){
                stmt3.setInt(1, i);
                changes += stmt3.executeUpdate();
            }
            System.out.println("Usunięto "+changes+" rekordów.");
            
            //GET MAX IP_PRAC
            rs = stmt.executeQuery("select MAX(ID_PRAC) from pracownicy");
            rs.next();
            int maxId = rs.getInt(1);
            
            //DODAJ PRACOWNIKOW
            stmt3 = conn.prepareStatement("INSERT INTO PRACOWNICY(ID_PRAC, NAZWISKO) VALUES(?, ?)");
            changes = 0;
            for(String lastName: zatrudnienia){
                maxId++;
                stmt3.setInt(1, maxId);
                stmt3.setString(2, lastName);
                changes += stmt3.executeUpdate();
            }
            System.out.println("Dodano " + changes + " pracowników");*/
            
            //ZADANIE 4
            //stmt.executeUpdate("DELETE FROM ETATY WHERE nazwa='NEWETAT'"); 
            /*conn.setAutoCommit(false);
            rs = stmt.executeQuery("SELECT NAZWA, PLACA_MIN, PLACA_MAX FROM ETATY");
            while (rs.next()) {
                System.out.println(rs.getString("NAZWA") + " " +
                rs.getFloat(2) + " " +
                rs.getFloat(3));
            }
            //UPDATE
            stmt.executeUpdate("INSERT INTO ETATY(NAZWA, PLACA_MIN, PLACA_MAX) VALUES('NEWETAT',300,500)");
            System.out.println("\n\n");
            
            rs = stmt.executeQuery("SELECT NAZWA, PLACA_MIN, PLACA_MAX FROM ETATY");
            while (rs.next()) {
                System.out.println(rs.getString("NAZWA") + " " +
                rs.getFloat(2) + " " +
                rs.getFloat(3));
            }
            //ROLLBACK
            conn.rollback();
            
            System.out.println("\n\n");
            rs = stmt.executeQuery("SELECT NAZWA, PLACA_MIN, PLACA_MAX FROM ETATY");
            while (rs.next()) {
                System.out.println(rs.getString("NAZWA") + " " +
                rs.getFloat(2) + " " +
                rs.getFloat(3));
            }
            
            stmt.executeUpdate("INSERT INTO ETATY(NAZWA, PLACA_MIN, PLACA_MAX) VALUES('NEWETAT',300,500)");
            System.out.println("\n\n");
            
            conn.commit();
            
            rs = stmt.executeQuery("SELECT NAZWA, PLACA_MIN, PLACA_MAX FROM ETATY");
            while (rs.next()) {
                System.out.println(rs.getString("NAZWA") + " " +
                rs.getFloat(2) + " " +
                rs.getFloat(3));
            }*/ //KONIEC ZADANIE 4
            
            //ZADANIE 5
            stmt5 = conn.prepareStatement("INSERT INTO PRACOWNICY(ID_PRAC, NAZWISKO, PLACA_POD, ETAT) VALUES(?, ?, ?, ?)")
            rs = stmt.executeQuery("select MAX(ID_PRAC) from pracownicy");
            rs.next();
            int maxId = rs.getInt(1);
			
			int i = 0;
			for(String lastName: nazwiska){
                maxId++;
                stmt5.setInt(1, maxId);
                stmt5.setString(2, lastName);
				stmt5.setInt(3, place[i]);
				stmt5.setString(4, etaty[i]);
                changes += stmt5.executeUpdate();
				i++;
            }
            //stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ID_PRAC, NAZWISKO, ETAT, PLACA_POD FROM PRACOWNICY");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
            }

            //ZADANIE 6
            conn.setAutoCommit(false);
            long start = System.nanoTime();
            stmt5 = conn.prepareStatement("INSERT INTO pracownicy(ID_PRAC) VALUES (?)");
            for(int k=600;k<1600;k++)
            {
                pstmt.setInt(1, k);
                rs = pstmt.executeQuery();
            }
            long czas = (System.nanoTime() - start)/1000000;
            System.out.println("Time: " + czas + "ms");
            conn.rollback();

            conn.setAutoCommit(true);

            //ZADANIE 7
            CallableStatement cstmt = conn.prepareCall("{? = call ZMIENWIELKOSCLITER(?)}");
            cstmt.setInt(2, 120);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();
            String nazwisko = cstmt.getString(1);
            System.out.println(nazwisko);
            System.out.println("1");
            
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        } 
        
        //koniec odczytu
        try {
            conn.close();
            System.out.println("Rozłączono z baza danych");
        } catch (SQLException ex) {
            Logger.getLogger(Lab01_jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
