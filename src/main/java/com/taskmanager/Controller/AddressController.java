package com.taskmanager.Controller;

import com.taskmanager.Model.Address;
import com.taskmanager.Model.Client;
import com.taskmanager.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/addresses")
    public String getAddress(Model model) {
        List<Address> addresses = this.addressRepository.findAll();
        model.addAttribute("address", new Address());
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @RequestMapping(value = "/addresses/add_address", method = RequestMethod.POST)
    public String addNewAddress(@RequestParam("city") String ct,
                                @RequestParam("street") String st,
                                @RequestParam("build") String bl,
                                @RequestParam("ent") String ent,
                                @RequestParam("lat") String lat,
                                @RequestParam("lon") String lon) {
        Address address = new Address(ct, st, bl, ent, lat, lon);
        addressRepository.save(address);
        return "redirect:/addresses";
    }
}
