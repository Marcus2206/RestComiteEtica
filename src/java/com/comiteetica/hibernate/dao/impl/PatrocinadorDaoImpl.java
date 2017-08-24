/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.PatrocinadorDao;
import com.comiteetica.hibernate.model.Patrocinador;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class PatrocinadorDaoImpl implements PatrocinadorDao {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void beginTransaction() {
        sessionFactory.getCurrentSession().beginTransaction();
    }

    @Override
    public void commit() {
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @Override
    public void close() {
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void rollback() {
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }

    @Override
    public void create(Patrocinador patrocinador) {
        sessionFactory.getCurrentSession().save(patrocinador);
    }

    @Override
    public Patrocinador read(String idPatrocinador) {
        Patrocinador patrocinador = (Patrocinador) sessionFactory.getCurrentSession().get(Patrocinador.class, idPatrocinador);
        return patrocinador;
    }

    @Override
    public void update(Patrocinador patrocinador) {
        sessionFactory.getCurrentSession().update(patrocinador);
    }

    @Override
    public void delete(Patrocinador patrocinador) {
        sessionFactory.getCurrentSession().delete(patrocinador);
    }

    @Override
    public List<Patrocinador> getAllPatrocinador() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select p "
                        + "from Patrocinador p "
                        + "order by p.nombre asc");

        List<Patrocinador> patrocinadors = query.list();

        return patrocinadors;
    }

    @Override
    public List<Patrocinador> getPatrocinadorSinIdCro(String idCro) {
        List<Patrocinador> patrocinadors = new ArrayList<>();

        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select p.idPatrocinador, p.nombre \n"
                        + "from Patrocinador p\n"
                        + "where p.idPatrocinador not in(select distinct pc.id.idPatrocinador \n"
                        + "from PatrocinadorCro pc\n"
                        + "where pc.id.idCro='"+idCro+"')\n"
                        + "");

        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((pat) -> {
            Patrocinador patr = new Patrocinador();
            patr.setIdPatrocinador(pat[0].toString());
            patr.setNombre(pat[1].toString());
            patrocinadors.add(patr);
        });

        return patrocinadors;
    }
}
