package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private  ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;
	
	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if(p!=null) {
			String email=p.getName();
			UserDtls userDtls=userService.getUserByEmail(email);
			m.addAttribute("user",userDtls);
			Integer countCart=cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart",countCart);
		}
		
		List<Category> allActiveCategory=categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}
	
	@GetMapping("/")
	public String index(Model m) {
		List<Category> allActiveCategory = categoryService.getAllActiveCategory().stream()
				.sorted(Comparator.comparing(Category::getId).reversed())
				.limit(6).toList();
		List<Product> allActiveProducts = productService.getAllActiveProducts("").stream().sorted(Comparator.comparing(Product::getId).reversed())
				.limit(8).toList();
		m.addAttribute("category", allActiveCategory);
		m.addAttribute("products", allActiveProducts);
		return "index";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	//to 
	@GetMapping("/products")
	public String products(Model m, @RequestParam(value="category", defaultValue="") String category,
			@RequestParam(name="pageNo", defaultValue="0") Integer pageNo , @RequestParam(name="pageSize", defaultValue  ="8") Integer pageSize, @RequestParam(defaultValue="") String ch ){
//		System.out.println("category="+category);
		List<Category> categories=categoryService.getAllActiveCategory();
		m.addAttribute("categories",categories);
		m.addAttribute("paramValue",category);
		
//		List<Product> products=productService.getAllActiveProducts(category);
//		m.addAttribute("products",products);
		
		Page<Product> page=null;
		if(StringUtils.isEmpty(ch)) {
		
			page = productService.getAllActiveProductPagination(pageNo, pageSize, category);
			
		}else {
			page = productService.searchActiveProductPagination(pageNo, pageSize,category,ch);
		}
		
		List<Product> products=page.getContent();
		m.addAttribute("products",products);
		m.addAttribute("productsSize", products.size());
		
		
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		return "product";
	}
	
	
	//view details
	@GetMapping("/product/{id}")
	public String product(@PathVariable int id, Model m) {
		Product productById=productService.getProductById(id);
		m.addAttribute("product",productById);
		return "view_product";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException{
		
		Boolean existsEmail = userService.existsEmail(user.getEmail());
		if(existsEmail) {
			session.setAttribute("errorMsg", "Email already exists");
		}else {
			String imageName= file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
			user.setProfileImage(imageName);
			UserDtls saveUser= userService.saveUser(user);
			
		
			if(!ObjectUtils.isEmpty(saveUser)) {
				if(!file.isEmpty()) {
					File saveFile = new ClassPathResource("static/img").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
							+ file.getOriginalFilename());

					System.out.println(path);
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					session.setAttribute("succMsg", "Registered Successfully");
				}else {
					session.setAttribute("erorrMsg", "Something wrong on server");
				}
			}
		}
		
		return "redirect:/register";
		
	}
	
	//forgot password code
	
	@GetMapping("/forgot-password")
	public String showforgotPassword() {
		return "forgot_password.html";
	}
	
	@PostMapping("/forgot-password")
	public String processforgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		UserDtls userByEmail =userService.getUserByEmail(email);
		
		if(ObjectUtils.isEmpty(userByEmail)) {
			session.setAttribute("errorMsg", "Invalid email");
		}else {
			
			String resetToken = UUID.randomUUID().toString();
			userService.updateUserToken(email, resetToken);
			
			//Generate url : http://localhost:8080/reset_password?token= shugfgfjgf
			
			String url=CommonUtil.generateURL(request)+"/reset-password?token=" + resetToken;
			
			
			Boolean sendMail = commonUtil.sendMail(url,email);
			
			if(sendMail) {
				session.setAttribute("succMsg", "Please check your email... Password link is sent");
			}else {
				session.setAttribute("errorMsg", "Something wrong on server ! Mail not sent");
			}
		}
		
		return "redirect:/forgot-password";
	}
	
	//reset password
	@GetMapping("/reset-password")
	public String showresetPassword(@RequestParam String token, HttpSession session, Model m) {
		
		UserDtls userByToken = userService.getUserByToken(token);
		
		if(userByToken == null) {
			m.addAttribute("msg", "Your link is invalid or expired !!");
			return "message";
		}
		m.addAttribute("token",token);
		return "reset_password";
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token,@RequestParam String password, HttpSession session, Model m) {
		
		UserDtls userByToken = userService.getUserByToken(token);
		if(userByToken == null) {
			m.addAttribute("msg", "Your link is invalid or expired !!");
			return "message";
		}else {
			userByToken.setPassword(passwordEncoder.encode(password));
			userByToken.setResetToken(null);
			userService.updateUser(userByToken);
			session.setAttribute("succMsg", "Password Changed Successfully");
			m.addAttribute("msg","Password Changed Successfully");
			return "message";
		}
		
		
	}
	
//	to implement search
	@GetMapping("/search")
	public String searchProduct(@RequestParam String ch, Model m) {
		
		List<Product> searchProducts = productService.searchProduct(ch);
		m.addAttribute("products",searchProducts);
		
		List<Category> categories=categoryService.getAllActiveCategory();
		m.addAttribute("categories",categories);
		
		return "product";
	}
		
}
