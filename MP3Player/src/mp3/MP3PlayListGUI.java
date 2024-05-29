package mp3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MP3PlayListGUI extends JFrame {

	public static final long serialVersionUID = 4425235345233248L;

	MP3Dao mdao;
	JPanel topPanel;
	public MP3PlayListGUI() {
		mdao = new MP3Dao();
		init();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MP3PlayListGUI().setVisible(true));	
	}

	private void init() {
		setTitle("MP3 Player");
		setSize(500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			List<Music> musicList = mdao.getMusicList();
			for (Music music : musicList) {
				music.setMusicGenreList(mdao.getMusicGenreList(music.getMid()));
			}

			showTopPenel();
			showPlayListPanel(musicList);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// MyPlayList텍스트, 노래추가버튼 추가 함수
	private void showTopPenel() {
		JPanel topPanel = new JPanel(new BorderLayout());

		// My PlayList 텍스트 화면 생성
		JLabel title = new JLabel("My PlayList");
		topPanel.add(title, BorderLayout.WEST);

		// 노래 추가 버튼 생성
		JButton addButton = new JButton("노래추가");
		topPanel.add(addButton, BorderLayout.EAST);

		// Frame에 topPanel 추가
		topPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
		add(topPanel, BorderLayout.NORTH);
	}

	// 플레이리스트 출력 화면 생성 함수
	private void showPlayListPanel(List<Music> musicList) throws SQLException{
		JPanel playListPanel = new JPanel(new BorderLayout()); // 뮤직 리스트 화면

		// 뮤직 하나의 리스트 화면
		// GridLayout(행, 열, 좌우간격, 상하간격)
		JPanel playMusicPanel = new JPanel(new GridLayout(2,4,3,3)); 

		for (Music music : musicList) {
			JLabel musicTitle = new JLabel(music.getMtitle());
			JLabel musicAutor = new JLabel(music.getMautor());
			JPanel musicContentPanel = new JPanel(new BorderLayout());
			musicContentPanel.add(musicTitle, BorderLayout.NORTH);
			musicContentPanel.add(musicAutor, BorderLayout.SOUTH);
			musicContentPanel.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));

			JButton playBtn = new JButton("재생");
			JButton deleteBtn = new JButton("삭제");
			
			playMusicPanel.add(getMusicImgLabel(music));
			playMusicPanel.add(musicContentPanel);
			playMusicPanel.add(playBtn);
			playMusicPanel.add(deleteBtn);

			// 리스트에 해당 뮤직 정보 추가
			playListPanel.add(playMusicPanel, BorderLayout.NORTH);
		}
		add(playListPanel, BorderLayout.CENTER);		
	}

	// IMG 화면 구현 (크기조절을 위해 Image변환 후 icon재설정)
	private JLabel getMusicImgLabel(Music music) {
		ImageIcon icon = new ImageIcon(music.getMimg());
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel lb1 = new JLabel(" ", JLabel.CENTER);
		lb1.setIcon(changeIcon);
		return lb1;
	}
}
