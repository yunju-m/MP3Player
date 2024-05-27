package mp3;

import java.util.List;

public class MP3 {

	private int mid;
	private String mtitle;
	private String mautor;
	private List<Mstype> mstypeList;

	public MP3() {
	}

	public MP3(int mid, String mtitle, String mautor, List<Mstype> mstypeList) {
		super();
		this.mid = mid;
		this.mtitle = mtitle;
		this.mautor = mautor;
		this.mstypeList = mstypeList;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getMtitle() {
		return mtitle;
	}

	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}

	public String getMautor() {
		return mautor;
	}

	public void setMautor(String mautor) {
		this.mautor = mautor;
	}

	public List<Mstype> getMstypeList() {
		return mstypeList;
	}

	public void setMstypeList(List<Mstype> mstypeList) {
		this.mstypeList = mstypeList;
	}

	@Override
	public String toString() {
		return "MP3 [mid=" + mid + ", mtitle=" + mtitle + ", mautor=" + mautor + ", mstypeList=" + mstypeList + "]";
	}

}
