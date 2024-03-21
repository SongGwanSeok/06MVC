<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	
	function fncAddCategory() {
		$('#categoryForm').submit();
		// document.getElementById('categoryForm').submit();
	}
	
	function fucUpdateCategory(fcategoryNo) {
		var newCategoryName = prompt("변경 할 카테고리 이름을 입력하세요.", "");
	    if (newCategoryName !== null) {
	        console.log(newCategoryName + fcategoryNo);
	        $('fcategoryNo').val(fcategoryNo);
	        $('newCategoryName').val(newCategoryName);
	        $('categoryUpdateForm').submit();
	        //document.getElementById("fcategoryNo").value = fcategoryNo;
	        //document.getElementById('newCategoryName').value = newCategoryName;
	        //document.getElementById('categoryUpdateForm').submit();
	    }
	}
	
	function confirmDelete(categoryNo) {
	    if (confirm("정말로 삭제하시겠습니까?")) {
	        //window.location.href = "/deleteCategory?categoryNo=" + categoryNo;
	    	$(location).attr("href", "/deleteCategory?categoryNo=" + categoryNo);
	    } 
	}
</script>
</head>
<body bgcolor="#ffffff" text="#000000">

<div style="width:50%; margin-left:10px;">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						카테고리 관리
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<form name="categoryUpdateForm" id="categoryUpdateForm" action="/updateCategory" method="post">
	<input type="hidden" name = "fcategoryNo" id="fcategoryNo"/>
	<input type="hidden" name="newCategoryName" id="newCategoryName"/>
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">카테고리 명</td>
		<td class="ct_line02"></td>	
		<td class="ct_list_b" width="150">상품 수</td>
		<td class="ct_line02"></td>	
		<td class="ct_list_b" width="150">관리</td>
		<td class="ct_line02"></td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var="i" value="0" />
	<c:forEach var="category" items="${list }">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">
				${ i}
			</td>
			<td></td>
			<td align="center">
				${ category.categoryName }
			</td>
			<td></td>
			<td align="center">
				${ category.productCnt }
			</td>
			<td></td>
			<td align="center">
				<a href="javascript:fucUpdateCategory(${category.categoryNo })">수정</a>
				<a href="#" onclick="confirmDelete(${category.categoryNo})">삭제</a>
			</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<br/>
<hr/>
<h2> 카테고리 추가 </h2>

<form name="categoryForm" id="categoryForm" action="/addCategory" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%">
			<input type="text" name ="categoryName" />
		</td>
		<td align="right">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01"  style="padding-top: 3px;">
					<a href="javascript:fncAddCategory()">등록</a>
				</td>
				<td width="14" height="23">
					<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>