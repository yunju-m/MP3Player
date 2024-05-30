package mp3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

public class MP3Dao {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	CallableStatement cstmt;
	ResultSet rs;

	public MP3Dao() {
		conn = ConnectionUtil.getConnection();
	}

	// 전체 뮤직리스트 반환 메소드
	public List<Music> getMusicList() throws SQLException {
		String sql = " { call PROC_SELECT_MUSICLIST(?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		cstmt.execute();

		List<Music> musicList = new ArrayList<Music>();
		rs = (ResultSet)cstmt.getObject(1);
		while (rs.next()) {
			Music music = new Music(rs.getString("MTITLE"), rs.getString("MAUTOR"),
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), null);
			musicList.add(music);
		}
		return musicList;
	}

	// 선택한 뮤직 정보 반환 메소드
	public Music getMusic(String mtitle, String mautor) throws SQLException {
		String sql = " call PROC_SELECT_MUSIC(?, ?, ?, ?, ?) ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, mtitle);
		cstmt.setString(2, mautor);
		cstmt.registerOutParameter(1, Types.VARCHAR); // 노래제목
		cstmt.registerOutParameter(2, Types.VARCHAR); // 노래작곡가
		cstmt.registerOutParameter(3, Types.VARCHAR); // 노래가사
		cstmt.registerOutParameter(4, Types.VARCHAR); // 노래이미지
		cstmt.registerOutParameter(5, Types.VARCHAR); // 노래파일
		cstmt.execute();

		Music music = new Music(
				cstmt.getString(1),
				cstmt.getString(2),
				cstmt.getString(3),
				cstmt.getString(4),
				cstmt.getString(5),
				null);
		return music;
	}

	// 전체 뮤직 장르 반환 메소드
	public List<MusicGenre> getMusicGenreList() throws SQLException {
		String sql = " { call PROC_SELECT_MUSICGENRELIST(?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		cstmt.execute();
		rs = (ResultSet)cstmt.getObject(1);

		List<MusicGenre> mgtypeList = new ArrayList<MusicGenre>();
		while (rs.next()) {
			MusicGenre mgtype = new MusicGenre(rs.getInt("MGID"), rs.getString("MGTYPE"));
			mgtypeList.add(mgtype);
		}
		return mgtypeList;
	}

	// 선택한 뮤직 장르 정보 반환 메소드
	public List<MusicGenre> getMusicGenreList(String mtitle, String mautor) throws SQLException {
		String sql = " { call PROC_SELECT_MUSICGENRE(?, ?, ?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, mtitle);
		cstmt.setString(2, mautor);
		cstmt.registerOutParameter(3, OracleTypes.CURSOR);
		cstmt.execute();
		List<MusicGenre> mgtypeList = new ArrayList<MusicGenre>();
		rs = (ResultSet)cstmt.getObject(3);
		while (rs.next()) {
			MusicGenre mgtype = new MusicGenre(rs.getInt("MGID"), rs.getString("MGTYPE"));
			mgtypeList.add(mgtype);
		}
		return mgtypeList;
	}

	// 새로운 뮤직 등록(제목, 작곡가, 음악파일, 음악가사) 메소드
	public void insertMusicSql(Music music) throws SQLException {
		String sql = " { call PROC_INSERT_MUSIC(?, ?, ?, ?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, music.getMtitle());
		cstmt.setString(2, music.getMautor());
		cstmt.setString(3, music.getMlyrics());
		cstmt.setString(4, music.getMfile());
		cstmt.executeUpdate();
	}

	// 뮤직 업데이트 메소드
	public void updateMusicSql(Music mp3) throws SQLException {
		// 관심 플레이리스트 등록 구현
	}

	// 뮤직 삭제 메소드
	public void deleteMusicSql(String mtitle, String mautor) throws SQLException {
		String sql = " { call PROC_DELETE_MUSIC(?, ?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, mtitle);
		cstmt.setString(2, mautor);
		cstmt.executeUpdate();
	}

}
