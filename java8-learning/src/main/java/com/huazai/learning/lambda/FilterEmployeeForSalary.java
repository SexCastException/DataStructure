package com.huazai.learning.lambda;

import com.huazai.learning.lambda.funinterface.MyPredicate;

public class FilterEmployeeForSalary implements MyPredicate<Employee> {

	@Override
	public boolean test(Employee t) {
		return t.getSalary() >= 5000;
	}

}
