package com.taskmanager.Controller;

import com.taskmanager.Exception.ResourceNotFoundException;
import com.taskmanager.Model.*;
import com.taskmanager.Repository.*;
import com.taskmanager.User.MyUserPrincipal;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class AppealController {

    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/appeals")
    public String getAppeals(Model model, Authentication authentication) {
        LocalDate now = new LocalDate();
        LocalDate dt_start = now.withDayOfWeek(DateTimeConstants.MONDAY);
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        Employee man = principal.getEmployee();
        List<Long> empstasks = employeeRepository.employeesTasks();
        List<Employee> employees = employeeRepository.empsNotManagers();
        model.addAttribute("client", new Client());
        List<Appeal> appeals_dont_sort = this.appealRepository.findByDateBetweenAndManagerAndEmployeeNullAndDoneFalse(dt_start.toDate(), new Date(), man);
        List<Appeal> appeals_in_work = this.appealRepository.findByDateBetweenAndManagerAndEmployeeNotNullAndDoneFalse(dt_start.toDate(), new Date(), man);
        List<Appeal> appeals_done = this.appealRepository.findByDateBetweenAndManagerAndEmployeeNotNullAndDoneTrue(dt_start.toDate(), new Date(), man);
        model.addAttribute("appeals_dont_sort", appeals_dont_sort);
        model.addAttribute("appeals_in_work", appeals_in_work);
        model.addAttribute("appeals_done", appeals_done);
        List<Client> clients = clientRepository.findAll();
        List<Address> addresses = addressRepository.findAll();
        List<Times> times = timeRepository.findAll();
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts", contracts);
        model.addAttribute("times", times);
        model.addAttribute("addresses", addresses);
        model.addAttribute("clients", clients);
        model.addAttribute("appeal", new Appeal());
        model.addAttribute("employees", employees);
        model.addAttribute("empstasks", empstasks);
        return "appeals";
    }

    @PostMapping("/appeals/add")
    public String createAppeal(Model model, Appeal appeal) {
        model.addAttribute("appeals", appealRepository.save(appeal));
        return "redirect:/appeals";
    }

    @RequestMapping(value = "/appeals/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Appeal getAppealById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдена задача с данным id :: " + id));
        return appeal;
    }

    @RequestMapping(method = RequestMethod.GET, value="/appeals/set_employee")
    @ResponseBody
    public Appeal putEmployeeAppeal(@RequestParam("id") Long id,
                                    @RequestParam("emp_id") Long emp_id)
            throws ResourceNotFoundException {
        appealRepository.setEmployeeForAppeal(emp_id, id);
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдена задача с данным id :: " + id));
        return appeal;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/appeals/null_employee")
    @ResponseBody
    public Appeal setNullEmployee(@RequestParam("id") Long id)
            throws ResourceNotFoundException {
        appealRepository.setNullEmployee(id);
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдена задача с данным id :: " + id));
        return appeal;
    }

    @PostMapping("/home/set_employee")
    public String updateAppeal(Appeal appeal) {
        appealRepository.setEmployeeForAppeal(appeal.getEmployee().getId(), appeal.getId());
        return "redirect:/home";
    }
}
