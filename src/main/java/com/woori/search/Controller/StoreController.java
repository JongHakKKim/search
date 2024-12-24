//12. 매핑 및 결과 메세지 처리
package com.woori.search.Controller;

import com.woori.search.DTO.StoreDTO;
import com.woori.search.Service.StoreService;
import com.woori.search.Util.PagenationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log
public class StoreController {
    private final StoreService storeService;
    private final PagenationUtil pagenationUtil;
    
    /***********************************
    함수명 : storeDeleteProc(Integer idx)
    입 력 : html로 부터 삭제할 일련번호
    출 력 : 성공 / 실패 메세지
    내 용 : 해당번호로 데이터를 삭제후 메세지를 가지고 list페이지로 이동
    다른맵핑으로 값과 함께 이동할 때는 RedirectAttributes를 사용
     ************************************/

    @GetMapping("/storeDelete")
    public String storeDeleteProc(Integer idx, RedirectAttributes redirectAttributes) {
        Boolean result = storeService.storeDelete(idx);
        //결과처리
        if(result) {
            //삭제하였습니다.
            redirectAttributes.addFlashAttribute("message", "삭제하였습니다.");
        }else {
            // 삭제를 실패하였습니다.
            redirectAttributes.addFlashAttribute("message", "삭제를 실패하였습니다.");
        }
        return "redirect:/storeList";
    }
    /***********************************
    함수명 : storeInsertForm()
    입 력 : 업음
    출 력 : 삽입폼으로 이동
    내 용 : 해당맵핑의 요청이 있으면 해당 HTML로 이동
     ************************************/
    @GetMapping("/storeInsert")
    public String storeInsertForm(Model model) {
        //검증 라이브러리 추가하면
        //입력폼에서 object, field를 이용해서 검증처리
        model.addAttribute("data", new StoreDTO());

        return "insert";
    }
    /***********************************
    함수명 : storeInsertProc(StoreDTO storeDTO)
    입 력 : 입력한 StoreDTO
    출 력 : 저장 결과 메세지를 가지고 list로 이동
    내 용 : 입력받은 데이터를 데이터베이스에 저장하고 , 결과를 가지고 List맵핑으로 이동
     ************************************/
    @PostMapping("/storeInsert")
    public String storeInsertProc(StoreDTO storeDTO,
                                  RedirectAttributes redirectAttributes) {
        StoreDTO result = storeService.storeInsert(storeDTO);
        if(result != null) { // 값이 있으면 , 저장에 성공했으면
            redirectAttributes.addFlashAttribute("message", "저장을 하였습니다.");
        }else { //저장을 실패했으면
            redirectAttributes.addFlashAttribute("message" , "저장을 실패하였습니다.");
        }
        return "redirect:/storeList";
    }

    /***********************************
    함수명 : storeUpdateForm(Integer idx)
    입 력 : 수정할 일련번호
    출 력 : 수정할 DTO를 전달
    내 용 : 일련번호로 해당 데이터를 조회해서 결과값을 HTML에 전달
     ************************************/
    @GetMapping("/storeUpdate")
    public String storeUpdateForm(Integer idx, Model model, RedirectAttributes redirectAttributes) {
        StoreDTO read = storeService.storeRead(idx);
        if(read != null) {// 수정할 데이터가 존재하면
            model.addAttribute("data" , read);

            return "update";
        }

        //수정할 데이터가 존재하지 않으면
        redirectAttributes.addFlashAttribute("message", "해당 데이터가 존재하지 않습니다.");
        return "redirect:/storeList";   // 수정할 데이터가 존재하지 않음녀 목록페이지로 이동
    }

    /***********************************
    함수명 : storeUpdateProc(StoreDTO storeDTO)
    입 력 : 수정한 DTO
    출 력 : 수정처리후 결과 메세지
    설 명 : 수정할 데이터를 저장해서 결과메세지를 가지고 list맵핑으로 이동
     ************************************/
    @PostMapping("/storeUpdate")
    public String storeUpdateProc(StoreDTO storeDTO , RedirectAttributes redirectAttributes) {
        StoreDTO result = storeService.storeUpdate(storeDTO);
        if(result != null) {    // 수정을 성공했을 때 , 결과값이 비어있지 않으면
            redirectAttributes.addFlashAttribute("message" , "수정하였습니다.");
        }else { // 수정에 실패했을때
            redirectAttributes.addFlashAttribute("message", "수정에 실패하였습니다.");
        }

        return "redirect:/storeList";
    }
    /***********************************
    함수명 : storeReadProc(Integer idx)
    입 력 : 읽어올 일련번호
    출 력 : 조회된 DTO
    설 명 : 해당번호로 데이터베이스에서 조회하여 결과를 전달(HTML 상세페이지)
     ************************************/
    @GetMapping("/storeRead")
    public String storeReadProc(Integer idx, RedirectAttributes redirectAttributes , Model model){
        StoreDTO result = storeService.storeRead(idx);
        if(result != null) { // 조회한 결과가 존재하면
            model.addAttribute("data" , result);
            return "read";
        }
        //조회한 결과가 존재하지 않으면
        redirectAttributes.addFlashAttribute("message", "해당하는 데이터가 존재하지 않습니다.");

        return "redirect:/storeList";
    }

    /***********************************
    함수명 : storeListForm(Pageable pageable , String type, String keyword)
    입 력 : 조회할 페이지 정보, 분류대상 ,검색 키워드
    출 력 : Page<StoreDTO>
    설 명 : 분류대상에 키워드로 조회한 해당 페이지 데이터를 전달
     ************************************/
    @GetMapping({"/", "/storeList"})
    public String storeListForm(
            @PageableDefault(page=1)Pageable pageable   // 페이지 정보, 페이지정보가 없으면 기본값으로 1페이지
            , @RequestParam(value = "type", defaultValue = "") String type  // 검색대상 , 없으면 기본값은 ""
            , @RequestParam(value = "keyword" , defaultValue = "") String keyword  // 키워드, 없으면 기본값은 null
            , Model model) {

        Page<StoreDTO> result = storeService.storeList(pageable, type, keyword);

        Map<String, Integer> pageInfo = PagenationUtil.pagination(result);

        model.addAttribute("data", result); // 데이터 전달
        model.addAllAttributes(pageInfo); // 페이지 정보
        model.addAttribute("type", type); // 검색분륜
        model.addAttribute("keyword", keyword); // 키워드
        return "list";
    }
}
