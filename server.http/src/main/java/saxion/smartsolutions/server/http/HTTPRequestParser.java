/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxion.smartsolutions.server.http;

public interface HTTPRequestParser {

    String BASE_FOLDER = "www";

    /**
     * Receives a http message request and returns a http message response
     *
     * @param request
     * @return
     */
    HTTPMessage parse(HTTPMessage request);
}
