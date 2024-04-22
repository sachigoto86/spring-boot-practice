package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {
	private final JdbcTemplate jdbcTemplate;
	//以下にjdbdの中身を詰めていく
	
	@Autowired
	public SampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}
	
	@GetMapping("/test")
	//String test becomes the html file name
	public String test(Model model) {
		// set data to pass into the html 
		model.addAttribute("title", "Inquiry Form");
		
		String sql = "SELECT id, name, email"
				+ " FROM inquiry WHERE id = 1";
		
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));
		//Map型のインターフェイスを取得
		//キーはStringでテーブルのカラム名
		//Objectはテーブルの値が対応
		//Objectを型にしているのは、カラムのデータに合わせた様々な型に柔軟に対応させるため。
		// test.html gets rendered
		return "test";

	}

}
