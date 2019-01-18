/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaapp1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author student
 */
public class JPAApp1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp1PU");
        EntityManager em = emf.createEntityManager();
        
        Zespol zespol = em.find(Zespol.class, (short)10);
        System.out.println(zespol.getIdZesp());
        System.out.println(zespol.getNazwa());
        System.out.println(zespol.getAdres());
        System.out.println("\n\n\n");
        //ZADANIE 5
        Query query=em.createNamedQuery("Zespol.findAll"); 
        List <Zespol> listaZespolow = query.getResultList();
        
        for(Zespol z : listaZespolow ){
            System.out.println("ID_ZESP: " + z.getIdZesp() + ", NAZWA: " + z.getNazwa() + ", ADRES: " + z.getAdres());
        }
        System.out.println("\n\n\n");
        //ZADANIE 6
        /*Zespol nowyZesp = new Zespol();
        nowyZesp.setIdZesp((short)90);
        nowyZesp.setNazwa("Zespol testowy");
        nowyZesp.setAdres("Kutrzeby");

        em.getTransaction().begin();
        em.persist(nowyZesp);
        em.getTransaction().commit();*/
        
        /*Zespol nowyZesp = new Zespol();
        nowyZesp.setNazwa("Test transakcji");

        em.getTransaction().begin();
        em.persist(nowyZesp);
        em.getTransaction().commit();*/
        
        /*Zespol zesp = em.find(Zespol.class, (short)90);
        Zespol zesp2 = em.find(Zespol.class, (short)92);
        System.out.println(zesp);
        em.getTransaction().begin();
        em.remove(zesp);
        em.remove(zesp2);
        em.getTransaction().commit();*/
        
    }
    
}
