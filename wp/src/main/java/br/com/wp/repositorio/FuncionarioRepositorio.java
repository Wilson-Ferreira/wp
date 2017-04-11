/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Funcionario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class FuncionarioRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public List<Funcionario> buscarTodosFuncionarios() throws Exception {

        return em.createQuery("Select f From Funcionario f").getResultList();
    }

    public Funcionario buscarFuncionarioPorId(long id) throws Exception {

        return em.find(Funcionario.class, id);
    }

    public void salvarAlterarFuncionario(Funcionario funcionario) throws Exception {

        if (funcionario.getId() == null) {
            System.out.println("persiste");
            em.persist(funcionario);
        } else {
             System.out.println("merge");
            em.merge(funcionario);
        }
    }

    public void excluirFuncionario(Funcionario funcionario) throws Exception {

        Funcionario funcionarioParaExcluir = em.find(Funcionario.class, funcionario.getId());
        em.remove(funcionarioParaExcluir);
    }

    public List<Funcionario> buscarFuncionariosNaoSaoUsuarios()throws Exception {
       
        return em.createQuery("Select f From Funcionario f where f.id <> all(Select u.funcionario.id from Usuario u)")
                .getResultList();
    }

    public List<Funcionario> buscarFuncionarioPorCargo(String cargo_garcon)throws Exception {
       System.out.println("cargo repo "+cargo_garcon);
        return em.createQuery("Select f From Funcionario f where f.cargo.cargo= :cargo_garcon and f.id = ANY(Select u.funcionario.id from Usuario u)")
                .setParameter("cargo_garcon", cargo_garcon).getResultList();
    }
}
