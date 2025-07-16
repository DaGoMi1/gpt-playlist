# 🎵 GPT-Playlist

**GPT-Playlist**는 사용자의 감정을 분석해 감정 기반 맞춤형 음악 플레이리스트를 추천하는 웹 애플리케이션입니다.  
감정 텍스트를 입력하면 AI가 적절한 음악 키워드를 매핑해 플레이리스트를 생성합니다.

(개발 중인 프로젝트로 내용은 업데이트될 수 있습니다.)

---

## 📌 기술 스택

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-green?logo=spring)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange)
![Redis](https://img.shields.io/badge/Redis-7.4.2-red?logo=redis)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-HTML%20Template-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Containerization-blue?logo=docker)

- **Backend:** Spring Boot, Spring Security, JPA, Redis  
- **Authentication:** JWT (Access Token + Refresh Token)  
- **Frontend:** Thymeleaf 기반 HTML, CSS, JavaScript (IntelliJ 내 개발)  
- **Infra:** AWS
- **Build:** Gradle

---

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 🎭 감정 입력 | 사용자가 감정 키워드를 입력 또는 선택 |
| 🤖 GPT API 연동 | OpenAI GPT API를 사용해 감정에 맞는 음악 키워드 생성 |
| 🎶 음악 키워드 매핑 | 감정에 맞는 음악 키워드 자동 매핑 |
| 🎵 Spotify API 연동 | Spotify API를 활용해 실제 음악 데이터를 기반으로 플레이리스트 구성 |
| 📃 플레이리스트 생성 | 매핑된 키워드 기반 음악 추천 리스트 생성 |
| 🌐 서버 렌더링 UI | Thymeleaf를 사용한 서버 사이드 렌더링 기반 웹 페이지 제공 |

---

## 📂 프로젝트 구조 및 아키텍처

- Spring Boot 기반 백엔드  
- Redis 캐시 활용 (데이터 조회 성능 최적화)  
- JWT 기반 인증  
- Thymeleaf 템플릿을 이용한 서버 사이드 렌더링  
- Docker 컨테이너화 및 배포

---

> ⚠️ 현재 AWS 프리 티어 비용 한계로 상시 배포 서비스는 유지하지 않고 있습니다.  
> 대신, 테스트용 화면 및 기능 시연 스크린샷을 [구글 드라이브]([https://drive.google.com/your-test-screenshots-folder](https://drive.google.com/drive/folders/1DG3DkcmUbi3Jd2cN5GVWraHD6j7KpnhN?usp=sharing))에서 확인하실 수 있습니다.
