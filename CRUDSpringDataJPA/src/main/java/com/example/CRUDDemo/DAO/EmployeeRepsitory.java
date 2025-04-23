package com.example.CRUDDemo.DAO;

import com.example.CRUDDemo.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepsitory extends JpaRepository<Employee, Integer> { //<enity, primary key>

}
