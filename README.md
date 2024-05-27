# MP3Player
**javax.sound 패키지를 이용해서 MP3 Player GUI 구현** <br/>
**개발기간: 2024.05.27**

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

### ✏️ 기능 구현 설계
```
ConnectionUtil
	- [ ] 연동할 오라클 계정 설정 클래스
	- MYJ 데이터베이스에 연동
MP3
	- MP3 Player 객체 클래스
	- mid : 노래 식별번호
	- mtitle : 노래 제목
	- mautor : 노래 작곡가
	- mstypeList : 노래 유형 리스트
MP3Player
	- uid : 유저 식별번호
	- uname : 유저 닉네임
Mstype
	- msid : 노래 유형 식별번호
	- mstype : 노래 유형 종류
MP3Dao - CRUD구현
	- getMusicList()
		- [ ] 전체 musicList 출력하는 메소드
	- getMusic()
		- [ ] 하나의 music 반환 메소드
	- insertMusicSql()
		- [ ] 뮤직 등록 메소드
	- updateMusicSql()
		- [ ] 뮤직(플레이리스트) 업데이트 메소드
	- deleteMusicSql()
		- [ ] 뮤직 삭제 메소드
MP3PlayerGUI
	- MP3PlayerGUI()
		- [ ] MP3DAO 객체 생성
		- [ ] init(초기화 메소드) 호출
	- init()
		- [ ] 뮤직 이미지 화면 설정
		- createMusicImgPanel()
			- [ ] 가운데 이미지 화면이 나오도록 구현
		- createMusicProbarPanel()
			- [ ] 음악 진행도를 나타내는 진행 바 구현
		- createMusicBtnPanel()
			- [ ] 재생/정지 버튼 화면 구현
```
