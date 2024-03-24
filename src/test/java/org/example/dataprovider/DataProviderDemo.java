package org.example.dataprovider;

import org.testng.annotations.DataProvider;

public class DataProviderDemo {
	
	@DataProvider(name="dataprovider", parallel = true)
	public Object[][] dataProviderFunction()
	{
		try {
			return new Object[][] {           
	               {"Test1","testPassword","Username cannot be empty"},        
	               {"Test2","Test","Invalid credentials"},
	               {"$%1234","2345%$","Invalid credentials"}          
	             };
		} catch (Exception e) {
			throw e;
		}
	}

}
