/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author User
 */

public class GerarTabelas {

    public static void main(String [] args){
    EntityManagerFactory f = Persistence.createEntityManagerFactory("wpPU");
    EntityManager em = f.createEntityManager();
    f.close();
    em.close();
}
}
