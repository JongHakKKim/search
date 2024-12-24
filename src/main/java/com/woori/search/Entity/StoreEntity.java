// 8. 테이블 정보
package com.woori.search.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter  // 개별적으로 변수처리시
@AllArgsConstructor
@NoArgsConstructor
// 상황에 따라 지정해서 사용(2개 이상의 테이블 join시 ToString() 사용하지 않는다.
// 2개 테이블에 ToString() 존재하면 무한반복으로 처리
// 2개 테이블 중 한 테이블(부모테이블)에서만 ToString() 을 사용
@ToString
@Builder
@Entity
@Table(name = "store")
@SequenceGenerator(  //순차처리 생성기
        name = "store_seq",  //순차값 정보를 저장할 테이블명
        sequenceName = "store_seq",  //필드명
        initialValue = 1, //초기값
        allocationSize = 1 //할당값(증가값)
)
public class StoreEntity extends BaseEntity {
    @Id //기본키
    // 자동화 처리
    // GenerationType.AUTO : 자동으로 타입을 설정
    // GenerationType.IDENTITY : 값이 중복되지 않도록 생성
    // GenerationType.SEQUENCE : 값을 순차적으로 생성
    // generator : 자동생성 참조 테이블
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_seq")
    @Column(name = "idx", nullable = false) //생략불가능
    private Integer idx;
    //필드명은 가능하면 소문자로 구성
    @Column(name = "storename", length =45) //nullable 생략처리
    private String storeName;
    @Column(name = "storeid", length =45)
    private String storeId;
    @Column(name = "storechiefid", length =45)
    private String storeChiefId;
    @Column(name = "storechief", length =45)
    private String storeChief;
    @Column(name = "storetel", length =25)
    private String storeTel;
    @Column(name = "storedel", length =1)
    private String storeDel;
}
// 반드시 변수명, 필드명에 오타 확인
// 서버를 실행한 후 Entity를 변경했을 때는 application.properties에서
// update를 create로 수정후 서버를 1번 실행하면 테이블정보가 수정된다.
