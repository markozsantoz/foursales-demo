package br.com.foursales.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequestMapping("/foursales-demo/api/v1")
public abstract class BaseController {

    protected URI getLocation(Object... uriVariableValues){
        return fromCurrentRequest().path("/{id}").buildAndExpand(uriVariableValues).toUri();
    }
}
