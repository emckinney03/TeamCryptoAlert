package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import crypto.alert.TwitterAPI;

class TwitterAPITests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void TestParseTweetsFailsOnBadInput() {
		Exception exception = assertThrows(Exception.class, () -> {
	        TwitterAPI.parseTweets("NA");
	    });

	    String expectedMessage = "Json recieved from twitter is empty. Something went wrong!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
