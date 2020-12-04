/**
 * 
 */
function go_wrt() {
	document.frm.action = "admin_product_write_form";
	document.frm.submit();
}

// 금액을 세자리마다 , 추가
function NumFormat(t) {

	s = t.value;
	s = s.replace(/\D/g, '');  // 정규표현식: 문자열에서 숫자가 아닌 문자를 모두 제거
	l = s.length - 3;
	while (l > 0) {
		s = s.substr(0, l) + ',' + s.substr(l);
		l -= 3;
	}
	t.value = s;
	return t;
}

function go_ab() {// 판매가-원가=순매출을 계산해 준다.
	var theForm = document.frm;
	var a = theForm.price2.value.replace(/,/g, '');		// 판매가
	var b = theForm.price1.value.replace(/,/g, '');		// 원가
	var ab = parseInt(a) - parseInt(b);					// 순매출
	theForm.price3.value = ab;
}

// 상품 등록 요청 함수
function go_save() 
{
	var theForm = document.frm;
	
	if (theForm.kind.value == '') {
		alert('상품분류를 선택하세요.');
		theForm.kind.focus();
	} else if (theForm.name.value == '') {
		alert('상품명을 입력하세요.');
		theForm.name.focus();
	} else if (theForm.price1.value == '') {
		alert('원가를 입력하세요.');
		theForm.price1.focus();
	} else if (theForm.price2.value == '') {
		alert('판매가를 입력하세요.');
		theForm.price2.focus();
	} else if (theForm.content.value == '') {
		alert('상품상세를 입력하세요.');
		theForm.content.focus();
	} else if (theForm.product_image.value == '') {
		alert('상품이미지들 입력하세요.');
		theForm.product_image.focus();
	} else {
		theForm.encoding = "multipart/form-data";
		theForm.price1.value = removeComma(theForm.price1);
		theForm.price2.value = removeComma(theForm.price2);
		theForm.price3.value = removeComma(theForm.price3);

		theForm.action = "admin_product_write";
		theForm.submit();
	}
}

function go_mov() {
	document.frm.action = "admin_product_list";
	document.frm.submit();
}

function go_search() {
	document.frm.action = "admin_product_list";
	document.frm.submit();
}

function go_total() {
	document.frm.key.value = "";
	document.frm.action = "admin_product_list";
	document.frm.submit();
}

// 숫자 입력값에서 모든 ,를 제거하는 함수
function removeComma(input) {
	return input.value.replace(/,/gi, "");
}

// 상세정보 화면에서 상품목록 화면으로 이동
function go_list(pageNum, numPerPage) {
	document.frm.action = "admin_product_list?pageNum="+pageNum+"&numPerPage="+numPerPage;
	document.frm.submit();
}

// 상품정보 수정화면 요청
function go_mod(pseq) {
	document.frm.action = "admin_product_update_form?pseq="+pseq;
	document.frm.submit();
}

// 상품정보 수정 요청
function go_mod_save(pseq) {
	var theForm = document.frm;
	
	if (theForm.kind.value == '') {
		alert('상품분류를 선택하세요.');
		theForm.kind.focus();
	} else if (theForm.name.value == '') {
		alert('상품명을 입력하세요.');
		theForm.name.focus();
	} else if (theForm.price1.value == '') {
		alert('원가를 입력하세요.');
		theForm.price1.focus();
	} else if (theForm.price2.value == '') {
		alert('판매가를 입력하세요.');
		theForm.price2.focus();
	} else if (theForm.content.value == '') {
		alert('상품상세를 입력하세요.');
		theForm.content.focus();
	} else {
		if (confirm("수정 하시겠습니까?")) {
			if (theForm.useyn.checked == true) {
				theForm.useyn.value = "y"
			} else {
				//alert("신규 상품여부"+theForm.useyn.value);
				theForm.useyn.value = "n";
			}
			if (theForm.bestyn.checked == true) {
				theForm.bestyn.value = "y"
			} else {
				//alert("인기 상품여부"+theForm.bestyn.value);
				theForm.bestyn.value = "n";
			}
			// 각종 금액의 값에서 모두 ","를 제거
			theForm.price1.value = removeComma(theForm.price1);
			theForm.price2.value = removeComma(theForm.price2);
			theForm.price3.value = removeComma(theForm.price3);
			
			theForm.encoding = "multipart/form-data";
			theForm.action = "admin_product_update";
			theForm.submit();
		}
	}
}



























