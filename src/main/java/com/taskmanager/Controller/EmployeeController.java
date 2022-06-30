package com.taskmanager.Controller;

import com.taskmanager.Exception.ResourceNotFoundException;
import com.taskmanager.Model.Employee;
import com.taskmanager.Model.Post;
import com.taskmanager.Repository.EmployeeRepository;
import com.taskmanager.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/employees")
    public String getEmployee(Model model) {
        List<Employee> employees = this.employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "emps";
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найден сотрудник с данным id :: " + id));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
                  @RequestBody Employee employeeDetails) throws ResourceNotFoundException{

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найден сотрудник с данным id :: " + id));

        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());
        employee.setUpname(employeeDetails.getUpname());
        employee.setPost(employeeDetails.getPost());
        employee.setPhone(employeeDetails.getPhone());
        employee.setEmail(employeeDetails.getEmail());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }

    @DeleteMapping("/employee/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable("id") Long id)
        throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найден сотрудник с данным id :: " + id));

        this.employeeRepository.delete(employee);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }
}
