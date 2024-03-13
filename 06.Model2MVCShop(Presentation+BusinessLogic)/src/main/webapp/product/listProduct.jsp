<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>상품 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${menu }" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						${menu.equals("manage") ? "상품 관리" : "상품 목록조회"}
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<c:if test="${ !empty search.searchCondition }">
			<td align="right">
				<select name="searchCondition" class="ct_input_g" style="width:80px">
					<c:if test="${(!empty user) && (user.role == 'manager' || user.role == 'admin') }">
					<option value="0" ${search.searchCondition.equals("0")? "selected" : "" } >상품번호</option>
					</c:if>
					<option value="1" ${search.searchCondition.equals("1")? "selected" : "" } >상품명</option>
					<option value="2" ${search.searchCondition.equals("2")? "selected" : "" } >상품가격</option>
				</select>
				<input 	type="text" name="searchKeyword"  value="${search.searchKeyword }" 
								class="ct_input_g" style="width:200px; height:19px" >
			</td>
		</c:if>
		<c:if test="${empty search.searchCondition }">
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<c:if test="${(!empty user) && (user.role == 'manager' || user.role == 'admin') }">
				<option value="0">상품번호</option>
				</c:if>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" >
		</td>
		</c:if>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">등록일</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list }">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i}</td>
			<td></td>
			<td align="left">
			<c:if test="${empty product.proTranCode }">
				<a href="/getProduct.do?prodNo=${product.prodNo }&menu=${menu}">${product.prodName }</a>
			</c:if>
			<c:if test="${!empty product.proTranCode }">
				${product.prodName }
			</c:if>
			</td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate }</td>
			<td></td>
			<td align="left">
			<c:if test="${(!empty user) && (user.role == 'manager' || user.role == 'admin') }">
				<c:if test="${empty product.proTranCode }">
					판매중
				</c:if>
				<c:if test="${product.proTranCode == '1' }">
					구매완료 <a href="/updateTranCodeByProd.do?prodNo=${product.prodNo }&tranCode=2" >배송하기</a>
				</c:if>
				<c:if test="${product.proTranCode == '2' }">
					배송중
				</c:if>
				<c:if test="${product.proTranCode == '3' }">
					배송완료
				</c:if>
			
			</c:if>
			<c:if test="${(empty user) || (user.role == 'user') }">
				<c:if test="${empty product.proTranCode }">
					판매중
				</c:if>
				<c:if test="${!empty product.proTranCode }">
					재고없음
				</c:if>
			</c:if>
			</td>		
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value="1"/>
			
			<c:import var="pageNavigator" url="../common/pageNavigator.jsp" scope="request">
				<c:param name="name" value="Product"/>
			</c:import>
			${pageNavigator }
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>