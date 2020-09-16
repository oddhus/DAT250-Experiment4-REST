package no.hvl.dat110.rest;

import com.google.gson.Gson;
import no.hvl.dat110.rest.enums.StatusResponse;
import no.hvl.dat110.rest.response.StandardResponse;
import no.hvl.dat110.rest.todos.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static spark.Spark.*;


public class App {

    static List<Todo> todos = null;

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        todos = new ArrayList<>();

        after((req, res) -> {
            res.type("application/json");
        });

        get("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            Optional<Todo> todo = todos.stream().filter(x -> x.getId().equals(id)).findFirst();
            if (!todo.isPresent()) {
                return new Gson()
                        .toJson(new StandardResponse(StatusResponse.ERROR, "Could not find a todo with the specified id"));
            }

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(todo)));
        });

        get("/todos", (req, res) -> new Gson()
                .toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(todos))));

        post("/todo", (req, res) -> {
            Gson gson = new Gson();
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todo.setId(UUID.randomUUID().toString());

            todos.add(todo);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(todo)));
        });

        patch("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            Optional<Todo> todo = todos.stream().filter(x -> x.getId().equals(id)).findFirst();

            if (!todo.isPresent()) {
                return new Gson()
                        .toJson(new StandardResponse(StatusResponse.ERROR, "Could not find a todo with the specified id"));
            }

            Todo updatedTodo = new Gson().fromJson(req.body(), Todo.class);

            if (updatedTodo.getDescription() != null && !todo.get().getDescription().equals(updatedTodo.getDescription())){
                todo.get().setDescription(updatedTodo.getDescription());
            }
            if (updatedTodo.getTitle() != null && !todo.get().getTitle().equals(updatedTodo.getTitle())){
                todo.get().setTitle(updatedTodo.getTitle());
            }

            return new Gson()
                    .toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(todo)));
        });

        delete("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            Optional<Todo> todo = todos.stream().filter(x -> x.getId().equals(id)).findFirst();

            if (!todo.isPresent()) {
                return new Gson()
                        .toJson(new StandardResponse(StatusResponse.ERROR, "Could not find a todo with the specified id"));
            }

            todos.remove(todo.get());

            return new Gson()
                    .toJson(new StandardResponse(StatusResponse.SUCCESS, "The todo was successfully deleted"));
        });
    }

}