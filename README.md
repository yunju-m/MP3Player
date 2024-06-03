# MP3Player
**javax.sound 패키지를 이용해서 MP3 Player GUI 구현** <br/>
**개발기간: 2024.05.27~2024.05.31**

### 프로젝트 소개
javax.sound 패키지를 이용해서 MP3 GUI를 구현해본다.
사용자별 뮤직 플레이리스트를 오라클에 연동하여 저장하고 원하는 유형의 노래를 검색할 수 있다.

## Stacks ⭐
### Environment
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)
![EclipseIDE](https://img.shields.io/badge/EclipseIDE-2C2255?style=for-the-badge&logo=EclipseIDE&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

### Development
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white)

### 🎧 기능 요구사항
```
1. 처음 나의 플레이리스트가 나타난다.
2-1. 추가버튼을 클릭하면 노래추가팝업이 나타난다.
2-2. 노래추가창 : 제목, 작곡가, 노래파일(.wav), 노래가사(.txt)로 구성된다. 
3-1. 해당 뮤직 재생버튼을 클릭하면 뮤직재생화면으로 넘어간다.
3-2. 재생화면 : 노래제목/작곡가, 노래이미지, 노래가사(스크롤포함, .txt), 재생/정지버튼으로 구성된다.
4-1. 재생 버튼을 클릭하면 노래가 재생된다.
4-2. 중지 버튼을 클릭하면 노래가 중지된다.
5-1. 노래가사는 텍스트 파일형식으로 읽어서 화면에 출력한다.
5-2. 노래가사화면은 스크롤바를 통해 내릴 수 있다.
6-1. 뒤로가기 버튼을 클릭하면 플레이리스트 화면으로 넘어간다.
6-2. 정지를 하지않은 상태에서 뒤로가도 노래는 계속 나온다.
6-3. 노래가 진행된 상태에서 새로운 노래를 시작하면 그 노래가 재생된다.
```

### 디자인 설계
## 화면 구성 📺
| 화면 디자인 설계 |
| :----------: |
|![image](https://github.com/yunju-m/MP3Player/assets/74498379/26568f87-9784-4696-a7dd-f806b7c5de00)|

## 화면 레이아웃 설계 
| MP3PlayListGUI 화면 | MP3PlayerGUI 화면 |
| :----------: | :----------: |
| ![image](https://github.com/yunju-m/MP3Player/assets/74498379/6178224c-709c-4a49-84f5-ec4f5a818e4f) | ![image](https://github.com/yunju-m/MP3Player/assets/74498379/7f17c56b-f0eb-480e-853a-5d3a32f29f2d) |

## ER다이어그램 설계
| 논리적모델링 | 물리적모델링 |
| :----------: | :----------: |
| ![논리적모델링](https://github.com/yunju-m/MP3Player/assets/74498379/c2a048a1-4e25-4f5d-a05f-c7d83ea946df) | ![물리적모델링](https://github.com/yunju-m/MP3Player/assets/74498379/25c2f632-74a9-419a-8a4b-039a00d289fa) |

### ✏️ 기능 구현 설계
```
ConnectionUtil
	- [X] 연동할 오라클 계정 설정 클래스
	- MP3 데이터베이스에 연동
MUSIC 
	- MUSIC 객체 클래스
	- mtitle : 노래 제목(PK)
	- mautor : 노래 작곡가(PK)
	- mlyrics : 노래 가사 파일 경로
	- mimg : 노래 이미지 파일 경로 
	- mfile : 노래 파일 경로
	- musicGenreList : 노래 장르 리스트
MusicGenre 
	- mgid : 노래 장르 식별번호(PK)
	- mgtype : 노래 장르 종류
MusicList
	- mgid : 노래 장르 식별번호(FK)
	- mtitle : 노래 제목(FK)
	- mautor : 노래 작곡가(FK)
MP3Dao - CRUD구현
	- getMusicList()
		- [X] 전체 musicList 출력하는 메소드
		- 처음 myPlayList에 출력할 때 호출
	- getMusic()
		- [X] 하나의 music 반환 메소드
		- myPlayList에서 노래 재생버튼 클릭할 때 호출
	- getMusicGenreList()
		- [X] 전체 musicGenreList 출력하는 메소드
	- insertMusicSql()
		- [X] 뮤직 등록 메소드
		- myPlayList에서 노래추가버튼 클릭할 때 호출
	- updateMusicSql()
		- [X] 뮤직(플레이리스트) 업데이트 메소드
		+ 관심 플레이리스트 작성시 호출
	- deleteMusicSql()
		- [X] 뮤직 삭제 메소드
		- myPlayList에서 노래삭제버튼 클릭할 때 호출

MP3PlayListGUI
	- startMP3PlayerGUI()
		- [X] MP3DAO 객체 생성
		- [X] init함수 호출
	- init()
		- showTopPanel()
			- showTitleAndBtn()
				-[X] myPlayList 글자와 노래추가버튼 화면 구현
			- showMusicGenreListMenu()
				- [X] myPlayList 메뉴바(장르별 메뉴) 생성
		- showPlayListPanel()
			- [X] musicItemPanel을 playMusicPanel에 추가
			- [X] 스크롤 기능 구현
			- [X] 전체 플레이리스트를 playListPanel에 추가 후 Jframe에 추가
			- showMusicImgPanel()
				-[X] 노래 이미지를 musicItemPanel에 추가
				- getMusicImgLabel()
					- [X] 노래 이미지 크기 조절을 위해 icon → Img → icon 설정
			- showMusicContentPanel()
				- [X] 노래 제목, 작곡가가 들어있는 패널 생성
				- [X] musicItemPanel에 추가
			- showButtonPanel()
				- [X] 노래 재생, 삭제버튼 패널 생성
				- [X] musicItemPanel에 추가
	- ActionListener 구현
		- clickMusicGenreListMenu()
			- [X] 해당하는 뮤직바(장르)에 플레이리스트 출력
		- clickAddMusicBtn()
			- 노래추가버튼 클릭시 노래추가함수
			- checkInputContent()
				- [X] 노래 이미지, 제목, 작곡가 카드가 띄워지도록 구현
				- [X] 작성 완료하면 해당 내용을 저장하고 노래가 추가되도록 구현
				- [X] 기본 이미지 경로(imgs/B_Music.jpg) 지정
		- clickPlayMusicBtn()
			- [X] MP3PlayerGUI 화면으로 이동
		- clickDelMusicBtn()
			- 노래삭제버튼 클릭시 노래삭제함수
			- [X] 삭제버튼에 뮤직ID를 지정하여 해당 뮤직ID를 삭제하도록 구현	
			
MP3PlayerGUI
	- MP3PlayerGUI()
		- [X] init함수 호출
	- startMP3PlayerGUI()
		- [X] 화면 보이도록 setVisible(true) 설정
	- init()
		- [X] 제목, 전체크기,뒤로가기버튼, x버튼 닫기 설정
			- showMusicTitlePanel()
				-[X] 노래 제목 화면 구현
				-[X] 뒤로가기버튼 생성
					- ActionListener 구현
					- clickBeforeBtn()
						- [X] 뒤로가기 버튼 클릭시 플레이리스트 화면으로 이동
			- showMusicImgPanel()
				- [X] 가운데 이미지 화면이 나오도록 구현
				- 크기조절을 위해 Image변환 후 icon재설정
			- showMusiclyricsPanel()
				- [X] 노래 가사 화면이 나오도록 구현
					- readLyricFile()
						- [X] FileReader를 활용하여 가사 추출 및 반환
				- [X] scrollbar 추가해서 스크롤이 되도록 구현
			- showMusicBtnPanel()
				- [X] 재생/정지 버튼 화면 구현
				- ActionListener 구현
					- clickPlayBtn()
						- [X] play버튼 클릭시 노래재생함수
					- clickStopBtn()
						- [X] stop버튼 클릭시 노래정지함수

MP3Main
	- [X] MP3PlayListGUI 함수 호출
```
