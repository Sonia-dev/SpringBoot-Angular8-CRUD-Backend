package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;


@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository ;
	
	//get employees
	@GetMapping("/employees")
	public List <Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	
	//create employee rest api
	
	@PostMapping("/employees")
	public Employee CreateEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
		}

	//get employee by id rest api
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee>getEmployeeById(@PathVariable Long id ){
		
		Employee employee = employeeRepository.findById(id)
		.orElseThrow(()->new ResourceNotFoundException("employee not exist with id " + id));
		return ResponseEntity.ok(employee);
				}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee>updateEmployee(@PathVariable Long id ,@RequestBody Employee detailEmployee)
	{
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("employee not exist with id" +id));
		
		employee.setFirstName(detailEmployee.getFirstName());
		employee.setLastName(detailEmployee.getLastName());
		employee.setEmail(detailEmployee.getEmail());
		
		Employee updatedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
		
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("employee not exist with id" +id));
		
		employeeRepository.delete(employee);
		Map<String,Boolean>response=new HashMap<>();
		
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
