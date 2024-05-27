package mp3;

public class Mstype {

	private int msid;
	private String mstype;

	public Mstype() {
	}

	public Mstype(int msid, String mstype) {
		super();
		this.msid = msid;
		this.mstype = mstype;
	}

	public int getMsid() {
		return msid;
	}

	public void setMsid(int msid) {
		this.msid = msid;
	}

	public String getMstype() {
		return mstype;
	}

	public void setMstype(String mstype) {
		this.mstype = mstype;
	}

	@Override
	public String toString() {
		return "Mstype [msid=" + msid + ", mstype=" + mstype + "]";
	}

}
