package mp3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MP3PlayListGUI extends JFrame {

	public static final long serialVersionUID = 4425235345233248L;

	MP3Dao mdao;
	JPanel topPanel;
	JButton addMusicBtn;
	JButton playBtn;
	JButton deleteBtn;

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
			clickAddMusicBtn();
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
		addMusicBtn = new JButton("노래추가");
		topPanel.add(addMusicBtn, BorderLayout.EAST);

		// Frame에 topPanel 추가
		topPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
		add(topPanel, BorderLayout.NORTH);
	}

	// 플레이리스트 출력 화면 생성 함수
	private void showPlayListPanel(List<Music> musicList) throws SQLException {
		JPanel playListPanel = new JPanel(new BorderLayout()); // 뮤직 리스트 화면

		// 뮤직 하나의 리스트 화면
		// GridLayout(행, 열, 좌우간격, 상하간격)
		JPanel playMusicPanel = new JPanel(new GridLayout(musicList.size(),4,3,3)); 

		for (Music music : musicList) {
			JLabel musicTitle = new JLabel(music.getMtitle());
			JLabel musicAutor = new JLabel(music.getMautor());
			JPanel musicContentPanel = new JPanel(new BorderLayout());
			musicContentPanel.add(musicTitle, BorderLayout.NORTH);
			musicContentPanel.add(musicAutor, BorderLayout.SOUTH);
			musicContentPanel.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));

			playBtn = new JButton("재생");
			deleteBtn = new JButton("삭제");
			deleteBtn.setActionCommand(""+music.getMid());
			clickDelMusicBtn();

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

	// 노래추가버튼 클릭시 노래 추가 카드 창 출력
	// 노래 제목, 작곡가, 노래파일, 노래가사, 노래장르 선택한 후 등록
	private void clickAddMusicBtn() {
		addMusicBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					checkInputContent();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
	}

	// 노래추가 팝업창 출력 함수
	private void checkInputContent() throws SQLException {
		String mtitle = JOptionPane.showInputDialog(null, "노래제목", "노래추가", JOptionPane.QUESTION_MESSAGE);
		if (mtitle == null || mtitle.isBlank()) return;

		String mautor = JOptionPane.showInputDialog(null, "작곡가", "노래추가", JOptionPane.QUESTION_MESSAGE);
		if (mautor == null || mautor.isBlank()) return;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("노래파일(.wav) 추가");
		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		File mfile = fileChooser.getSelectedFile();
		String mfilePath = mfile.getAbsolutePath();

		fileChooser.setDialogTitle("노래가사(.txt) 추가");
		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		File mlyrics = fileChooser.getSelectedFile();
		String mlyricsPath = mlyrics.getAbsolutePath();

		List<MusicGenre> genreList = mdao.getMusicGenreList();
		int genreListSize = genreList.size();
		String[] genreArr = new String[genreListSize];
		for (int i = 0; i < genreListSize; i++) {
			genreArr[i] = genreList.get(i).getMgtype();
		}
		String genre = (String) JOptionPane.showInputDialog(null, "노래장르", "노래추가", JOptionPane.QUESTION_MESSAGE, 
				null, genreArr, genreArr[0]);
		if (genre == null || genre.isBlank()) return;

		JOptionPane.showMessageDialog(null, 
				"노래 제목: " + mtitle + "\n" +
						"작곡가: " + mautor + "\n" +
						"음악 파일: " + mfile.getAbsolutePath() + "\n" +
						"음악 가사: " + mlyrics.getAbsolutePath(),
						"노래 추가 완료",
						JOptionPane.INFORMATION_MESSAGE);

		Music music = new Music(0, mtitle, mautor, mlyricsPath, null, mfilePath, null);
		for (MusicGenre gen : genreList) {
			if (gen.getMgtype().equals(genre)) {
				music.setMusicGenreList(mdao.getMusicGenreList(gen.getMgid()));
			}
		}
		mdao.insertMusicSql(music);
	}

	// 노래삭제버튼 클릭시 해당 노래 플레이리스트에서 삭제
	private void clickDelMusicBtn() {
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mdao.deleteMusicSql(Integer.parseInt(e.getActionCommand()));
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
	}

}
