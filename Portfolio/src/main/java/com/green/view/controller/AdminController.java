package com.green.view.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.green.biz.admin.AdminService;
import com.green.biz.admin.WorkerVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.SalesQuantity;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;
import com.green.biz.utils.Criteria;
import com.green.biz.utils.PageMaker;

@SessionAttributes("adminUser")
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private QnaService qnaService;
	
	@RequestMapping(value="admin_login_form")
	public String adminLoginView() {
		
		return "admin/main";
	}
	
	@RequestMapping(value="admin_logout")
	public String adminLogout(SessionStatus status) {
		
		status.setComplete();
		
		return "admin/main";
		
	}
	
	@RequestMapping(value="admin_login")
	public String adminLoginAction(@RequestParam(value="workerId") String workerId,
								   @RequestParam(value="workerPwd") String workerPwd,
								   Model model) {
		
		int result = adminService.workerCheck(workerId, workerPwd);
		
		if (result == 1) {	// 정상적인 로그인
			WorkerVO adminUser = adminService.getEmployee(workerId);
			
			model.addAttribute("adminUser", adminUser);
			
			model.addAttribute("key", "");
			return "redirect:admin_product_list";
		} else {
			if (result == 0) {
				model.addAttribute("message", "비밀번호를 확인하세요");
			} else {
				model.addAttribute("message", "아이디를 확인하세요");
			} 
			return "admin/main";
		}

	}
	/* 페이징이 적용 안된 목록 조회 */
	/*
	@RequestMapping(value="admin_product_list")
	public String adminProductList(@RequestParam(value="key", defaultValue="", required=false)  String key, 
								   HttpSession session, Model model) {
		
		WorkerVO adminUser = (WorkerVO)session.getAttribute("adminUser");
		
		
		if (adminUser == null) {
			return "admin/main";
		} else {
			List<ProductVO> prodList = productService.getListProduct(key);
		
			int prodCount = productService.countProductList(key);
			
			model.addAttribute("productList", prodList);
			model.addAttribute("productListSize", prodCount);
		
			return "admin/product/productList";
		}
	}
	*/
	
	// 페이징 처리가 구현된 제품 목록 조회
	@RequestMapping(value="admin_product_list")
	public String adminProductList(@RequestParam(value="key", defaultValue="", required=false)  
									  			 String key, 
									  			 HttpSession session, Model model,
									  			 Criteria criteria) {
			
		WorkerVO adminUser = (WorkerVO)session.getAttribute("adminUser");
			
			
		if (adminUser == null) {
			return "admin/main";
		} else {
			List<ProductVO> prodList = productService.getListProductPaging(key, criteria);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);
			
			// 조회할 품목의 갯수	
			int prodCount = productService.countProductList(key);
			pageMaker.setTotalCount(prodCount);
			System.out.println("페이지 정보 : " + pageMaker);
			
			model.addAttribute("productList", prodList);
			model.addAttribute("productListSize", prodCount);
			model.addAttribute("pageMaker", pageMaker);
			
			return "admin/product/productList";
		}
	}
	
	@RequestMapping(value="admin_product_write_form")
	public String adminProductWriteView(Model model) {
		
		String kindList[] = {"Food", "Home", "Wear", "Play", "Beauty", "Health"};
		
		model.addAttribute("kindList", kindList);
		
		return "admin/product/productWrite";
	}
	
	@RequestMapping(value="admin_product_write")
	public String adminProductWriteAction(@RequestParam(value="product_image") MultipartFile uploadFile,
											ProductVO vo, Model model, HttpSession session) {
	
		WorkerVO adminUser = (WorkerVO) session.getAttribute("adminUser");
		
		if (adminUser == null) {
			
			return "admin/main";
		} else {
			
			String fileName = "";
			
			if (!uploadFile.isEmpty()) {
				String root_path =
						session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
				
				System.out.println("프로잭트 Root 경로: " + root_path);
				fileName = uploadFile.getOriginalFilename();
				
				File file = new File(root_path + fileName);
			
				try {
					uploadFile.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
				
			vo.setImage(fileName);
			
			productService.insertProduct(vo);
			
			return "redirect:admin_product_list";
		}
	}
	
	@RequestMapping(value="admin_product_detail")
	public String adminProductDetail(ProductVO vo, Criteria criteria, Model model) {
		
		String[] kindList = {"", "Food", "Home", "Wear", "Play", "Beauty", "Health"};
		
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);
		model.addAttribute("kind", kindList[Integer.parseInt(product.getKind())]);
		model.addAttribute("criteria", criteria);
		
		return "admin/product/productDetail";
	}
	
	@RequestMapping(value="admin_product_update_form")
	public String adminProductUpdateView(ProductVO vo, Model model) {
		
		String[] kindList = {"Food", "Home", "Wear", "Play", "Beauty", "Health"};
		
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);
		model.addAttribute("kindList", kindList);
		
		return "admin/product/productUpdate";
	}
	
	@RequestMapping(value="admin_product_update")
	public String adminProductUpdate(@RequestParam(value="product_image") MultipartFile uploadFile,
									 ProductVO vo, HttpSession session) {
		
		WorkerVO adminUser = (WorkerVO) session.getAttribute("adminUser");
		
		if (adminUser == null) {
			
			return "admin/main";
		} else {
			
			String fileName = "";
			
			if (!uploadFile.isEmpty()) {
				String root_path =
						session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
				
				System.out.println("프로잭트 Root 경로: " + root_path);
				fileName = uploadFile.getOriginalFilename();
				
				File file = new File(root_path + fileName);
			
				try {
					uploadFile.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
				}
				vo.setImage(fileName);
			}	
			if (vo.getBestyn() == null) {
				vo.setBestyn("n");
			}
			if (vo.getUseyn() == null) {
				vo.setUseyn("n");
			}
			productService.updateProduct(vo);
			
			return "redirect:admin_product_detail?pseq="+vo.getPseq();
		}
	}
	
	// 주문 목록 표시
	@RequestMapping(value="admin_order_list")
	public String adminOrderList(@RequestParam(value="key", defaultValue="") String key, Model model) {
		
		List<OrderVO> orderList = orderService.listOrder(key);
		
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	// 주문 완료 처리
	@RequestMapping(value="admin_order_save")
	public String adminOrderSave(@RequestParam(value="result") int[] odseq) {
		
		for (int i=0; i < odseq.length; i++) {
			orderService.updateOrder(odseq[i]);
		}
		return "redirect:admin_order_list";
	}
	
	@RequestMapping(value="admin_member_list")
	public String adminMemberList(@RequestParam(value="key", defaultValue="") String name, Model model) {
		
		List<MemberVO> listMember = memberService.listMember(name);
		
		model.addAttribute("memberList", listMember);
		
		return "admin/member/memberList";
	}
	
	// 게시글 전체 목록 조회
	@RequestMapping(value="admin_qna_list")
	public String adminQnaList(Model model) {
		
		List<QnaVO> qnaList = qnaService.listAllQna();
		
		model.addAttribute("qnaList", qnaList);
		
		return "admin/qna/qnaList";
	}
	
	@RequestMapping(value="admin_qna_detail")
	public String adminQnaDetail(@RequestParam(value="qseq")int qseq, Model model) {
		
		QnaVO qna = qnaService.getQna(qseq);
		
		model.addAttribute("qnaVO", qna);
		
		return "admin/qna/qnaDetail";
	}
	
	@RequestMapping(value="admin_qna_repsave")
	public String adminQnaRepsave(QnaVO vo) {
		
		qnaService.updateQna(vo);
		
		return "redirect:admin_product_list";
	}
	
	// 상품별 판매 실적 화면 표시
	@RequestMapping(value="admin_sales_record_form")
	public String adminSalesRecordView() {
		
		return "admin/order/salesRecords";
	}
	
	// 상품별 판매 실적 데이터 조회
	@RequestMapping(value="sales_record_chart", produces="application/json; charset=utf-8")
	@ResponseBody
	public List<SalesQuantity> salesRecordChart() {
	
	List<SalesQuantity> listSales = productService.getProductSales(); 
	
	System.out.println("<<<<<판매실적>>>>>");
	System.out.println("   제품명     수량     ");
	System.out.println("----------------");
	
	for (SalesQuantity item : listSales) {
		System.out.printf("%10s%3d\n", item.getPname(), item.getQuantity());
	}
	System.out.println("================");
	
	return listSales;
	}
}

















































