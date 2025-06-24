
package com.game;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        // Configure port
        port(4567);
        
        // Serve static files from frontend directory
        staticFiles.externalLocation(System.getProperty("user.dir") + "/../frontend");
        
        // Simple API endpoint
        get("/api/status", (req, res) -> "Game server is running");
        
        // Start the server
        init();
        System.out.println("Game server running at http://localhost:4567");
    }
}
