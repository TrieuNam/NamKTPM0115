package experiments;

public class CoverData {

	public static void main(String[] args) {
		String[] arrPhone = { "0762 898 272", "0949 034 068" };
		for (int i = 0; i < arrPhone.length; i++) {
			System.out.println("Phone before hide: " + arrPhone[i]);

			String phone = hidePartPhoneNumber(arrPhone[i]);
			System.out.println("Phone after hide:  " + phone);
			System.out.println("------------------------------------------------");
		}
//		String[] arrEmail = { "lengoctienthanh@gmail.com", "1234567890@gmail.com" };
//		for (int i = 0; i < arrEmail.length; i++) {
//			System.out.println("Email before hide: " + arrEmail[i]);
//
//			String mail = displayYearBirthday(arrEmail[i]);
//			System.out.println("Email after hide:  " + mail);
//			System.out.println("------------------------------------------------");
//		}
//		String aString = "17/04/2013";
//		aString = hideYearBirthday(aString);
//		System.out.println(aString);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String hide(String data) {
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			data = data.replace(c, '*');
		}
		return data;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String hidePartPhoneNumber(String data) {
		/* Cut 3 phone number */
		String head = data.substring(0, 3);
		/* Cut data from Character 3 */
		String coverData = data.substring(3);
		for (int i = 0; i < coverData.length(); i++) {
			/* check character in String follow value "i" */
			char c = coverData.charAt(i);
			/* replace character with '*' */
			coverData = coverData.replace(c, '*');
		}
		data = head + coverData;
		return data;
	}

	/**
	 * @param mail
	 * @return
	 */
	public static String hidePartEmail(String mail) {
		String[] coverEmail = mail.split("@");
		/*
		 * coverEmail[0]: value before '@' ; coverEmail[1]: value after '@' ;
		 */
		for (int i = 0; i < coverEmail[0].length(); i++) {
			/* check character in String follow value "i" */
			char c = coverEmail[0].charAt(i);

			/* replace character with '*' */
			coverEmail[0] = coverEmail[0].replace(c, '*');
		}
		mail = coverEmail[0] + "@" + coverEmail[1];
		return mail;
	}

	/**
	 * 
	 * @param birthday
	 * @return
	 */
	public static String displayYearBirthday(String birthday) {
		int length = birthday.length();
		/* Get year from Birthday dd/mm/yyyy */
		String yearBirthday = birthday.substring(length - 4);
		/* Cut date month */
		String[] arrBirthday = birthday.split(yearBirthday);

		for (int i = 0; i < arrBirthday[0].length(); i++) {
			char c = arrBirthday[0].charAt(i);
			arrBirthday[0] = arrBirthday[0].replace(c, '*');
		}
		birthday = arrBirthday[0] + yearBirthday;
		return birthday;
	}
	
	/**
	 * 
	 * @param birthday
	 * @return
	 */
	public static String hideYearBirthday(String birthday) {
		/* Get dd/mm/ from Birthday dd/mm/yyyy */
		String dayBirthday = birthday.substring(0, 6);
		/* Cut date month */
		String[] arrBirthday = birthday.split(dayBirthday);

		for (int i = 0; i < arrBirthday[1].length(); i++) {
			char c = arrBirthday[1].charAt(i);
			arrBirthday[1] = arrBirthday[1].replace(c, '*');
		}
		birthday =  dayBirthday+ arrBirthday[1];
		return birthday;
	}

}
