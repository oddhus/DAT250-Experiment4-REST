package no.hvl.dat110.rest.dao;

import no.hvl.dat110.rest.entities.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoDAO {

    Optional<Todo> getTodoById(String id);

    List<Todo> getAllTodos();

    Optional<Todo> createTodo(Todo todo);

    Optional<Todo> updateTodo(String id, Todo todo);

    Boolean deleteTodo(String id);



}
