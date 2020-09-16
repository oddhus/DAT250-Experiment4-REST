package no.hvl.dat110.rest;

import com.google.gson.Gson;
import no.hvl.dat110.rest.enums.OperationTypes;
import no.hvl.dat110.rest.enums.StatusResponse;
import no.hvl.dat110.rest.response.TodoResponse;
import no.hvl.dat110.rest.entities.Todo;
import no.hvl.dat110.rest.service.TodoService;

import java.util.List;


import static spark.Spark.*;


public class App {

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        TodoService todoService = new TodoService();
        todoService.setup();

        after((req, res) -> {
            res.type("application/json");
        });

        get("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            return  new TodoResponse(
                    new Gson().toJsonTree(todoService.getTodoById(id)),
                    OperationTypes.FIND).toJson();
        });

        get("/todos", (req, res) -> {
            List<Todo> todos = todoService.getAllTodos();

            return new Gson()
                    .toJson(new TodoResponse(new Gson().toJsonTree(todos), OperationTypes.FIND));
        });

        post("/todo", (req, res) -> {
            Gson gson = new Gson();
            Todo todo = gson.fromJson(req.body(), Todo.class);
            return  new TodoResponse(
                    new Gson().toJsonTree(todoService.createTodo(todo)),
                    OperationTypes.CREATE).toJson();
        });

        patch("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            Todo todo = new Gson().fromJson(req.body(), Todo.class);

            return  new TodoResponse(
                    new Gson().toJsonTree(todoService.updateTodo(id, todo)),
                    OperationTypes.UPDATE).toJson();
        });

        delete("/todo/:id", (req, res) -> {
            String id = req.params(":id");
            Boolean deleted = todoService.deleteTodo(id);

            if (!deleted) {
                return new Gson()
                        .toJson(new TodoResponse(StatusResponse.ERROR, "Could not deleted the specified todo"));
            }

            return new Gson()
                    .toJson(new TodoResponse(StatusResponse.SUCCESS, "The todo was successfully deleted"));
        });

    }


}