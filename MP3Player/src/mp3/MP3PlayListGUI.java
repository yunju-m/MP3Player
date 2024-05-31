package mp3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class MP3PlayListGUI extends JFrame {

	public static final long serialVersionUID = 4425235345233248L;

	MP3Dao mdao;
	JPanel topPanel;
	JPanel playListPanel;
	JPanel playMusicPanel;
	JPanel musicItemPanel;
	JButton addMusicBtn;
	JButton playBtn;
	JButton deleteBtn;

	List<Music> musicList;

	public MP3PlayListGUI() {
		mdao = new MP3Dao();
		init();
	}

	public void startMP3PlayListGUI() {
		SwingUtilities.invokeLater(() -> new MP3PlayListGUI().setVisible(true));	
	}

	private void init() {
		setTitle("MP3 Player");
		setSize(500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			musicList = mdao.getMusicList();
			for (Music music : musicList) {
				music.setMusicGenreList(mdao.getMusicGenreList());
			}
			showTopPenel();
			showPlayListPanel();
			clickAddMusicBtn();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

	// topPanel 생성 함수
	private void showTopPenel() throws SQLException {
		topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 16));
		showTitleAndBtn();
		showMenuPanel();
		add(topPanel, BorderLayout.NORTH);
	}

	// 제목, 노래추가버튼 화면 생성 함수
	private void showTitleAndBtn() {
		JPanel titleAndBtnPanel = new JPanel(new BorderLayout()); 

		// My PlayList 텍스트 화면 생성
		JLabel title = new JLabel("My PlayList");
		titleAndBtnPanel.add(title, BorderLayout.WEST);
		title.setFont(new Font("Malgun Gothic", Font.BOLD, 30));

		// 노래 추가 버튼 생성
		addMusicBtn = new CustomButton("노래추가");
		addMusicBtn.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		titleAndBtnPanel.add(addMusicBtn, BorderLayout.EAST);

		topPanel.add(titleAndBtnPanel, BorderLayout.NORTH);
	}

	// MyPlayList 메뉴바 생성 함수
	private void showMenuPanel() throws SQLException {
		JPanel menuPanel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(new JMenu("노래리스트"));
		for (MusicGenre genre : mdao.getMusicGenreList()) {
			menuBar.add(new JMenu((genre.getMgtype())));
		}
		menuPanel.add(menuBar);
		topPanel.add(menuPanel, BorderLayout.SOUTH);
	}

	// 플레이리스트 출력 화면 생성 함수
	private void showPlayListPanel() throws SQLException {
		playListPanel = new JPanel(new BorderLayout()); // 뮤직 리스트 화면

		// 하나의 뮤직 리스트 화면
		playMusicPanel = new JPanel(new GridLayout(musicList.size(),1,0,0)); 
		
		for (Music music : musicList) {
			musicItemPanel = new JPanel(new BorderLayout());
			musicItemPanel.setBorder(BorderFactory.createEmptyBorder(0 , 10 , 0 , 0));
			
			showMusicImgPanel(music);
			showMusicContentPanel(music);
			showButtonPanel(music);

			clickPlayMusicBtn();
			clickDelMusicBtn();

			playMusicPanel.add(musicItemPanel);
			JScrollPane scrollPane = new JScrollPane(playMusicPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			playListPanel.add(scrollPane, BorderLayout.CENTER);
		}
		add(playListPanel, BorderLayout.CENTER);
	}

	// 노래 이미지 패널 생성
	private void showMusicImgPanel(Music music) {
		musicItemPanel.add(getMusicImgLabel(music), BorderLayout.WEST);
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

	// 노래 제목, 작곡가가 들어있는 패널 생성
	private void showMusicContentPanel(Music music) {
		JLabel musicTitle = new JLabel(music.getMtitle());
		JLabel musicAutor = new JLabel(music.getMautor());
		JPanel musicContentPanel = new JPanel(new BorderLayout());

		musicContentPanel.setBorder(BorderFactory.createEmptyBorder(40 , 0 , 40 , 0));
		musicTitle.setFont(new Font("Aharoni", Font.BOLD, 20));
		musicAutor.setFont(new Font("Aharoni", Font.BOLD, 15));
		musicAutor.setForeground(Color.GRAY);

		musicContentPanel.add(musicTitle, BorderLayout.NORTH);
		musicContentPanel.add(musicAutor, BorderLayout.SOUTH);
		musicItemPanel.add(musicContentPanel, BorderLayout.CENTER);
	}

	// 노래 재생, 삭제버튼 패널 생성
	private void showButtonPanel(Music music) {
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 20));

		// 재생버튼 생성
		playBtn = new JButton("재생");
		playBtn.setBackground(new Color(255, 204, 000));
		playBtn.setForeground(Color.BLACK);
		playBtn.setActionCommand(music.getMtitle() + "-" + music.getMautor());

		//삭제버튼 생성
		deleteBtn = new JButton("삭제");
		deleteBtn.setBackground(new Color(204, 000, 000));
		deleteBtn.setForeground(Color.BLACK);
		deleteBtn.setActionCommand(music.getMtitle() + "-" + music.getMautor());

		buttonPanel.add(playBtn, BorderLayout.WEST);
		buttonPanel.add(deleteBtn, BorderLayout.EAST);
		musicItemPanel.add(buttonPanel, BorderLayout.EAST);
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

		Music music = new Music(mtitle, mautor, mlyricsPath, null, mfilePath, null);
		music.setMusicGenreList(mdao.getMusicGenreList(music.getMtitle(), music.getMautor()));

		mdao.insertMusicSql(music);
		initPlayListGUI();
	}

	private void initPlayListGUI() {
		remove(playListPanel);
		init();
		revalidate();
		repaint();
	}

	// 노래시작버튼 클릭시 MP3PlayerGUI로 이동
	private void clickPlayMusicBtn() {
		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] split = e.getActionCommand().split("-");
				new MP3PlayerGUI(split[0], split[1]);
				setVisible(false);
			}
		});
	}

	// 노래삭제버튼 클릭시 해당 노래 플레이리스트에서 삭제
	private void clickDelMusicBtn() {
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] split = e.getActionCommand().split("-");
					mdao.deleteMusicSql(split[0], split[1]);
					initPlayListGUI();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
	}

}
