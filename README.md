﻿
# 🌟 Super Me 쇼핑몰 : BACK END 🌟

쇼핑몰 플랫폼을 위한 기본적인 백엔드 기능을 제공  
구현된 **주요 기능**에는 로그인, 회원가입, 마이페이지, 상품 등록, 조회, 장바구니 관리, 그리고 페이팔을 통한 결제가 포함

## 🗓️ 프로젝트 스케줄
- **개발 기간**: 2024.03.11 ~ 03.22 (2주)
- **추가 작업**: 2024.04.22 ~ 04.25

## 👥 팀 구성
- **프론트엔드**: 4명
- **백엔드**: 3명

## ✨ 기능 ✨
<details>
<summary><strong>🔐 인증</strong></summary>
<ul>
    <li>🆕 회원가입</li>
    <li>🔑 로그인</li>
    <li>🔒 JWT 기반 보안</li>
</ul>
</details>

<details>
<summary><strong>👤 사용자 관리</strong></summary>
<ul>
    <li>🔧 마이페이지</li>
</ul>
</details>

<details>
<summary><strong>📦 상품 관리</strong></summary>
<ul>
    <li>📝 상품 등록</li>
    <li>🔍 상품 조회</li>
    <li>🔎 상품 디테일</li>
</ul>
</details>

<details>
<summary><strong>🛍️ 장바구니</strong></summary>
<ul>
    <li>➕ 장바구니에 아이템 추가</li>
    <li>📋 장바구니 아이템 조회</li>
    <li>🔄 장바구니 아이템 수량 업데이트</li>
    <li>❌ 장바구니 아이템 삭제</li>
    <li>🗑️ 장바구니 비우기</li>
</ul>
</details>

<details>
<summary><strong>💳 결제</strong></summary>
<ul>
    <li>💵 페이팔 결제 처리</li>
</ul>
</details>

## 🛠️ 기술 스택 🛠️
- **☕ Java 17**
- **🚀 Spring Boot 3.2.3**
- **🛡️ Spring Security**
- **💽 Spring Data JPA**
- **🔐 JWT 인증**
- **🔧 QueryDSL**
- **📜 Swagger**
- **⚙️ H2 데이터베이스 (테스트용)**
- **🗃️ MySQL 데이터베이스 (프로덕션용)**


## 📂 프로젝트 구조
<details>
<summary><strong>📁 프로젝트 구조</strong></summary>
<ul>
  <li><strong>src/</strong>
    <ul>
      <li><strong>main/</strong>
        <ul>
          <li><strong>java/</strong>
            <ul>
              <li><strong>com.backend.superme/</strong>
                <ul>
                  <li><strong>cart/</strong>
                    <ul>
                      <li>controller/</li>
                      <li>dto/</li>
                      <li>entity/</li>
                      <li>repository/</li>
                      <li>service/</li>
                    </ul>
                  </li>
                  <li>global/</li>
                  <li>image/</li>
                  <li>item/</li>
                  <li>member/</li>
                  <li>order/</li>
                </ul>
              </li>
            </ul>
          </li>
          <li><strong>resources/</strong>
            <ul>
              <li>static/
                <ul>
                  <li>files/</li>
                </ul>
              </li>
              <li>application.properties</li>
              <li>application-Key.properties</li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
  <li>.gitignore</li>
  <li>README.md</li>
  <li>pom.xml</li>
</ul>
</details>
