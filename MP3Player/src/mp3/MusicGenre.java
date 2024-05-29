package mp3;

public class MusicGenre {

	private int mgid;
	private String mgtype;

	public MusicGenre() {
	}

	public MusicGenre(int mgid, String mgtype) {
		super();
		this.mgid = mgid;
		this.mgtype = mgtype;
	}

	public int getMgid() {
		return mgid;
	}

	public void setMgid(int mgid) {
		this.mgid = mgid;
	}

	public String getMgtype() {
		return mgtype;
	}

	public void setMgtype(String mgtype) {
		this.mgtype = mgtype;
	}

	@Override
	public String toString() {
		return "MusicGenre [mgid=" + mgid + ", mgtype=" + mgtype + "]";
	}

}
