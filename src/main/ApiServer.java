package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Complaint;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

public class ApiServer {

    private static Queue<Complaint> complaints;
    private static int complaintId = 101;

    public static void start(Queue<Complaint> queue) throws IOException {
        complaints = queue;

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/submit", ApiServer::submitComplaint);
        server.createContext("/complaints", ApiServer::getComplaints);
        server.createContext("/process", ApiServer::processComplaint);
        server.createContext("/next", ApiServer::getNextComplaint);
        server.start();

        System.out.println("CampusFix Web API: http://localhost:8081");
    }

    private static void submitComplaint(HttpExchange exchange) throws IOException {
        cors(exchange);
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, "{\"error\":\"POST required\"}", 405);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> data = parse(body);

        String name = data.get("name");
        String category = data.get("category");
        String description = data.get("description");

        if (blank(name) || blank(category) || blank(description)) {
            send(exchange, "{\"error\":\"All fields are required\"}", 400);
            return;
        }

        Complaint complaint = new Complaint(complaintId++, name, category, description);
        complaints.offer(complaint);
        send(exchange, "{\"message\":\"Complaint submitted\",\"id\":" + complaint.getComplaintId() + "}", 200);
    }

    private static void getComplaints(HttpExchange exchange) throws IOException {
        cors(exchange);
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, "{\"error\":\"GET required\"}", 405);
            return;
        }

        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        for (Complaint c : complaints) {
            if (!first) json.append(',');
            json.append(toJson(c));
            first = false;
        }
        json.append(']');
        send(exchange, json.toString(), 200);
    }

    private static void getNextComplaint(HttpExchange exchange) throws IOException {
        cors(exchange);
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, "{\"error\":\"GET required\"}", 405);
            return;
        }
        if (complaints.isEmpty()) {
            send(exchange, "{\"error\":\"No complaints in queue\"}", 404);
            return;
        }
        send(exchange, toJson(complaints.peek()), 200);
    }

    private static void processComplaint(HttpExchange exchange) throws IOException {
        cors(exchange);
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, "{\"error\":\"POST required\"}", 405);
            return;
        }
        if (complaints.isEmpty()) {
            send(exchange, "{\"error\":\"No complaints in queue\"}", 404);
            return;
        }

        Complaint complaint = complaints.poll();
        complaint.setStatus("Resolved");
        send(exchange, "{\"message\":\"Complaint processed\",\"id\":" + complaint.getComplaintId() + "}", 200);
    }

    private static String toJson(Complaint c) {
        return "{" +
                "\"id\":" + c.getComplaintId() + "," +
                "\"name\":\"" + escape(c.getStudentName()) + "\"," +
                "\"category\":\"" + escape(c.getCategory()) + "\"," +
                "\"description\":\"" + escape(c.getDescription()) + "\"," +
                "\"status\":\"" + escape(c.getStatus()) + "\"" +
                "}";
    }

    private static Map<String, String> parse(String body) {
        Map<String, String> data = new LinkedHashMap<>();
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                data.put(
                        URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                );
            }
        }
        return data;
    }

    private static boolean blank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static void cors(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void send(HttpExchange exchange, String response, int status) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }
}
