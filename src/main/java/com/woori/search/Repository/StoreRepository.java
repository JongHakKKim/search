//10. 사용할 Entity에 필요한 쿼리(SQL)문을 추가 작성
package com.woori.search.Repository;

import com.woori.search.Entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// 참고할 Entity와 id의 데이터형으로 연결
@Repository
public interface StoreRepository extends JpaRepository<StoreEntity , Integer> {
    // 분류(총판명, 총판ID, 총판장ID, 총판장,) 키워드()
    //총판명, 총판ID, 총판장ID, 총판장 , 총판명+총판ID, 총판장ID+총판명 , 전체
    //결과값 여러개이면 List<StoreEntity>, Page<StoreEntity>
    //결과값이 하나이면 StoreEntity, Optional<StoreEntity>

    //총판명 검색
    Page<StoreEntity> findByStoreNameContaining(String keyword, Pageable pageable);
    //StoreEntity u(별칭) : 별칭은 테이블명을 약식표기(알파벳 1글자 지정)
    //String keyword=>@Param("keyword")=>:keyword
    @Query("SELECT u FROM StoreEntity u WHERE u.storeName like %:keyword%")
    Page<StoreEntity> searchStoreName(@Param("keyword") String keyword, Pageable pageable);

    //총판ID
    Page<StoreEntity> findByStoreIdContaining(String keyword, Pageable pageable);
    @Query("SELECT u FROM StoreEntity u WHERE u.storeId like %:keyword%")
    Page<StoreEntity> searchStoreId(@Param("keyword") String keyword, Pageable pageable);

    //총판장ID
    Page<StoreEntity> findByStoreChiefIdContaining(String keyword, Pageable pageable);
    @Query("SELECT u FROM StoreEntity u WHERE u.storeChiefId like %:keyword%")
    Page<StoreEntity> searchStoreChiefId(@Param("keyword") String keyword, Pageable pageable);

    //총판장ID
    Page<StoreEntity> findByStoreChiefContaining(String keyword, Pageable pageable);
    @Query("SELECT u FROM StoreEntity u WHERE u.storeChief like %:keyword%")
    Page<StoreEntity> searchStoreChief(@Param("keyword") String keyword, Pageable pageable);

    //총판명+총판ID
    Page<StoreEntity> findByStoreNameContainingOrStoreIdContaining(String keyword1, String keyword2, Pageable pageable);
    // 긴문장을 다중행으로 처리시 반드시 문자 뒤와 앞에 빈공백 포함
    @Query("SELECT u FROM StoreEntity u WHERE" +
            " u.storeName like %:keyword% or u.storeId like %:keyword%" )
    Page<StoreEntity> searchStoreAll(@Param("keyword") String keyword, Pageable pageable);

    //총판장ID+총판장
    Page<StoreEntity> findByStoreChiefIdContainingOrStoreChiefContaining(String keyword1, String keyword2, Pageable pageable);
    // 긴문장을 다중행으로 처리시 반드시 문자 뒤와 앞에 빈공백 포함
    @Query("SELECT u FROM StoreEntity u WHERE" +
            " u.storeChiefId like %:keyword% or u.storeChief like %:keyword%" )
    Page<StoreEntity> searchStoreChiefAll(@Param("keyword") String keyword, Pageable pageable);

    //모든항목에서 조회
    Page<StoreEntity> findByStoreChiefIdContainingOrStoreChiefContainingOrStoreChiefIdContainingOrStoreChiefContaining(String keyword1, String keyword2, String keyword3, String keyword4, Pageable pageable);
    @Query("SELECT u FROM StoreEntity u WHERE" +
            " u.storeName like %:keyword% or u.storeId like %:keyword% or" +
            " u.storeChiefId like %:keyword% or u.storeChief like %:keyword%")
    Page<StoreEntity> searchAll(@Param("keyword") String keyword, Pageable pageable);
    /*
        findBy 검색(조회)작업하는 JPA 명령을 이용한 메소드 작성법

        1. 기본키를 이용한 조회
        findById(변수) : 변수값으로 id와 일치하는 데이터를 조회

        2. 모든 데이터 조회
        findByAll() : 모든 데잍어를 조회

        3. 지정 필드로 조회
        findBy필드명(변수) : 변수값으로 필드명의 값과 일치하는 데이터를 조회
        findByStoreName(String name) : name의 값이 storeName 필드의 값과 일치하면 조회

        4. 비슷한 값 조회(%값%)
        findBy필드명Containing(변수) : 변수값이 포함된 데이터를 조회
        예)findByStoreNameContaining(STring name) : name 의 값이 storeName에 포함된 데이터

        5. 여러 필드를 사용(AND - 모두 맞으면 , OR~~ 중 하나라도 맞으면)
            서비스에서 문법이 복잡하다.
        findBy필드명1 AND필드명(변수1 , 변수2) : 변수1값이 필드명1과 같고 , 변수2값이 필드명2와 같으면 조회
        예) findByStoreNameAndStoreId(String name, String id) : name 값이 storeName과 같고 , id값이 StoreId와 같으면 조회
            findByStoreNameORStoreId(String name, String id) : name 값이 storeName과 같거나 , id값이 StoreId와 같으면 조회

        6. 정렬
            findBy필드명10rederBy필드명2ASC(변수) : 변수값이 필드명1과 같은 데이터를 필드명2로 오름차순해서 조회
        예) findByStoreNameOrderByIdxDesc(name) : name값이 storeName과 같은 데이터를 idx로 내림차순해서 조회

        7. 정확한 데이터를 조회할 때 (문자열 데이터)
        findBy필드명Like(변수) : 변수값과 필드의 내용이 정확히 일치하는 데이터를 조회
        예) findByStoreNameLike(String name) : name값이 storeName과 정확히 일치하는 데이터를 조회

        8. 페이징 처리
        findBy필드명(변수, Pageable 페이지변수) : 변수값과 같은 데이터를 해당 페이지부분을 조회
        예 ) findByStoreName(String name, Pageable pageable)
        
        9. 숫자 , 날짜 범위처리
        GreaterThen(>) , LessThen(<) , GreaterThenEqual(>=) , LessThenEqual(<=)
        예) findByAgeGreateThen(Integer age) : age값 보다 큰 데이터를 조회
        
        10. 값이 빈 테이터 조회
        findBy필드명IsNull();
        예) findByStoreNameIsNull() : storeName 필드에 값이 없는 데이터만 조회
    */
}
