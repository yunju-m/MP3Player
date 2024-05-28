package mp3;

public class MP3Player {

	private int uno;
	private String uname;

	public MP3Player() {
	}

	public MP3Player(int uno, String uname) {
		super();
		this.uno = uno;
		this.uname = uname;
	}

	public int getuno() {
		return uno;
	}

	public void setuno(int uno) {
		this.uno = uno;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public String toString() {
		return "MP3Player [uno=" + uno + ", uname=" + uname + "]";
	}

}
