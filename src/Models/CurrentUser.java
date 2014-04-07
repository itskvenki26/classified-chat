package Models;

public class CurrentUser {

	private static int id;
	private static String email_id;
	private static String name;
	private static String ph_no;

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		CurrentUser.id = id;
	}

	public static String getEmail_id() {
		return email_id;
	}

	public static void setEmail_id(String email_id) {
		CurrentUser.email_id = email_id;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		CurrentUser.name = name;
	}

	public static String getPh_no() {
		return ph_no;
	}

	public static void setPh_no(String ph_no) {
		CurrentUser.ph_no = ph_no;
	}
}
