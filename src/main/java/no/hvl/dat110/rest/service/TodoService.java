package no.hvl.dat110.rest.service;

import no.hvl.dat110.rest.dao.TodoDAO;
import no.hvl.dat110.rest.entities.Todo;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class TodoService implements TodoDAO {

    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Todo> getTodoById(String id) {
        return Optional.ofNullable(em.find(Todo.class, id));
    }

    @Override
    public List<Todo> getAllTodos() {
        Query query = em.createQuery("SELECT t from Todo t");
        return query.getResultList();
    }

    @Override
    public Optional<Todo> createTodo(Todo newTodo) {
        if (newTodo.getDescription() == null || newTodo.getDescription().isEmpty()
                || newTodo.getTitle() == null || newTodo.getTitle().isEmpty()){
            return Optional.empty();
        }

        Todo todo = new Todo();
        todo.setTitle(newTodo.getTitle());
        todo.setDescription(newTodo.getDescription());

        em.getTransaction().begin();
        em.persist(todo);
        em.getTransaction().commit();

        return Optional.ofNullable(em.find(Todo.class, todo.getId()));
    }

    @Override
    public Optional<Todo> updateTodo(String id, Todo updateTodo) {
        Optional<Todo> todo = Optional.ofNullable(em.find(Todo.class, id));

        if (!todo.isPresent()) {
            return todo;
        }

        if (updateTodo.getTitle() != null && !updateTodo.getTitle().isEmpty()) {
            todo.get().setTitle(updateTodo.getTitle());
        }
        if (updateTodo.getDescription() != null && !updateTodo.getDescription().isEmpty()) {
            todo.get().setTitle(updateTodo.getDescription());
        }

        return todo;
    }


    @Override
    public Boolean deleteTodo(String id) {
        Optional<Todo> todo = Optional.ofNullable(em.find(Todo.class, id));

        if (!todo.isPresent()) {
            return false;
        }

        em.getTransaction().begin();
        Query q = em.createQuery("DELETE from Todo t WHERE t.id = :id");
        q.setParameter("id", id);
        int deleted = q.executeUpdate();
        em.getTransaction().commit();

        return deleted == 1;

    }

    public void setup(){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
    }


}
