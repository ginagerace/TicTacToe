import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class TicTacToeTest {

	MinMax minMaxTester;

	@BeforeAll
	static void before(){
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
	}

	@Test //test max return -10
	void testMax1() {
		String [] board = {"O", "O", "X", "X", "X", "O","O", "O", "X"};
		Node tester = new Node(board, 3);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		System.out.println(tester.getMinMax());
		assertEquals(-10, minMaxTester.Max(tester), "test one worked!");
	}

	@Test //test max return 0
	void testMax2() {
		String [] board = {"O", "O", "X", "X", "X", "O","O", "O", "b"};
		Node tester = new Node(board, 8);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		System.out.println(tester.getMinMax());
		assertEquals(0, minMaxTester.Max(tester), "test two worked!");
	}

	@Test //test max return 10
	void testMax3() {
		String [] board = {"O", "O", "O", "X", "X", "b","b", "b", "b"};
		Node tester = new Node(board, 5);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		System.out.println(tester.getMinMax());
		assertEquals(10, minMaxTester.Max(tester), "test three worked!");
	}

	@Test //test min return 10
	void testMin1() {
		String [] board = {"O", "O", "X", "X", "X", "O","O", "O", "X"};
		Node tester = new Node(board, 3);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		System.out.println(tester.getMinMax());
		assertEquals(10, minMaxTester.Min(tester), "test four worked!");
	}

	@Test //test max return 0
	void testMin2() {
		String [] board = {"O", "O", "X", "X", "X", "O","O", "O", "b"};
		Node tester = new Node(board, 8);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_X();
		System.out.println(tester.getMinMax());
		assertEquals(-10, minMaxTester.Min(tester), "test five worked!");
	}

	@Test //test max return 10
	void testMin3() {
		String [] board = {"b", "b", "b", "b", "X", "b","b", "b", "b"};
		Node tester = new Node(board, 5);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		System.out.println(tester.getMinMax());
		assertEquals(0, minMaxTester.Min(tester), "test six worked!");
	}

	@Test //test print_minMax
	void testPrintMinMax() {
		String [] board = {"b", "b", "b", "b", "b", "b","b", "b", "b"};
		Node tester = new Node(board, 5);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		assertNotNull(minMaxTester.Min(tester), "test seven worked!");
	}

	@Test //test print_minMax
	void testPrintList() {
		String [] board = {"b", "b", "b", "b", "b", "b","b", "b", "b"};
		Node tester = new Node(board, 5);
		MinMax minMaxTester = new MinMax(board);
		tester.setMinMax_for_O();
		assertNotNull(minMaxTester.Min(tester), "test eight worked!");
	}

}