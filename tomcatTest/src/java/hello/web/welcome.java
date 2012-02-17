/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hello.web;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PKMubasharH
 */
@WebService(serviceName = "welcome")
public class welcome {

    /** This is a sample web service operation */
    @WebMethod(operationName = "welcome")
    public String welcome(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
