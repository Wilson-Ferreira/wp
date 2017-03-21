/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Wilson F Florindo
 */

public class ClienteRepositorio implements Serializable {

    @Inject
    private EntityManager em;
  
    public void salvarAlterarCliente(Cliente cliente) throws Exception {

        if (cliente.getId() == null) {

            em.persist(cliente);

        } else {

            em.merge(cliente);
        }
    }

    public void excluirCliente(Cliente cliente) throws Exception {

        Cliente clienteParaExcluir = em.find(Cliente.class, cliente.getId());

        em.remove(clienteParaExcluir);
    }

    public void zerarCartoes(Cartao cartaoZero) throws Exception {

        String query = "Update Cliente c set c.cartao= :cartaoZero where c.cartao<> :cartaoZero";
        Query sQuery = em.createQuery(query);

        sQuery.setParameter("cartaoZero", cartaoZero);

        sQuery.executeUpdate();
    }

    public Cliente buscarClientePorCartao(Cartao cartao) throws Exception {

        return (Cliente) em.createQuery("Select c From Cliente c where c.cartao= :cartao")
                .setParameter("cartao", cartao).getSingleResult();

    }

    public List<Cliente> buscarTodosClientes(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filtros) throws Exception {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(Cliente.class);
        Root<Cliente> from = criteriaQuery.from(Cliente.class);
        CriteriaQuery<Cliente> select = criteriaQuery.select(from);

        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = builder.and();
        for (Iterator<String> it = filtros.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            String filterValue = (String) filtros.get(filterProperty);

            if (filterProperty.equals("cartao.numeroCartao")) {

                predicate = builder.and(predicate, builder.like(
                        from.join("cartao").get("numeroCartao"), filterValue + "%"));
                predicates.add(predicate);
            } else {

                Expression expression = (Expression) from.get(filterProperty);
                predicates.add(builder.like(expression, filterValue + "%"));

            }
        }

        select.where(predicates.toArray(new Predicate[]{}));

        if (sortField != null) {

            switch (sortOrder) {
                case ASCENDING:
                    select.orderBy(builder.asc(from.get(sortField)));
                    break;

                case DESCENDING:
                    select.orderBy(builder.desc(from.get(sortField)));
                    break;
            }
        }

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Cliente.class)));
        em.createQuery(countQuery);

        TypedQuery<Cliente> listQuery = em.createQuery(select);
        listQuery.setFirstResult(first);
        listQuery.setMaxResults(pageSize);

        return listQuery.getResultList();
    }

    public int buscarQtdeRegistros(String sortField, SortOrder sortOrder, Map<String, Object> filtros) throws Exception {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(Cliente.class);
        Root<Cliente> from = criteriaQuery.from(Cliente.class);
        CriteriaQuery<Cliente> select = criteriaQuery.select(from);

        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = builder.and();
        for (Iterator<String> it = filtros.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            String filterValue = (String) filtros.get(filterProperty);

            if (filterProperty.equals("cartao.numeroCartao")) {

                predicate = builder.and(predicate, builder.like(
                        from.join("cartao").get("numeroCartao"), filterValue + "%"));
                predicates.add(predicate);

            } else {
                Expression expression = (Expression) from.get(filterProperty);
                predicates.add(builder.like(expression, filterValue + "%"));
            }
        }

        select.where(predicates.toArray(new Predicate[]{}));

        if (sortField != null) {

            switch (sortOrder) {
                case ASCENDING:
                    select.orderBy(builder.asc(from.get(sortField)));
                    break;

                case DESCENDING:
                    select.orderBy(builder.desc(from.get(sortField)));
                    break;
            }
        }

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Cliente.class)));
        em.createQuery(countQuery);

        TypedQuery<Cliente> listQuery = em.createQuery(select);

        int qtde = listQuery.getResultList().size();

        return qtde;
    }
}
