# JavaTalk

< 실행하기 전 자신의 데이터 베이스 정보를 입력 >
- config.properties 파일

< 클래스 설명 >
- Client 폴더
 - ChatClient.java : 클라이언트 연결 관리 클래스

- Server 폴더
 - ClientHandler.java : 서버에서 클라이언트 관리 클래스
 - Server.java : 서버 실행 관리 클래스
 - ServerLauncher.java : 로그인시 서버 실행시키는 클래스 
 
- database 폴더 : 데이터베이스 연동과 관련된 코드 
  - DBConnection.java : 데이터베이스 사용 관련 함수들 구현
  - main.java : 데이터 베이스 연결 확인을 위해 사용한 파일 (프로젝트에서 사용 안 함)

- image 폴더 : 프로젝트에 사용되는 이미지 파일들
- Interface
 - BottomBar.java : 하단바 파일
 - ChatMessage.java : 메세지 전송 형태 클래스 파일
 - Chatting_Page.java : 채팅 화면 클래스 파일
 - ChattingList_Page.java : 채팅방 목록 클래스 파일
 - FriendList.java : 프로필 화면에서 표시할 친구목록을 가져오는 파일
 - Login_Page.java : 로그인 화면 파일
 - PageManager.java : 하단 바에서 사용될 화면 전환 효과를 위한 파일
 - SignUp_Page.java : 회원가입 화면 파일
 - Start_Page.java : 시작 화면 파일
 - Profile_Page.java : 사용자 프로필과 친구목록 화면 파일
 - UserProfileButton.java : 친구 목록을 출력할때 사용하는 버튼 파일
