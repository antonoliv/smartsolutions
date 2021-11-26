package saxion.smartsolutions.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.part.application.RegisterPartController;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.value.Designation;
import saxion.smartsolutions.server.http.HTTPMessage;
import saxion.smartsolutions.server.http.HTTPRequestParser;

public class AppParser implements HTTPRequestParser {

    private RegisterPartController ctrl = new RegisterPartController();

    @Override
    public HTTPMessage parse(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        System.out.println(request.getMethod() + " - " + request.getURI());
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
            case "POST":
                switch (request.getURI()) {
                    case "/parts":

                        System.out.println(request.getString());
                        JsonObject json = (JsonObject) JsonParser.parseString(request.getString());
                        PersistenceContext.repositories().modelRepository().findByModelNumber(ModelNumber.valueOf("test"));
                        ctrl.registerPart(Designation.valueOf(json.get("name").getAsString()), PartNumber.valueOf(json.get("partNumber").getAsString()),
                                Designation.valueOf(json.get("brand").getAsString()), ModelNumber.valueOf(json.get("modelNumber").getAsString()));

                        response.setResponseStatus("200 OK");
                }

                break;
            default:
        }
        return response;
    }
}
