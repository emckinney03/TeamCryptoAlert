package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import crypto.alert.CoinMarketCapAPI;

class CoinMarketCapAPITests {

	private CoinMarketCapAPI api;
	
	@BeforeEach
	void setUp() throws Exception {
		api = new CoinMarketCapAPI();
	}

	@Test
	void TestAPIPollForPrices() {
		
	}

}
