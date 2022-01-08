package saxion.smartsolutions.server;


import org.json.JSONArray;
import org.json.JSONObject;
import saxion.smartsolutions.core.model.application.RegisterModelController;
import saxion.smartsolutions.core.model.application.SearchModelController;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.part.application.RegisterPartController;
import saxion.smartsolutions.core.part.application.SearchPartController;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.domain.Stock;
import saxion.smartsolutions.core.property.application.SearchPropertiesController;
import saxion.smartsolutions.core.property.application.RegisterPropertyController;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.value.Designation;
import saxion.smartsolutions.server.http.HTTPMessage;
import saxion.smartsolutions.server.http.HTTPRequestParser;

import java.io.*;
import java.util.*;

public class AppParser implements HTTPRequestParser {


    public static File decodeImage(String base64, String path) {

        File image = null;
        byte[] result = Base64.getDecoder().decode(base64);
        try (OutputStream stream = new FileOutputStream(path)) {
            stream.write(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public HTTPMessage parse(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        System.out.println(request.getMethod() + " - " + request.getURI());
        switch (request.getMethod()) {
            // GET
            case "GET":
                switch (request.getURI()) {
                    // Main Menu
                    case "/":
                        String file = BASE_FOLDER + "/index.html";
                        if (response.setContentFromFile(file)) {
                            response.setResponseStatus("200 OK");
                        } else {
                            String page = "HTTP SERVER ERROR";
                            response.setContentFromString(page, "text/plain");
                            response.setResponseStatus("500 Internal Server Error");
                        }
                        break;
                    case "/properties":
                        response = listProperties(request);
                        break;
                    case "/models":
                        response = listModels(request);
                        break;
                    // Other file resources
                    default:
                        String name = BASE_FOLDER + request.getURI();
                        if (response.setContentFromFile(name)) {
                            response.setResponseStatus("200 OK");
                        } else {
                            // Search
                            if (request.getURI().startsWith("/parts/") && isNumeric(request.getURI().substring(7))) {
                                response = getPart(request);
                            } else if (request.getURI().startsWith("/models/")) {
                                response = getModel(request);
                            } else if (request.getURI().startsWith("/part.html")) {
                                response.setContentFromFile(BASE_FOLDER + "/part.html");
                                response.setResponseStatus("200 Ok");
                            } else if (request.getURI().startsWith("/model.html")) {
                                response.setContentFromFile(BASE_FOLDER + "/model.html");
                                response.setResponseStatus("200 Ok");
                            } else {
                                response.setContentFromString("404 Not Found", "text/plain");
                                response.setResponseStatus("404 Not Found");
                            }
                        }
                }
                break;
            // POST
            case "POST":
                switch (request.getURI()) {
                    case "/parts":
                        response = registerPart(request);
                        break;
                    case "/models":
                        response = registerModel(request);
                        break;
                    case "/properties":
                        response = registerProperty(request);
                        break;
                    case "/search_part":
                        response = searchPart(request);
                        break;
                    default:/*
                        if(request.getURI().startsWith("/parts/") && request.getURI().endsWith("/pictures")
                            && isNumeric(request.getURI().substring(7, request.getURI().length() - 10))) {
                            response = addPictures(request);
                        }*/
                        response.setResponseStatus("405 Method Not Allowed");
                        response.setContentFromString("405 Method Not Allowed", "text/plain");
                }

                break;
            default:
        }
        return response;
    }

    private HTTPMessage listModels(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        SearchModelController ctrl = new SearchModelController();
        Iterable<Model> props = ctrl.listModels();
        JSONObject json = new JSONObject();
        for(Model m : props) {
            json.accumulate("models", m.toJSON());
        }
        response.setContentFromString(json.toString(), "application/json");
        response.setResponseStatus("200 Ok");
        return response;
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private HTTPMessage registerProperty(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        RegisterPropertyController ctrl = new RegisterPropertyController();
        JSONObject json = new JSONObject(request.getString());
        try {
            Property p = ctrl.registerProperty(Designation.valueOf(json.getString("name")));
            response.setContentFromString(String.valueOf(p.identity()), "text/plain");
            response.setResponseStatus("201 Created");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            response.setContentFromString("<p>" + e.getMessage() + "</p>", "text/html");
            response.setResponseStatus("409 Conflict");
        }
        return response;
    }

    private HTTPMessage listProperties(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        SearchPropertiesController ctrl = new SearchPropertiesController();
        Iterable<Property> props = ctrl.listProperties();
        JSONObject json = new JSONObject();
        for(Property p : props) {
            json.accumulate("properties", p.toJSON());
        }
        response.setContentFromString(json.toString(), "application/json");
        response.setResponseStatus("200 Ok");
        return response;
    }

    private HTTPMessage registerPart(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        RegisterPartController partctrl = new RegisterPartController();
        JSONObject json = new JSONObject(request.getString());
        try {

            Part p = partctrl.registerPart(Designation.valueOf(json.getString("name")), PartNumber.valueOf(json.getString("part_number")),
                    Designation.valueOf(json.getString("brand")), ModelNumber.valueOf(json.getString("model_number")), Stock.valueOf((json.getInt("stock"))));


            Set<File> pics = new HashSet<>();
            JSONArray arr = json.getJSONArray("pictures");
            for (int i = 0; i < arr.length(); i++) {
                String str = arr.getString(i).split(";")[1].split(",")[1];
                String path = "/home/treeman/school/smartsolutions/tmp/" + p.identity() + "_picture_" + i + ".png";
                pics.add(decodeImage(str, path));
                i++;
            }
            response.setContentFromString(String.valueOf(p.identity()), "text/plain");
            response.setResponseStatus("201 Created");
        } catch (IllegalArgumentException e) {
            response.setContentFromString(e.getMessage(), "text/plain");
            response.setResponseStatus("409 Conflict");
        }
        return response;
    }

    private HTTPMessage registerModel(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        RegisterModelController modelctrl = new RegisterModelController();
        JSONObject json = new JSONObject(request.getString());
        try {
            JSONArray arr = json.getJSONArray("properties");

            Map<Designation, Designation> props = new HashMap<>();
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                props.put(Designation.valueOf(obj.getString("name")), Designation.valueOf(obj.getString("value")));
            }
            modelctrl.registerModel(Designation.valueOf(json.getString("name")), ModelNumber.valueOf(json.getString("model_number")),
                    Designation.valueOf(json.getString("type")), Designation.valueOf(json.getString("brand")), props);

            response.setResponseStatus("201 Created");
        } catch (IllegalArgumentException e) {
            response.setContentFromString(e.getMessage(), "text/plain");
            response.setResponseStatus("409 Conflict");
        }
        return response;
    }

    private HTTPMessage searchPart(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        SearchPartController searchpctrl = new SearchPartController();
        JSONObject json = new JSONObject(request.getString());

        Designation gname = null;
        ModelNumber model = null;
        PartNumber part = null;
        if (json.getString("name") != null && !json.getString("name").trim().isEmpty()) {
            gname = Designation.valueOf(json.getString("name"));
        }
        if (json.getString("model_number") != null && !json.getString("model_number").trim().isEmpty()) {
            model = ModelNumber.valueOf(json.getString("model_number"));
        }
        if (json.getString("part_number") != null && !json.getString("part_number").trim().isEmpty()) {
            part = PartNumber.valueOf(json.getString("part_number"));
        }

        Map<Designation, Designation> props = new HashMap<>();
        if(json.getJSONArray("properties").get(0).getClass() == JSONObject.class) {
            JSONArray arr = json.getJSONArray("properties");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                props.put(Designation.valueOf(obj.getString("name")), Designation.valueOf(obj.getString("value")));
            }
        }


        if (gname == null && model == null && part == null && props.isEmpty()) {
            response.setResponseStatus("200 Ok");
            return response;
        }
        Iterable<Part> ret = searchpctrl.searchPart(gname, model, part, props);



        JSONObject res = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Part p : ret) {

            arr.put(p.toJSON());
        }
        System.out.println(arr);
        res.put("search_results", arr);
        response.setContentFromString(res.toString(), "application/json");
        response.setResponseStatus("200 Ok");
        return response;
    }

    private HTTPMessage getPart(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        SearchPartController searchpctrl = new SearchPartController();
        try {

            Part p = searchpctrl.findByID(Integer.parseInt(request.getURI().substring(7))).get();
            response.setContentFromString(p.toJSON().toString(), "application/json");

            response.setResponseStatus("200 Ok");
        } catch (NoSuchElementException e) {
            response.setContentFromString("404 Not Found", "text/plain");
            response.setResponseStatus("404 Not Found");
        }
        return response;
    }

    private HTTPMessage getModel(HTTPMessage request) {
        HTTPMessage response = new HTTPMessage();
        SearchModelController searchmctrl = new SearchModelController();
        try {
            System.out.printf(request.getURI().substring(9));
            Model m = searchmctrl.findByModelNumber(ModelNumber.valueOf(request.getURI().substring(8))).get();
            System.out.println(m.toJSON().toString());
            response.setContentFromString(m.toJSON().toString(), "application/json");

            response.setResponseStatus("200 Ok");
        } catch (NoSuchElementException e) {
            response.setContentFromString("404 Not Found", "text/plain");
            response.setResponseStatus("404 Not Found");
        }
        return response;
    }
}
