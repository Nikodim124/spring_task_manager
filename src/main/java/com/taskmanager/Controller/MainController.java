package com.taskmanager.Controller;

import com.taskmanager.Exception.ResourceNotFoundException;
import com.taskmanager.Model.Appeal;
import com.taskmanager.Model.Employee;
import com.taskmanager.Model.Times;
import com.taskmanager.Repository.AppealRepository;
import com.taskmanager.Repository.EmployeeRepository;
import com.taskmanager.Repository.TimeRepository;
import com.taskmanager.User.MyUserPrincipal;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private TimeRepository timeRepository;

    @GetMapping("/home")
    public String getHome(Device device, Model model, Authentication authentication) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        Employee man = principal.getEmployee();
        if (principal.getAuthorities().toString().equals("[ROLE_MANAGER]")) {
            LocalDate now = new LocalDate();
            Appeal appeal = new Appeal();
            List<Long> empstasks = employeeRepository.employeesTasks();
            List<Employee> employees = employeeRepository.empsNotManagers();
            LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
            var weektasks = appealRepository.findByDateBetweenAndManagerAndDoneFalse(monday.toDate(), new Date(), man);
            var weektasksdone = appealRepository.findByDateBetweenAndManagerAndDoneTrue(monday.toDate(), new Date(), man);
            var notdonetasksbefore = appealRepository.findByDateLessThanAndManagerAndDoneFalse(monday.toDate(), man);
            model.addAttribute("notdonetasksbefore", notdonetasksbefore);
            model.addAttribute("emptasks", empstasks);
            model.addAttribute("employees", employees);
            model.addAttribute("man", man);
            model.addAttribute("weektasks", weektasks);
            model.addAttribute("appeal", appeal);
            model.addAttribute("weektasksdone", weektasksdone);
            return "index";
        } else {
            if (device.isMobile()) {
                return "home-mobile";
            } else {
                return "home";
            }
        }
    }

    @GetMapping("/appeals_detail/{id}")
    public String AppealDetails(Model model, @PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдена задача с данным id :: " + id));
        model.addAttribute("appeal", appeal);
        return "appeal_detail.html";
    }

    /*
    @GetMapping("/appeal/{id}")
    @ResponseBody
    public List<Appeal> updateAppealList(Authentication authentication, @RequestParam("date") Date dt) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        Employee man = principal.getEmployee();
        List<Appeal> appeals = appealRepository.findByTaskdateAndEmployeeAndTasktimeAndDoneFalseOrderByAddress(dt, man);
        return appeals;
    }
    */

    @GetMapping("/other")
    public String fragmentPage(Model model, Authentication authentication, @RequestParam("date") Date dt) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        Employee man = principal.getEmployee();
        List<Times> times = timeRepository.findAll();
        List<Appeal> appealsfirst = appealRepository.findByTaskdateAndEmployeeAndTasktimeAndDoneFalse(dt, man, times.get(0));
        List<Appeal> appealssecond = appealRepository.findByTaskdateAndEmployeeAndTasktimeAndDoneFalse(dt, man, times.get(1));
        List<Appeal> appealsthird = appealRepository.findByTaskdateAndEmployeeAndTasktimeAndDoneFalse(dt, man, times.get(2));
        List<Appeal> doneappeals = appealRepository.findByTaskdateAndEmployeeAndDoneTrue(dt, man);
        List<Appeal> appeals = new ArrayList<Appeal>();
        appeals.addAll(appealsfirst);
        appeals.addAll(appealssecond);
        appeals.addAll(appealsthird);
        model.addAttribute("appeals", appeals);
        model.addAttribute("appealsfirst", appealsfirst);
        model.addAttribute("appealssecond", appealssecond);
        model.addAttribute("appealsthird", appealsthird);
        model.addAttribute("doneappeals", doneappeals);
        return "other.html";
    }

    @GetMapping("/home/done_appeal/{id}")
    public String doneAppeal(@PathVariable(value="id") Long id) {
        appealRepository.setDoneForAppeal(id);
        return "redirect:/home";
    }
}
