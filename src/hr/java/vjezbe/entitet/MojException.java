package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MojException {
	private static final Logger logger = LoggerFactory.getLogger(MojException.class);
	
	static public BigDecimal tryBigDecimal(String x ,Scanner scanner) {
		int i = 0;
		BigDecimal var = null;
		do {
			try {
				System.out.println(x);
				var = scanner.nextBigDecimal();
				i++;
			}
			catch (InputMismatchException e) {
				System.out.println("Unos mora biti broj!");
				logger.error("Pogreška prilikom unosa BigDecimal tipa podatka", e);
				//logger.info(e.getMessage(), e);
			}
			scanner.nextLine();
		}while(i == 0);
		
		return var;
	}
	
	static public int tryInt(String x ,Scanner scanner) {
		int i = 0;
		int var = 0;
		do {
			try {
				System.out.println(x);
				var = scanner.nextInt();
				i++;
			}
			catch (InputMismatchException e) {
				System.out.println("Unos mora biti broj!");
				logger.error("Pogreška prilikom unosa int tipa podatka", e);
				//logger.info(e.getMessage(), e);
			}
			scanner.nextLine();
		}while(i == 0);
		
		return var;
	}
}
