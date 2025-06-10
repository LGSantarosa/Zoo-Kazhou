package zoo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import zoo.service.ZooService;
import zoo.animais.Animal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import zoo.model.Funcionario;
import zoo.model.Ocorrencia;
import zoo.model.Tarefa;
import zoo.web.adapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class ZooServer {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private static final ZooService zooService = new ZooService();

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // API Endpoints
        server.createContext("/api/animais", new AnimaisHandler());
        server.createContext("/api/tarefas", new TarefasHandler());
        server.createContext("/api/funcionarios/registrar", new RegistroFuncionarioHandler());
        server.createContext("/api/funcionarios/login", new LoginFuncionarioHandler());

        // Frontend (Static files)
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Servidor Zoo Kazhou rodando na porta " + port);
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css")) return "text/css; charset=UTF-8";
        if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
        if (path.endsWith(".json")) return "application/json; charset=UTF-8";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        if (path.endsWith(".svg")) return "image/svg+xml";
        return "application/octet-stream";
    }
    
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedPath = exchange.getRequestURI().getPath();
            if ("/".equals(requestedPath)) {
                requestedPath = "/index.html";
            }
            String resourcePath = "/static" + requestedPath;

            URL resource = getClass().getResource(resourcePath);
            if (resource == null) {
                sendEmptyResponse(exchange, 404);
                return;
            }

            try (InputStream is = resource.openStream()) {
                byte[] fileBytes = is.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", getContentType(requestedPath));
                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
                sendJsonResponse(exchange, Map.of("erro", "Não foi possível carregar o arquivo"), 500);
            }
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, Object data, int statusCode) throws IOException {
        String jsonResponse = gson.toJson(data);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void sendEmptyResponse(HttpExchange exchange, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, -1);
    }

    private static <T> T parseRequestBody(HttpExchange exchange, Class<T> clazz) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, clazz);
        }
    }

    static class AnimaisHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                String path = exchange.getRequestURI().getPath();
                String[] pathSegments = path.split("/");
                String method = exchange.getRequestMethod();

                // Rota: /api/animais
                if (pathSegments.length == 3 && "GET".equals(method)) {
                    List<Animal> animais = zooService.getAnimais();
                    sendJsonResponse(exchange, animais, 200);
                    return;
                }
                
                // Rota: /api/animais
                if (pathSegments.length == 3 && "POST".equals(method)) {
                    Animal novoAnimal = parseRequestBody(exchange, Animal.class);
                    novoAnimal.setId(java.util.UUID.randomUUID().toString());
                    zooService.addAnimal(novoAnimal);
                    sendJsonResponse(exchange, novoAnimal, 201);
                    return;
                }

                // Rota: /api/animais/{id}
                if (pathSegments.length == 4 && "GET".equals(method)) {
                    String id = pathSegments[3];
                    Animal animal = zooService.getAnimalById(id);
                    if (animal != null) {
                        sendJsonResponse(exchange, animal, 200);
                    } else {
                        sendJsonResponse(exchange, Map.of("erro", "Animal não encontrado"), 404);
                    }
                    return;
                }
                
                // Rota: /api/animais/{id}
                if (pathSegments.length == 4 && "DELETE".equals(method)) {
                    String id = pathSegments[3];
                    try {
                        zooService.removeAnimal(id);
                        sendEmptyResponse(exchange, 204);
                    } catch (Exception e) {
                         sendJsonResponse(exchange, Map.of("erro", e.getMessage()), 404);
                    }
                    return;
                }

                // Rota: /api/animais/{id}/ocorrencias
                if (pathSegments.length == 5 && "ocorrencias".equals(pathSegments[4]) && "POST".equals(method)) {
                    String animalId = pathSegments[3];
                    Ocorrencia ocorrencia = parseRequestBody(exchange, Ocorrencia.class);
                    try {
                        zooService.registrarOcorrencia(animalId, ocorrencia.getDescricao());
                        sendJsonResponse(exchange, ocorrencia, 201);
                    } catch (Exception e) {
                        sendJsonResponse(exchange, Map.of("erro", e.getMessage()), 404);
                    }
                    return;
                }
                
                sendEmptyResponse(exchange, 404); // Not Found
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(exchange, Map.of("erro", "Erro interno no servidor"), 500);
            }
        }
    }
    
    static class TarefasHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
             try {
                String path = exchange.getRequestURI().getPath();
                String[] pathSegments = path.split("/");
                String method = exchange.getRequestMethod();

                // Rota: /api/tarefas
                if (pathSegments.length == 3 && "GET".equals(method)) {
                    sendJsonResponse(exchange, zooService.getTarefas(), 200);
                    return;
                }
                
                 // Rota: /api/tarefas
                if (pathSegments.length == 3 && "POST".equals(method)) {
                    Tarefa novaTarefa = parseRequestBody(exchange, Tarefa.class);
                    zooService.addTarefa(novaTarefa);
                    sendJsonResponse(exchange, novaTarefa, 201);
                    return;
                }

                // Rota: /api/tarefas/{id}/concluir
                if (pathSegments.length == 5 && "concluir".equals(pathSegments[4]) && "PUT".equals(method)) {
                    String id = pathSegments[3];
                    zooService.concluirTarefa(id);
                    sendEmptyResponse(exchange, 204);
                    return;
                }

                sendEmptyResponse(exchange, 404); // Not Found
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(exchange, Map.of("erro", "Erro interno no servidor"), 500);
            }
        }
    }
    
    static class RegistroFuncionarioHandler implements HttpHandler {
         @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendEmptyResponse(exchange, 405); // Method Not Allowed
                return;
            }

            try {
                Funcionario novoFuncionario = parseRequestBody(exchange, Funcionario.class);
                Funcionario funcionarioRegistrado = zooService.registrarFuncionario(novoFuncionario.getLogin(), novoFuncionario.getSenha());
                sendJsonResponse(exchange, funcionarioRegistrado, 201);
            } catch (Exception e) {
                sendJsonResponse(exchange, Map.of("erro", e.getMessage()), 400); // Bad Request
            }
        }
    }

    static class LoginFuncionarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendEmptyResponse(exchange, 405); // Method Not Allowed
                return;
            }

            try {
                Funcionario credenciais = parseRequestBody(exchange, Funcionario.class);
                Funcionario funcionario = zooService.autenticarFuncionario(credenciais.getLogin(), credenciais.getSenha());
                if (funcionario != null) {
                    sendJsonResponse(exchange, Map.of("mensagem", "Login bem-sucedido!", "funcionarioId", funcionario.getId()), 200);
                } else {
                    sendJsonResponse(exchange, Map.of("erro", "Credenciais inválidas"), 401); // Unauthorized
                }
            } catch (Exception e) {
                 sendJsonResponse(exchange, Map.of("erro", "Erro interno no servidor"), 500);
            }
        }
    }
} 