package mp3;

public class MP3Player {

	private int uid;
	private String uname;

	public MP3Player() {
	}

	public MP3Player(int uid, String uname) {
		super();
		this.uid = uid;
		this.uname = uname;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public String toString() {
		return "MP3Player [uid=" + uid + ", uname=" + uname + "]";
	}

}
