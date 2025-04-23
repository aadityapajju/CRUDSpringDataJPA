package com.example.CRUDDemo.Rest;



import com.example.CRUDDemo.Entity.Employee;
import com.example.CRUDDemo.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper; //used for json processing

    //quick and dirty : inject employeeDAO(contructor injection)
    @Autowired
    public EmployeeRestController(EmployeeService theemployeeService, ObjectMapper theObjectMapper){
        employeeService=theemployeeService;
        objectMapper = theObjectMapper;
    }

    //expose "/employee" endpoint and return employee list
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }


    //add mapping to get single employee by using Id
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null) {
            throw new RuntimeException("Employee Id not found");
        }
        return theEmployee;
        }

        //add mapping for post or saving new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;

    }

    //add mapping for update(PUT)
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //adding patch mapping
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String, Object> patchPayLoad){
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null) {
            throw new RuntimeException("Employee Id not found");
        }

        //throw exception if ID is in request body
        if(patchPayLoad.containsKey("id")){
            throw new RuntimeException("ID isn't allowed n request body");
        }
        Employee patchedEmployee = apply(patchPayLoad, theEmployee);
        Employee dbEmployee = employeeService.save(patchedEmployee);
        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayLoad, Employee theEmployee) {
        //Convert employee object to json object node
        ObjectNode employeeNode = objectMapper.convertValue(theEmployee, ObjectNode.class);

        //convert patchpayload map to JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayLoad, ObjectNode.class);

        //merge patch updates into employeeNode
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }
    //delete an employee by employeeId
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee Id not found");
        }
        employeeService.deleteById(employeeId);

        return "Deleted employee with Id: " + employeeId;
    }


}
