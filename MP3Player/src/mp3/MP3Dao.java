package mp3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MP3Dao {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;

	public MP3Dao() {
		conn = ConnectionUtil.getConnection();
	}

	// 전체 뮤직리스트 메소드
	public List<Music> getMusicList() throws SQLException {
		String sql = " SELECT * FROM MUSIC ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		List<Music> musicList = new ArrayList<Music>();

		while (rs.next()) {
			int mid = rs.getInt("MID");
			Music music = new Music(mid, rs.getString("MTITLE"), rs.getString("MAUTOR"),
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), null);
			musicList.add(music);
		}
		return musicList;
	}

	// 뮤직 장르 반환 메소드
	public List<MusicGenre> getMusicGenreList() throws SQLException {
		String sql = " SELECT * FROM MUSICGENRE ORDER BY MGID ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		List<MusicGenre> mgtypeList = new ArrayList<MusicGenre>();
		while (rs.next()) {
			MusicGenre mgtype = new MusicGenre(rs.getInt("MGID"), rs.getString("MGTYPE"));
			mgtypeList.add(mgtype);
		}
		return mgtypeList;
	}

	// 뮤직 장르 반환 메소드 (mgid)
	public List<MusicGenre> getMusicGenreList(int mgid) throws SQLException {
		String sql = " SELECT * FROM MUSICGENRE WHERE MGID = ? ORDER BY MGID ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mgid);
		rs = pstmt.executeQuery();

		List<MusicGenre> mgtypeList = new ArrayList<MusicGenre>();
		while (rs.next()) {
			MusicGenre mgtype = new MusicGenre(rs.getInt("MGID"), rs.getString("MGTYPE"));
			mgtypeList.add(mgtype);
		}
		return mgtypeList;
	}

	// 하나의 뮤직 반환 메소드
	public Music getMusic(int mid) throws SQLException {
		String sql = " SELECT * FROM MUSIC WHERE MID = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mid);
		rs = pstmt.executeQuery();

		Music music = null;
		if (rs.next()) {
			music = new Music(rs.getInt("MID"), rs.getString("MTITLE"), rs.getString("MAUTOR"),
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), null);
		}
		return music;
	}

	// 새로운 뮤직 등록(제목, 작곡가, 음악파일, 음악가사) 메소드
	public void insertMusicSql(Music music) throws SQLException {
		String sql = " INSERT INTO MUSIC(mid, mtitle, mautor, mlyrics, mfile, mgid) VALUES(SEQ_MUSIC.NEXTVAL, ?, ?, ?, ?, ?) ";
		pstmt = conn.prepareStatement(sql);
		for (MusicGenre mgtype : music.getMusicGenreList()) {
			pstmt.setString(1, music.getMtitle());
			pstmt.setString(2, music.getMautor());
			pstmt.setString(3, music.getMlyrics());
			pstmt.setString(4, music.getMfile());
			pstmt.setInt(5, mgtype.getMgid());
			pstmt.executeQuery();
		}
	}

	// 뮤직 업데이트 메소드
	public void updateMusicSql(Music mp3) throws SQLException {
		// 관심 플레이리스트 등록 구현
	}

	// 뮤직 삭제 메소드
	public void deleteMusicSql(int mid) throws SQLException {
		String sql = " DELETE FROM MUSIC WHERE MID = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mid);
		pstmt.executeUpdate();
	}

}
