package com.taskmanager.Controller;

import com.taskmanager.Model.Client;
import com.taskmanager.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String getClients(Model model) {
        List<Client> clients = this.clientRepository.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("client", new Client());
        return "clients";
    }

    @RequestMapping(value = "/clients/add", method = RequestMethod.POST)
    @ResponseBody
    public Client createClient(@RequestParam("firstname") String fn,
                                     @RequestParam("lastname") String ln,
                                     @RequestParam("upname") String un,
                                     @RequestParam("phone") String ph,
                                     @RequestParam("email") String em) {
        Client client = new Client(fn, ln, un, ph, em);
        clientRepository.save(client);
        return client;
    }
}
