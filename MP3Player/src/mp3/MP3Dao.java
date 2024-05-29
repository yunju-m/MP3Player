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
		String sql = " SELECT * FROM MUSIC ORDER BY MID ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		List<Music> musicList = new ArrayList<Music>();
		while (rs.next()) {
			Music mp3 = new Music(rs.getInt("MID"), rs.getString("MTITLE"), rs.getString("MAUTOR"),
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), getMusicGanreList());
			musicList.add(mp3);
		}
		return musicList;
	}
	
	// 뮤직 장르 반환 메소드
	public List<MusicGenre> getMusicGanreList() throws SQLException {
		String sql = " SELECT * FROM MUSICGANRE ORDER BY MGID ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
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
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), getMusicGanreList());
		}
		return music;
	}

	// 새로운 뮤직 등록(제목, 작곡가, 음악파일) 메소드
	public void insertMusicSql(Music music) throws SQLException {
		String sql = " INSERT INTO MUSIC VALUES(SEQ_MUSIC.NEXTVAL, ?, ?, ?) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, music.getMtitle());
		pstmt.setString(2, music.getMautor());
		pstmt.setString(3, music.getMfile());
		pstmt.executeQuery();
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
