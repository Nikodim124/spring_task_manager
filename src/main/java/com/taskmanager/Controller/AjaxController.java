package com.taskmanager.Controller;

import com.taskmanager.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AjaxController {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(method = RequestMethod.GET, value="/addresses/search")
    public boolean addressExist(@RequestParam("city") String city,
                                @RequestParam("street") String street,
                                @RequestParam("build") String build) {
        var ex = addressRepository.existsAddressByCityAndStreetAndBuild(city, street, build);
        return ex;
    }
}
