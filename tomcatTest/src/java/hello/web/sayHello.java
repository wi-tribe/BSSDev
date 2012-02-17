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
@WebService(serviceName = "sayHello")
public class sayHello {

    /** This is a sample web service operation */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
        @WebMethod(operationName = "hey")
    public String hey(@WebParam(name = "name") String txt) {
        return "hey " + txt + " !";
    }
}
