package mp3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class MP3PlayerGUI extends JFrame {

	public static final long serialVersionUID = 4238230498209348L;

	JTextField filePathField;
	JButton playButton;
	JButton stopButton;
	JPanel centerPanel;	// 노래 이미지, 가사 영역
	JTextField lyricsField; // 가사 출력 영역

	Color backgroundColor = new Color(240, 240, 240); // 배경색
	Color buttonColor = new Color(75, 75, 75); // 버튼 색깔
	Color textColor = new Color(50, 50, 50); // 텍스트 색깔
	Font textFont = new Font("Malgun Gothic", Font.PLAIN, 20); // 텍스트 폰트 (글자체, 스타일, 크기)
	Font buttonFont = new Font("Malgun Gothic", Font.BOLD, 12); // 버튼 텍스트 폰트

	MP3Dao mdao;
	private Clip clip;
	private boolean isPlaying = false;

	public MP3PlayerGUI() {
		mdao = new MP3Dao();
		init();
	}

	public void startMP3PlayerGUI() {
		SwingUtilities.invokeLater(() -> setVisible(true));	
	}

	private void init() {
		setTitle("MP3 Player");
		setSize(500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			// 현재 뮤직의 정보를 가져온다.
			// 임의로 1이라고 지정
			Music music = mdao.getMusic(1);

			showMusicTitlePanel(music);
			showMusicImgPanel(music);
			showMusiclyricsPanel(music);
			showMusicBtnPanel();
			clickPlayBtn(music);
			clickStopBtn();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// 노래 제목 화면 구현
	private void showMusicTitlePanel(Music music) throws SQLException {
		JPanel topPanel = new JPanel();
		JLabel musicTitle = new JLabel(music.getMtitle());
		JLabel musicAutor = new JLabel(music.getMautor());

		topPanel.add(musicTitle);
		topPanel.add(musicAutor);
		topPanel.setBorder(BorderFactory.createEmptyBorder(30 , 0 , 0 , 0));
		add(topPanel, BorderLayout.NORTH);
	}

	// IMG 화면 구현 (크기조절을 위해 Image변환 후 icon재설정)
	private void showMusicImgPanel(Music music) {
		ImageIcon icon = new ImageIcon(music.getMimg());
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel lb1 = new JLabel(" ", JLabel.CENTER);
		lb1.setIcon(changeIcon);

		JPanel imgPanel = new JPanel();
		imgPanel.add(lb1);
		centerPanel = new JPanel(new BorderLayout());	// centerPanel을 BorderLayout으로 기준을 잡음
		centerPanel.add(imgPanel, BorderLayout.NORTH);
	}

	// 가사 화면 구현
	private void showMusiclyricsPanel(Music music) {
		// 가사 영역 생성
		JTextArea lyricsArea = new JTextArea();	
		lyricsArea.setEditable(false);
		String lyrics = readLyricFile(music.getMlyrics());
		lyricsArea.setText(lyrics);

		// 가사 영역에 스크롤바 생성
		JScrollPane scrollPane = new JScrollPane(lyricsArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0 , 90 , 0 , 90));
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

	}

	// 노래 재생, 정지 버튼 화면 구현
	private void showMusicBtnPanel() {
		JPanel bottomPanel = new JPanel();
		playButton = new JButton("Play");
		stopButton = new JButton("Stop");

		bottomPanel.add(playButton);
		bottomPanel.add(stopButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	// 가사 추출 및 반환 함수
	private String readLyricFile(String mfile) {
		File file = new File(mfile);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file));
			String lineStr = null;
			while ((lineStr = br.readLine()) != null) {
				sb.append(lineStr + '\n');
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return sb.toString();
	}

	// play버튼 클릭시 노래 재생 함수
	private void clickPlayBtn(Music music) {
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPlaying) {
					try {
						System.out.println("노래시작");
						FileInputStream fileInputStream = new FileInputStream(music.getMfile());
						BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
						clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(bufferedInputStream));
						clip.start();
						isPlaying = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}

	// stop버튼 클릭시 노래 정지 함수
	private void clickStopBtn() {
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isPlaying) {
					clip.stop();
					clip.close();
					isPlaying = false;
				}
			}
		});
	}

}
