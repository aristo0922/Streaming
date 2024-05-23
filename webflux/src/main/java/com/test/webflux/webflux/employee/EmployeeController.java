package com.test.webflux.webflux.employee;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    WebClient client = WebClient.create("http://localhost:8080");

    Mono<Employee> employeeMono = client.get()
            .uri("/employees/{id}", "1")
            .retrieve()
            .bodyToMono(Employee.class);
    Flux<Employee> employeeFlux = client.get()
            .uri("/employees")
            .retrieve()
            .bodyToFlux(Employee.class);

    private final EmployeeRepository employeeRepository;
    EmployeeController(){
        this.employeeRepository = new EmployeeRepository();
    }

    // constructor...
    @GetMapping("/{id}")
    public Mono<Employee> getEmployeeById(@PathVariable long id) {
        employeeMono.subscribe(System.out::println);

        return employeeRepository.findEmployeeById(id);
    }

    @GetMapping
    public Flux<Employee> getAllEmployees() {
        employeeFlux.subscribe(System.out::println);

        return employeeRepository.findAllEmployees();
    }
    @PostMapping("/update")
    public Mono<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.updateEmployee(employee);
    }
}