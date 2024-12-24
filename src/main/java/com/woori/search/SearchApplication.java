package com.woori.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//JPA 감사(auditing)을 활성화
//엔티티의 레코드의 생성 및 수정 시간을 자동 기록하는데 사용
@EnableJpaAuditing
public class SearchApplication {

    public static void main(String[] args) {

        SpringApplication.run(SearchApplication.class, args);
    }

}
// 배포파일 war 지정하면 ServletInitializer 파일이 존재

//4. 주요 작업 폴더(패키지)를 생성
//Config, Controller, DTO, Entity, Repository, Service, Util, Constant
//Config : 빈 등록(변수, 메소드), 보안, 로그인
//Controller : 요청에 따라 맵핑처리(Html, Service간에 연결)
//DTO : 변수그룹, HTML과 Service에 자료를 전송
//Entity : 변수그룹(테이블 정보) , Service와 Repository에 자료를 전송
//Repository : 데이터베이스 처리르 ㄹ위한 SQL문 구성
//Service : 내부적으로 처리할 내용(데이터베이스 처리)
//Util : 독립적인 메소드를 구성(날짜처리 , 페이지처리 , AI , AWS, 채팅, 이메일)
//Constant : 고정화 된 값을 처리(열거형) - 절대 변화없이 사용할 값
// 상품에 대해서 신상품 ,추천상품 , 세일상품