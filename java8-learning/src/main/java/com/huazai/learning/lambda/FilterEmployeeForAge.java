package com.huazai.learning.lambda;

import com.huazai.learning.lambda.funinterface.MyPredicate;

public class FilterEmployeeForAge implements MyPredicate<Employee> {

	@Override
	public boolean test(Employee t) {
		return t.getAge() <= 35;
	}

}
