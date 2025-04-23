package com.example.CRUDDemo.Service;


import com.example.CRUDDemo.DAO.EmployeeRepsitory;
import com.example.CRUDDemo.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepsitory employeeRepsitory;


    //constructor Injection
    @Autowired
    public EmployeeServiceImpl(EmployeeRepsitory theEmployeeRepsitory){
        employeeRepsitory = theEmployeeRepsitory;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepsitory.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> byId = employeeRepsitory.findById(theId);
        Employee theEmployee = null;
        if(byId.isPresent()){
            theEmployee = byId.get();
        }else{
            throw new RuntimeException("Couldn't find employee with ID: "+theId);
        }
        return theEmployee;
    }


    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepsitory.save(theEmployee);
    }


    @Override
    public void deleteById(int theId) {
        employeeRepsitory.deleteById(theId);

    }
}
