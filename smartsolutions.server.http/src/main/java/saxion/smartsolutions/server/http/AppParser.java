package saxion.smartsolutions.server.http;

import saxion.smartsolutions.core.part.application.RegisterPartController;

import java.io.IOException;

public class AppParser implements HTTPRequestParser{

    private RegisterPartController = new RegisterPartController();

    @Override
    public HTTPMessage parse(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();

        switch (request.getMethod()) {
            case "GET":
                switch (request.getURI()) {
                    case "/":
                        String file = BASE_FOLDER + "/index.html";
                        if (response.setContentFromFile(file)) {
                            response.setResponseStatus("200 OK");
                        } else {
                            String page = "<html><body><h1>HTTP SERVER ERROR</h1></body></html>";
                            response.setContentFromString(page, "text/html");
                            response.setResponseStatus("500 Internal Server Error");
                        }
                        break;
                    default:
                        String name = BASE_FOLDER + request.getURI();
                        if(response.setContentFromFile(name)) {
                            response.setResponseStatus("200 OK");
                        } else {
                            response.setContentFromString(ERROR, "text/html");
                            response.setResponseStatus("404 NOT FOUND");
                        }
                }
                break;
            case "PUT":
                switch (request.getURI()) {
                    case "/new_part":
                        request.getContentInJSON();

                }
                break;
            default:
        }
        return response;
    }


}
