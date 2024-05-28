package mp3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MP3Main {

	Connection conn;
	
	public MP3Main() {
		conn = ConnectionUtil.getConnection();
	}
	
	public static void main(String[] args) {

		MP3Dao mdao = new MP3Dao();
		
		try {
			// 뮤직리스트 출력 
			List<MP3> musicList = mdao.getMusicList();
			for (MP3 mp3 : musicList) {
				System.out.println(mp3);
			}
			
			// 뮤직 장르 출력
			List<Mstype> mstypeList = mdao.getMstypeList();
			for (Mstype mstype : mstypeList) {
				System.out.println(mstype);
			}
			
			// 하나의 뮤직 출력
			MP3 mp3 = mdao.getMusic(1);
			System.out.println(mp3);
			
			// 새로운 뮤직 추가
			MP3 newMusic = new MP3(0, "분홍신", "아이유", mdao.getMstypeList());
			mdao.insertMusicSql(newMusic);
			
			// 뮤직 정보 변경
			MP3 updateMusic = new MP3(3, "Magnetic", "IELIT", mdao.getMstypeList());
			mdao.updateMusicSql(updateMusic);
			
			// 뮤직 삭제
			mdao.deleteMusicSql(3);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}

}
