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
	public List<MP3> getMusicList() throws SQLException {
		String sql = " SELECT * FROM MP3 ORDER BY MID ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		List<MP3> musicList = new ArrayList<MP3>();
		while (rs.next()) {
			MP3 mp3 = new MP3(rs.getInt("MID"), rs.getString("MTITLE"), rs.getString("MAUTOR"), getMstypeList());
			musicList.add(mp3);
		}
		return musicList;
	}
	
	// 뮤직 타입 반환 메소드
	public List<Mstype> getMstypeList() throws SQLException {
		String sql = " SELECT * FROM MSTYPE ORDER BY MSID ";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		List<Mstype> mstypeList = new ArrayList<Mstype>();
		while (rs.next()) {
			Mstype mstype = new Mstype(rs.getInt("MSID"), rs.getString("MSTYPE"));
			mstypeList.add(mstype);
		}
		return mstypeList;
	}

	// 하나의 뮤직 반환 메소드
	public MP3 getMusic(int mid) throws SQLException {
		String sql = " SELECT * FROM MP3 WHERE MID = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mid);
		rs = pstmt.executeQuery();

		MP3 mp3 = null;
		if (rs.next()) {
			mp3 = new MP3(rs.getInt("MID"), rs.getString("MTITLE"), rs.getString("MAUTOR"), getMstypeList());
		}
		return mp3;
	}

	// 새로운 뮤직 등록 메소드
	public void insertMusicSql(MP3 mp3) throws SQLException {
		String sql = " INSERT INTO MP3 VALUES(SEQ_MP3.NEXTVAL, ?, ?) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mp3.getMtitle());
		pstmt.setString(2, mp3.getMautor());
		pstmt.executeQuery();
	}

	// 뮤직 업데이트 메소드
	public void updateMusicSql(MP3 mp3) throws SQLException {
		String sql = " UPDATE MP3 SET MTITLE = ?, MAUTOR = ? WHERE MID = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mp3.getMtitle());
		pstmt.setString(2, mp3.getMautor());
		pstmt.setInt(3, mp3.getMid());
		pstmt.executeUpdate();
	}

	// 뮤직 삭제 메소드
	public void deleteMusicSql(int mid) throws SQLException {
		String sql = " DELETE FROM MP3 WHERE MID = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mid);
		pstmt.executeUpdate();
	}

}
