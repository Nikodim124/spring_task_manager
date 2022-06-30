package com.taskmanager.Controller;

import com.taskmanager.Exception.ResourceNotFoundException;
import com.taskmanager.Model.Address;
import com.taskmanager.Model.Client;
import com.taskmanager.Model.Contract;
import com.taskmanager.Model.Employee;
import com.taskmanager.Repository.AddressRepository;
import com.taskmanager.Repository.ClientRepository;
import com.taskmanager.Repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;


    @RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contract getContractById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найден договор с данным id :: " + id));
        return contract;
    }

    @GetMapping("/contracts")
    public String getContracts(Model model) {
        List<Contract> contracts = contractRepository.findAll();
        List<Client> clients = clientRepository.findAll();
        List<Address> addresses = addressRepository.findAll();
        model.addAttribute("contract", new Contract());
        model.addAttribute("clients", clients);
        model.addAttribute("addresses", addresses);
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @RequestMapping(value = "/contracts/add", method = RequestMethod.POST)
    public String createContract(Model model, Contract contract){

        //Contract contract = new Contract(address, appart, client, hi, tv, ph, false);
        contractRepository.save(contract);
        return "redirect:/contracts";
    }
}
