package experiments;

public class ReadJson {
	public static void main(String args[]) {
		String[] idPatients = { "a106f2b6-7aca-41a5-a036-ad99d9468a3e", "3f83a95e-fcc3-4ea9-b94b-945258616a3c" };
		String[] DoctorA = { "3f83a95e-fcc3-4ea9-b94b-945258616a3c", "a106f2b6-7aca-41a5-a036-ad99d9468a3e",
				"ce9f962c-b79a-42ab-86ac-5b25a431c9ba" };

		comparedPatientDoctor(idPatients, DoctorA);
	}

	private static boolean comparedPatientDoctor(String[] idPatients, String[] DoctorA) {
		boolean boo = true;
		for (int i = 0; i < idPatients.length; i++) {
			System.out.println("Giá trị Patient: " + idPatients[i]);
			System.out.println("*****************************************************");
			for (int j = 0; j < DoctorA.length; j++) {
				System.out.println("\tGiá trị Doctor: " + DoctorA[j]);
				if (idPatients[i] == DoctorA[j]) {
					boo = true;
					System.out.println("\t" + boo);


				} else {
					boo = false;
					System.out.println("\t" + boo);
				}
				System.out.println("\t------------------------------------");
			}
		}
		return boo;
	}
}