package org.apache.poi.ss.formula.functions;


/* [From MS Excel function reference] Following are some of the key formulas that are used in this implementation:
 * p(1+r)^n + y(1+rt)((1+r)^n-1)/r + f=0   ...{when r!=0}
 * ny + p + f=0                            ...{when r=0}
 */

public final class TotalTuitionBill {
	
	
	private TotalTuitionBill(){
		// class has no instances
	}
	
	/* Future value of an amount given the number of payments, rate, amount of 
     * individual payment, present value and boolean value indicating whether payments 
     * are due at the beginning of period (false => payments are due at end of period)
     * 
     * Parameters: Rate, NumberOfPeriods, PaymenPerPeriod, PresentValue, Type
	 */
		public static double FutureValue(double Rate, double NumberOfPeriods, double PaymentPerPeriod,double PresentValue, boolean Type)
		{
			double amount = 0;
			if (Rate == 0){
				amount = -1*(PresentValue+(NumberOfPeriods*PaymentPerPeriod));
			}
			else
			{
				double Rate1 = Rate + 1;
				amount = ((1-Math.pow(Rate1, NumberOfPeriods)) * (Type ? Rate1 : 1) * PaymentPerPeriod) / Rate - PresentValue*Math.pow(Rate1,NumberOfPeriods);
						
			}
			return amount;
		}
		
		/* Amount given the number of future payments, rate, amount of individual payment, 
		 * future value and boolean value indicating whether payments are due at the beginning 
		 * of period (false => payments are due at end of period)
		 * 
		 * Parameters: Rate, NumberOfPeriods, PaymenPerPeriod, FutureValue, Type
		 */
		
		public static double PresentValue(double Rate, double NumberOfPeriods, double PaymentPerPeriod, double FutureValue, boolean Type)
		{
	        double amount = 0;
	        if (Rate == 0) {
	        	amount = -1*((NumberOfPeriods*PaymentPerPeriod)+FutureValue);
	        }
	        else {
	            double Rate1 = Rate + 1;
	            amount =(( ( 1 - Math.pow(Rate1, NumberOfPeriods) ) / Rate ) * (Type ? Rate1 : 1)  * PaymentPerPeriod - FutureValue)
	                     /
	                    Math.pow(Rate1, NumberOfPeriods);
	        }
	        return amount;

		}
		
		/* Calculates the Net Present Value of a principal amount given the discount rate and a 
		 * sequence of cash flows (supplied as an array). If the amounts are income the value should 
		 * be positive, else if they are payments and not income, the value should be negative.
		 * 
		 * Parameters: Rate, CashFlowAmounts
		 */
		public static double NetPresentValue(double Rate, double[] CashFlowAmounts)
		{
			double NetPresentValue = 0;
			double Rate1 = Rate + 1;
			double Rate2 = Rate1;
			for(int i = 0, iAmount = CashFlowAmounts.length; i<iAmount; i++)
			{
				NetPresentValue += CashFlowAmounts[i] / Rate2;
				Rate2 *= Rate1;
			}
			return NetPresentValue;
		}
		
		// Parameters: Rate, NumberOfPeriods, PresentValue, FutureValue, Type
		public static double PeriodicPayment(double Rate, double NumberOfPeriods, double PresentValue, double FutureValue, boolean Type)
		{
			double amount = 0;
			if (Rate == 0){
				amount = -1*(FutureValue + PresentValue)/NumberOfPeriods;
			}
			else{
				double Rate1 = Rate + 1;
				amount = (FutureValue + PresentValue * Math.pow(Rate1, NumberOfPeriods))*Rate/((Type ? Rate1 : 1) * (1 - Math.pow(Rate1, NumberOfPeriods)));
			}
			return amount;
		}
			
		// Parameters: Rate, PaymenPerPeriod, PresentVale, FutureValue, Type
		public static double nper(double Rate, double PaymentPerPeriod, double PresentValue, double FutureValue, boolean Type)
		{
			double amount = 0;
			if (Rate == 0){
				amount = -1 * (FutureValue + PresentValue)/PaymentPerPeriod;
			}
			else{
				double Rate1 = Rate + 1;
				double Rate2 = (Type ? Rate1 : 1) * PaymentPerPeriod / Rate;
				double amount1 = ((Rate2-FutureValue) < 0) 
						? Math.log(FutureValue - Rate2)
						: Math.log(Rate2-FutureValue);
				
				double amount2 = ((Rate2-FutureValue) < 0) 
				? Math.log(-PaymentPerPeriod - Rate2)
				: Math.log(Rate2 + PaymentPerPeriod);
				
				double amount3 = Math.log(Rate1);
				amount = (amount1 - amount2)/amount3;
			}
			return amount;
		}		
	}