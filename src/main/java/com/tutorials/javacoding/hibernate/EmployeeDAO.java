package com.tutorials.javacoding.hibernate;

public interface EmployeeDAO {

  void persistEmployee(Employee employee);

  Employee findEmployeeById(String id);

  void updateEmployee(Employee employee);

  void deleteEmployee(Employee employee);

}