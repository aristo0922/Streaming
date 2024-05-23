package com.test.webflux.webflux.employee;

import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@NoArgsConstructor
public class EmployeeRepository {
    static Flux<Employee> repository;
    public Mono<Employee> findEmployeeById(long id) {
        return null;
    }

    public Flux<Employee> findAllEmployees() {
        return repository;
    }

    public Mono<Employee> updateEmployee(Employee employee) {

        return null;
    }
}
