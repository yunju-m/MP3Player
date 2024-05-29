package mp3;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.SQLException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MP3PlayerGUI extends JFrame {

	public static final long serialVersionUID = 4238230498209348L;

	JTextField filePathField;
	JButton playButton;
	JButton stopButton;
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
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			// 현재 뮤직의 정보를 가져온다.
			// 임의로 1이라고 지정
			Music music = mdao.getMusic(1);
			
			showMusicTitlePanel(music);
			showMusicImgPanel(music);
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

		JPanel centerPanel = new JPanel();
		centerPanel.add(lb1);
		add(centerPanel, BorderLayout.CENTER);
	}

	// 노래 재생, 정지 버튼 화면 구현
	private void showMusicBtnPanel() {
		JPanel bottomPanel = new JPanel();
//		filePathField = new JTextField(20);
		playButton = new JButton("Play");
		stopButton = new JButton("Stop");

//		bottomPanel.add(filePathField);
		bottomPanel.add(playButton);
		bottomPanel.add(stopButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	// play버튼 클릭시 노래 재생 함수
	private void clickPlayBtn(Music music) {
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPlaying) {
					try {
//						String filePath = filePathField.getText();
//						System.out.println(filePath);
//						if (filePath != null && !filePath.isEmpty()) {
							FileInputStream fileInputStream = new FileInputStream(music.getMfile());
							BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
							clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(bufferedInputStream));
							clip.start();
							isPlaying = true;
//						}
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
