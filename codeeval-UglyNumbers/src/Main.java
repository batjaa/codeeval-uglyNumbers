import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Main {

	// For storing all possible variaton of +,-
	private static Vector<Vector<String>>	possibleOps;
	// Temporary coutner for permuteDigitsAndCal method
	private static long						counter;

	/**
	 * Permutes the given string and creates all possible numbers, without changing the order
	 * @param prefix At beginning go for empty
	 */
	public static void permuteDigitsAndCalc(String str, String prefix) {
		int len = str.length();
		if (len == 0) {
			// Convert the string into list of numbers
			long[] nums = convert(prefix);
			// Get all possible operations for the given list
			Vector<String> ops = possibleOps.get(nums.length - 1);
			// Calculate
			for (String op : ops) {
				long num = nums[0];
				for (int i = 0; i < op.length(); i++) {
					switch (op.charAt(i)) {
					case '+':
						num += nums[i + 1];
						break;
					case '-':
						num -= nums[i + 1];
						break;
					}
				}
				// Check if the calculated value is an ugly number
				if ((num & 1) == 0 || num % 3 == 0 || num % 5 == 0 || num % 7 == 0) {
					counter++;
				}
			}
		} else {
			for (int i = 1; i <= len; i++) {
				String left = str.substring(0, i);
				String right = str.substring(i);
				permuteDigitsAndCalc(right, prefix + left + " ");
			}
		}
	}

	/**
	 * Permutes given array of character
	 * @param n Length of permutation
	 */
	public static void permutation(char[] c, int n, String start, Vector<String> list) {
		if (start.length() >= n) {
			list.add(start);
		} else {
			for (char x : c) {
				permutation(c, n, start + x, list);
			}
		}
	}

	/**
	 * Converts the given string into list of numbers, delimeter is a space
	 */
	public static long[] convert(String str) {
		String[] strNums = str.split("\\s");
		long[] nums = new long[strNums.length];
		for (int i = 0; i < strNums.length; i++) {
			nums[i] = Long.parseLong(strNums[i]);
		}
		return nums;
	}

	public static void main(String[] args) {
		// Max digits that can occur is 13
		// Pre-create all possible operation
		possibleOps = new Vector<Vector<String>>();
		for (int i = 0; i < 13; i++) {
			Vector<String> opPerm = new Vector<String>();
			permutation(new char[] { '+', '-' }, i, "", opPerm);
			possibleOps.add(opPerm);
		}

		String filePath = args[0];
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				counter = 0;
				permuteDigitsAndCalc(sCurrentLine, "");
				System.out.println(counter);
			}

		} catch (IOException e) {
			System.out.println("File Read Error: " + e.getMessage());
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
//			System.out.println("Finished");
		}
	}

}
