//package com.test.webflux.webflux;
//
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//public class EmployeeWebClient {
//
//    WebClient client = WebClient.create("http://localhost:8080");
//
//    // ...
//    Mono<Employee> employeeMono = client.get()
//            .uri("/employees/{id}", "1")
//            .retrieve()
//            .bodyToMono(Employee.class);
//
//    employeeMono.subscribe(System.out::println); // Cannot resolve symbol 'subscribe', Unknown class: 'System.out'
//
//    Flux<Employee> employeeFlux = client.get()
//            .uri("/employees")
//            .retrieve()
//            .bodyToFlux(Employee.class);
//
//    employeeFlux.subscribe(System.out::println); // Cannot resolve symbol 'subscribe', Unknown class: 'System.out'
//}