package saxion.smartsolutions.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.application.RegisterModelController;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.part.application.RegisterPartController;
import saxion.smartsolutions.core.part.application.SearchPartController;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.value.Designation;
import saxion.smartsolutions.server.http.HTTPMessage;
import saxion.smartsolutions.server.http.HTTPRequestParser;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class AppParser implements HTTPRequestParser {

    private RegisterPartController partctrl = new RegisterPartController();
    private RegisterModelController modelctrl = new RegisterModelController();
    private SearchPartController searchpctrl = new SearchPartController();

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
                        if (response.setContentFromFile(name)) {
                            response.setResponseStatus("200 OK");
                        } else {
                            if(request.getURI().startsWith("/search_part")) {
                                String uri = request.getURI();
                                if(uri.charAt(12) != '?') {
                                    response.setContentFromString(ERROR, "text/html");
                                    response.setResponseStatus("400 Bad Request");
                                } else {

                                    String data[] = uri.substring(13).split("&");
                                    Map<String, String> vars = new HashMap<>();
                                    for (String line : data) {
                                        String tmp[] = line.split("=");
                                        vars.put(tmp[0], tmp[1]);
                                    }
                                    Designation gname = null;
                                    ModelNumber model = null;
                                    PartNumber part = null;
                                    if (vars.get("name") != null) {
                                        gname = Designation.valueOf(vars.get("name"));
                                    }
                                    if (vars.get("model") != null) {
                                        model = ModelNumber.valueOf(vars.get("model"));
                                    }
                                    if (vars.get("part") != null) {
                                        part = PartNumber.valueOf(vars.get("part"));
                                    }
                                    Iterable<Part> ret = searchpctrl.searchPart(gname, model, part);
                                    response.setResponseStatus("200 Ok");
                                    System.out.println(ret.toString());
                                }
                            } else {
                                response.setContentFromString(ERROR, "text/html");
                                response.setResponseStatus("404 NOT FOUND");
                            }
                        }
                }
                break;
            case "POST":
                JsonObject json;
                switch (request.getURI()) {
                    case "/parts":

                        json = (JsonObject) JsonParser.parseString(request.getString());
                        try {
                            partctrl.registerPart(Designation.valueOf(json.get("name").getAsString()), PartNumber.valueOf(json.get("partNumber").getAsString()),
                                    Designation.valueOf(json.get("brand").getAsString()), ModelNumber.valueOf(json.get("modelNumber").getAsString()));

                            response.setResponseStatus("201 Created");
                        } catch (IllegalArgumentException e) {
                            response.setContentFromString("<p>" + e.getMessage() + "</p>", "text/html");
                            response.setResponseStatus("409 Conflict");
                        }
                        break;
                    case "/models":
                        json = (JsonObject) JsonParser.parseString(request.getString());
                        try {
                            modelctrl.registerModel(Designation.valueOf(json.get("name").getAsString()), ModelNumber.valueOf(json.get("modelNumber").getAsString()),
                                    Designation.valueOf(json.get("device").getAsString()), Designation.valueOf(json.get("brand").getAsString()));

                            response.setResponseStatus("201 Created");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Test");
                            response.setContentFromString("<p>" + e.getMessage() + "</p>", "text/html");
                            response.setResponseStatus("409 Conflict");
                        }
                }

                break;
            default:
        }
        return response;
    }
}
