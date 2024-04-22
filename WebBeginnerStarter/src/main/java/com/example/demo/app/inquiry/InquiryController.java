package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	//初期化
    private final InquiryService inquiryService;

	//default constructorを使ってinquiry serviceを読み込んでいく
 	public InquiryController(InquiryService inquiryService){
 		this.inquiryService = inquiryService;
 	}

	@GetMapping
	public String index(Model model) {

		List<Inquiry> list = inquiryService.getAll();
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(1);
//		inquiry.setName("Jamie");
//		inquiry.setEmail("sample4@example.com");
//		inquiry.setContents("Hello.");
//		inquiryService.update(inquiry);
		
//		try {
//			
//		}catch(InquiryNotFoundException e){
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}
        model.addAttribute("inquiryList", list);
        model.addAttribute("title", "Inquiry Index");
        
        // index.htmlをよびだす
		return "inquiry/index_boot";
	}

	
	@GetMapping("/form")
	//戻り値の型を設定(この場合はString)
	public String form(InquiryForm inquiryForm, Model model, @ModelAttribute("complete") String complete) {
        model.addAttribute("title", "Inqiry Form");
		return "inquiry/form_boot";
	}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form_boot";
	}


	@PostMapping("/confirm")
	   public String confirm(@Validated InquiryForm inquiryForm, BindingResult result, Model model){
		   if(result.hasErrors()) {
			   model.addAttribute("title", "inquiry Form");
			   return "inquiry/form_boot";
		   }
		   
		   model.addAttribute("title", "Confirm Page");
		   return "inquiry/confirm_boot";
	   }


	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm, BindingResult result, Model model , RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
        	model.addAttribute("title", "InquiryForm");
        	return "inquiry/form";
        }
        
        //データベースの詰め替え
        //inquiry formというクラスからinquiry のentity のクラスにデータを入れ替える
        //自動化できるクラスがある。
        Inquiry inquiry = new Inquiry();
        inquiry.setName(inquiryForm.getName());
        inquiry.setEmail(inquiryForm.getEmail());
        inquiry.setContents(inquiryForm.getContents());
        inquiry.setCreated(LocalDateTime.now());
        inquiryService.save(inquiry);
        
        redirectAttributes.addFlashAttribute("complete", "Registered");
        return "redirect:/inquiry/form";
        		
	}
	
	@ExceptionHandler(InquiryNotFoundException.class)
    public String handleException(InquiryNotFoundException e, Model model) {
		model.addAttribute("message", e);
		return "error/CustomPage";
	}
}
	
