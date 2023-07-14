package org.dimensa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
    /**
     *
     * @return status code 200, to inform that the server is running
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void health() {}
}
