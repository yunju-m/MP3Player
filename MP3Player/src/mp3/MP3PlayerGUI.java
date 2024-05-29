package mp3;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

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

	private Clip clip;
	private boolean isPlaying = false;

	public MP3PlayerGUI() {
		init();
	}
	
	public void startMP3PlayerGUI() {
		SwingUtilities.invokeLater(() -> setVisible(true));	
	}

	private void init() {
		setTitle("MP3 Player");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		showMusicTitlePanel();
		showMusicImgPanel();
		showMusicBtnPanel();
		clickPlayBtn();
		clickStopBtn();
	}


	// 노래 제목 화면 구현
	private void showMusicTitlePanel() {
		System.out.println("노래 제목 화면 구현");
		JPanel topPanel = new JPanel();
		JLabel musicTitle = new JLabel("노래제목");
		JLabel musicAutor = new JLabel("작곡가");
		topPanel.add(musicTitle);
		topPanel.add(musicAutor);
		topPanel.setBorder(BorderFactory.createEmptyBorder(30 , 0 , 0 , 0));
		add(topPanel, BorderLayout.NORTH);
	}

	// IMG 화면 구현 (크기조절을 위해 Image변환 후 icon재설정)
	private void showMusicImgPanel() {
		System.out.println("IMG 화면 구현");
		ImageIcon icon = new ImageIcon("imgs/B_좋은날.jpg");
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
		System.out.println("노래 재생, 정지 버튼 화면 구현");
		JPanel bottomPanel = new JPanel();
		filePathField = new JTextField(20);
		playButton = new JButton("Play");
		stopButton = new JButton("Stop");

		bottomPanel.add(filePathField);
		bottomPanel.add(playButton);
		bottomPanel.add(stopButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	// play버튼 클릭시 노래 재생 함수
	private void clickPlayBtn() {
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPlaying) {
					try {
						String filePath = filePathField.getText();
						System.out.println(filePath);
						if (filePath != null && !filePath.isEmpty()) {
							FileInputStream fileInputStream = new FileInputStream(filePath);
							BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
							clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(bufferedInputStream));
							clip.start();
							isPlaying = true;
						}
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
