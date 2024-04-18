package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

// 	private final InquiryService inquiryService;

	//Add an annotation here
// 	public InquiryController(InquiryService inquiryService){
// 		this.inquiryService = inquiryService;
// 	}

//	@GetMapping
//	public String index(Model model) {
//
//		//hands-on
//
//		return "inquiry/index";
//	}

	
	@GetMapping("/form")
	//戻り値の型を設定(この場合はString)
	public String form(InquiryForm inquiryForm, Model model, @ModelAttribute("complete") String complete) {
        model.addAttribute("title", "Inqiry Form");
		return "inquiry/form";
	}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}


	@PostMapping("/confirm")
	   public String confirm(@Validated InquiryForm inquiryForm, BindingResult result, Model model){
		   if(result.hasErrors()) {
			   model.addAttribute("title", "inquiry Form");
			   return "inquiry/form";
		   }
		   
		   model.addAttribute("title", "Confirm Page");
		   return "inquiry/confirm";
	   }


	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm, BindingResult result, Model model , RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
        	model.addAttribute("title", "InquiryForm");
        	return "inquiry/form";
        }
        
        redirectAttributes.addFlashAttribute("complete", "Registered");
        return "redirect:/inquiry/form";
        		
	}

}
	
