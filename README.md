# walkingWith7Puppy
항해99 프론트 백엔드 협업 프로젝트 7조
<div align="center">
<img width="329" alt="image" src="https://walkingpuppy7.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0.PNG">
</div>

# Walking With Puppy v1.0
> **Hanghae99 Mini Project 7조 백엔드** <br/> **개발기간: 2023.05.04 ~ 2022.05.11**
> 
> ## 배포 주소
> **프론트 서버** : [http://walking-with-puppy.s3-website.ap-northeast-2.amazonaws.com/](http://walking-with-puppy.s3-website.ap-northeast-2.amazonaws.com/)<br>
> **백엔드 서버** : [http://ec2-3-34-51-88.ap-northeast-2.compute.amazonaws.com:8080](http://ec2-3-34-51-88.ap-northeast-2.compute.amazonaws.com:8080)<br>

## 백엔드 개발팀
|      김건       |          김재형         |       박근홍         |       조우필         |

## 프로젝트 소개
반려견 산책 메이트 매칭 서비스인 WWP (WalkingWithPuppy)는 반려견을 키우는 이용자들끼리 서로 산책 파트너를 찾아주는 웹 어플리케이션입니다.
항해99에서 한달동안 배운 기술들을 내재화 시키기 위해 프런트-백엔드 개발자들이 모여 프로젝트 진행했습니다.
한달동안 항해99에서 배운 Spring 기술들을 내재화 시키기 위해 프로젝트를 진행했습니다.
본 프로젝트는 일주일 동안 진행되었으며 Git 기반으로 한 협업 과정도 집중하였습니다.


## 시작 가이드
### Requirements
For building and running the application you need:
 - [Springboot 2.7.11](https://spring.io/blog/category/releases/)
 - [AWS RDS](https://us-east-1.console.aws.amazon.com/rds/home)
 - [AWS S3](https://s3.console.aws.amazon.com/s3)

## Stacks 🐈
### Environment
|       IntelliJ IDEA      |          GIT         |       GITHUB         |

### Development
|       Springboot      |          React         |

### Communication
|       Slack      |          Gether         |       Notion      |          Discord         |

---
## 화면 구성 📺
|                                                             메인 페이지                                                             |                                                            로그인 페이지                                                             |
|:------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------:|
| <img width="329" src="https://walkingpuppy7.s3.ap-northeast-2.amazonaws.com/%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80.PNG"/> | <img width="329" src="https://walkingpuppy7.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80.PNG"/> |  
|                                                           게시물 조회 페이지                                                           |                                                           게시물 작성 페이지                                                           |
| <img width="329" src="https://walkingpuppy7.s3.ap-northeast-2.amazonaws.com/%EA%B2%8C%EC%8B%9C%EB%AC%BC%EC%A1%B0%ED%9A%8C.PNG"/> | <img width="329" src="https://walkingpuppy7.s3.ap-northeast-2.amazonaws.com/%EA%B2%8C%EC%8B%9C%EB%AC%BC%EC%9E%91%EC%84%B1.PNG"/> |  


---
## 주요 기능 📦
- 회원가입 및 로그인
  - 회원 가입 필드에 대한 유효성 검증
- 게시글 및 댓글 CRUD
  - Spring Data JPA Repository 구현
  - 주소 기반 검색
- Spring Security
  - access 및 refresh token을 통한 회원인증
- Spring Boot Cache 적용을 통한 게시물 조회 성능 개선
- Jasypt를 이용한 민감 정보 암호화
  - DB 정보, AWS IAM 엑세스키, 시크릿키
- Form-data형식을 통한 Json정보와, 이미지 파일 데이터 수신 및 처리
- RDS mysql 연결 및 S3 버킷 연결
- Swagger을 통한 문서자동화 기능 사용
- FE와 원활한 데이터 송수신

---
## 아키텍쳐
### 디렉토리 구조
```bash
├── README.md
├── com.turkey.walkingwith7puppy:
│   ├── annotation : 유효성 검사 annotation 관련 폴더
│   │   ├── Email
│   │   ├── Password
│   │   └── Username
│   ├── config : 각종 기능관련 Configuration 폴더
│   │   ├── JasyptConfig
│   │   ├── S3Config
│   │   ├── SwaggerConfig
│   │   └── WebSecurityConfig
│   ├── controller
│   │   ├── MemberController
│   │   ├── BoardController
│   │   └──CommentController
│   ├── dto
│   │   ├── BoardDto
│   │   ├── CommentDto
│   │   ├── TokenDto
│   │   │── request
│   │   ├   ├── MemberLoginRequest
│   │   ├   ├── MemberSignupRequet
│   │   ├   ├── BoardRequest
│   │   ├   └── CommentRequest
│   │   │── response
│   │   ├   ├── BoardResponse
│   │   ├   └── CommentResponse
│   ├── entity
│   │   │── Member
│   │   │── Board
│   │   │── Comment
│   │   │── Timestamped
│   │   └── RefreshToken
│   ├── exception
│   │   │── CommonErrorCode
│   │   │── ErrorCode
│   │   │── GlobalExceptionHandler
│   │   │── MemberErrorCode
│   │   │── RestApiException
│   │   └── TokenErrorCode
│   ├── jwt
│   │   │── JwtAuthFilter
│   │   └── JwtUtil
│   ├── repository
│   │   │── BoardRepository
│   │   │── CommentRepository
│   │   │── MemberRepository
│   │   └── RefreshTokenRepository
│   ├── security
│   │   │── UserDetailsImpl
│   │   └── UserDetailsServiceImpl
└───── service
        │── BoardService
        │── CommentService
        └── MemberService

```

