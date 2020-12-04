/**
 * 장바구니에 담기
 */
function go_cart() {
	if (document.formm.quantity.value == "") {
		alert("수량을 입력해 주세요!");
		document.formm.quantity.focus();
	} else {
		document.formm.action="cart_insert";
		document.formm.submit();
	}
}

// 장바구니 항목 삭제
function go_cart_delete() {
	var count = 0;	// 삭제할 항목의 갯수 저장
	
	alert("length="+document.formm.cseq.length);
	// 장바구니에 상품이 하나일 경우 배열로 인식이 안되기 때문에 배열의 길이에 값이 안들어감
	if (document.formm.cseq.length == undefined) {
		// 체크박스가 하나만 체크되어 있는 경우를 확인
		if (document.formm.cseq.checked == true) {
			count++;
		}
	}
	// 장바구니에 상품이 두개 이상 체크되어 있을 경우 체크된 갯수를 확인
	for (var i=0; i < document.formm.cseq.length; i++) {
		if (document.formm.cseq[i].checked == true) 
			count++;
	}
	if (count == 0) {
		alert("삭제할 항목을 선택해 주세요");
	} else {
		document.formm.action = "cart_delete";
		document.formm.submit();
	}
}

function go_order_insert() {
	document.formm.action = "order_insert";
	document.formm.submit();
}
































