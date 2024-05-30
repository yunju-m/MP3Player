package mp3;

import java.util.List;

public class Music {

	private String mtitle;
	private String mautor;
	private String mlyrics;
	private String mimg;
	private String mfile;
	private List<MusicGenre> musicGenreList;
	
	public Music() {
	}

	public Music(String mtitle, String mautor, String mlyrics, String mimg, String mfile,
			List<MusicGenre> musicGenreList) {
		super();
		this.mtitle = mtitle;
		this.mautor = mautor;
		this.mlyrics = mlyrics;
		this.mimg = mimg;
		this.mfile = mfile;
		this.musicGenreList = musicGenreList;
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

	public String getMlyrics() {
		return mlyrics;
	}

	public void setMlyrics(String mlyrics) {
		this.mlyrics = mlyrics;
	}

	public String getMimg() {
		return mimg;
	}

	public void setMimg(String mimg) {
		this.mimg = mimg;
	}

	public String getMfile() {
		return mfile;
	}

	public void setMfile(String mfile) {
		this.mfile = mfile;
	}

	public List<MusicGenre> getMusicGenreList() {
		return musicGenreList;
	}

	public void setMusicGenreList(List<MusicGenre> musicGenreList) {
		this.musicGenreList = musicGenreList;
	}

	@Override
	public String toString() {
		return "Music [mtitle=" + mtitle + ", mautor=" + mautor + ", mlyrics=" + mlyrics + ", mimg=" + mimg + ", mfile="
				+ mfile + ", musicGenreList=" + musicGenreList + "]";
	}

}
