public class StringSplitter {

	public static void main(String[] args) {
		String str = "12:00 - 14:00";
		String[] hours = str.split(" ");
		for (String hour : hours) {
			System.out.println(hour);
		}
		
		System.out.println(hours[0]);
		System.out.println(hours[2]);
	}
}
