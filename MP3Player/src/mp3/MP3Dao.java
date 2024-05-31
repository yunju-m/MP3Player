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
		cstmt.close();
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
		cstmt.close();
		return music;
	}

	// 선택한 장르에 뮤직리스트 반환 메소드
	public List<Music> getMusicGenreMusicList(String genre) throws SQLException {
		String sql = " { call PROC_SELECT_GENRE_BY_MUSICLIST(?, ?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, genre);
		cstmt.registerOutParameter(2, OracleTypes.CURSOR);
		cstmt.execute();
		rs = (ResultSet)cstmt.getObject(2);

		List<Music> genreByMusicList = new ArrayList<Music>();
		while (rs.next()) {
			Music music = new Music(rs.getString("MTITLE"), rs.getString("MAUTOR"),
					rs.getString("MLYRICS"), rs.getString("MIMG"), rs.getString("MFILE"), null);
			genreByMusicList.add(music);
		}
		cstmt.close();
		return genreByMusicList;
	}

	// 전체 뮤직 장르 반환 메소드1
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
		cstmt.close();
		return mgtypeList;
	}

	// 선택한 뮤직 장르 정보 반환 메소드2 (장르종류로 검색)
	public MusicGenre getMusicGenreList(String mgenre) throws SQLException {
		String sql = " { call PROC_SELECT_MUSICGENRE_PK(?, ?) } ";
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, mgenre);		
		cstmt.registerOutParameter(1, Types.VARCHAR); // 노래 장르
		cstmt.registerOutParameter(2, Types.NUMERIC); // 장르 번호
		cstmt.execute();

		MusicGenre mgtype = new MusicGenre(cstmt.getInt(2), cstmt.getString(1));	
		cstmt.close();
		return mgtype;
	}

	// 선택한 뮤직 장르 정보 반환 메소드3 (가사이름, 작곡가 검색)
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
		cstmt.close();
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
		cstmt.close();
	}

	// 새로운 뮤직을 장르에 맞춰서 뮤직리스트에 등록 메소드
	public void insertMusicListSql(Music music) throws SQLException {
		String sql = " { call PROC_INSERT_MUSICLIST(?, ?, ? ) } ";
		cstmt = conn.prepareCall(sql);
		for (MusicGenre mg : music.getMusicGenreList()) {
			cstmt.setInt(1, mg.getMgid());
			cstmt.setString(2, music.getMtitle());
			cstmt.setString(3, music.getMautor());
			cstmt.executeUpdate();	
		}
		cstmt.close();
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
		cstmt.close();
	}

	// 뮤직장르별 뮤직 삭제 메소드
	public void deleteMusicListSql(int mgid, String mtitle, String mautor) throws SQLException {
		String sql = " { call PROC_DELETE_MUSICLIST(?, ?, ?) } ";
		cstmt = conn.prepareCall(sql);		
		cstmt.setInt(1, mgid);
		cstmt.setString(2, mtitle);
		cstmt.setString(3, mautor);
		cstmt.executeUpdate();
		cstmt.close();
	}

}
