package com.usoit.api.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "employee")
public class Employee extends User {


	
}
