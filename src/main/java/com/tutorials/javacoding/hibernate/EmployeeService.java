package com.tutorials.javacoding.hibernate;


import com.tutorials.javacoding.hibernate.config.Functional;

/**
 * This Service deals with the employees.
 */
@Functional
public interface EmployeeService {

  /**
   * persist employee
   * @param employee the employee
     */
  void persistEmployee(Employee employee);

  /**
   * find employee
   * @param id the employee's id
   * @return the employee
     */
  Employee findEmployeeById(String id);

  /**
   * update the employee's data
   * @param employee the employee
     */
  void updateEmployee(Employee employee);

  /**
   * delete the employee
   * @param employee the employee
     */
  void deleteEmployee(Employee employee);
}