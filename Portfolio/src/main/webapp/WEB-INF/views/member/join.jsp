<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ include file="../header.jsp" %>   
<%@ include file="sub_img.html"%> 
<%@ include file="sub_menu.html" %>   
  <article>
    <h2>Join Us</h2>
    <form id="join" action="join" method="post" name="formm">
      <fieldset>
        <legend>기본 정보</legend>
        <label>사용자 아이디</label>
        <input type="text"      name="id"        size="12"  >
        <input type="hidden"    name="reid">
        <input type="button"    value="중복 체크"  class="dup" onclick="idcheck()"><br>
        <label>비빌번호</label> 
        <input type="password"  name="pwd"><br> 
        <label>비밀번호 확인</label> 
        <input type="password"  name="pwdCheck"><br> 
        <label>이름</label>
        <input type="text"      name="name"><br> 
        <label>이메일</label>
        <input type="text"      name="email"><br>
        
      </fieldset>
      <fieldset>
        <legend>선택 사항</legend>
        <label>우편번호</label> 
        <input type="text"       name="zip_num"   size="10" >      
        <input type="button"     value="주소 찾기" class="dup" onclick="post_zip()"><br>
        <label>상세주소</label> 
        <input type="text"        name="addr1"   size="50"> <br>
        <input type="text"        name="addr2"   size="25" style="margin-left: 140px"> <br>
        <label>전화번호</label> 
        <input  type="text"       name="phone"><br>
      </fieldset>
      <div class="clear"></div>
      <div id="buttons">
        <input type="button"    value="회원가입"   class="submit" onclick="go_save()"> 
        <input type="reset"      value="취소"     class="cancel">
      </div>
      <br>
    </form>
  </article>
<%@ include file="../footer.jsp" %>
  