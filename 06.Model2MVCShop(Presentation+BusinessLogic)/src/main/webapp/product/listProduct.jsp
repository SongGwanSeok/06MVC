<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>��ǰ ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}

function fncGetProductListByCategory(category) {
	document.getElementById("currentPage").value = 1;
	document.getElementById("categoryNo").value = category;
   	document.detailForm.submit();		
}

function clearInput(){
	document.getElementById("textInput1").value = null;
	document.getElementById("textInput2").value = null;
}

function changeSearchkeyword2(){
	var searchCondition = document.getElementById("searchCondition");
	var selectValue = searchCondition.options[searchCondition.selectedIndex].value;
	console.log("selectValue : " + selectValue);
	var searchKeyword2 = document.getElementById("searchKeyword2");
	if(selectValue == 2 && searchKeyword2.style.display == 'none'){
		searchKeyword2.style.display = 'inline';
		clearInput();
	}else if(selectValue != 2 && searchKeyword2.style.display == 'inline'){
		searchKeyword2.style.display = 'none';
		clearInput();
	}
	
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct?menu=${menu }" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						${menu.equals("manage") ? "��ǰ ����" : "��ǰ �����ȸ"}
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
		<td align="right">
			<select name="orderStandard"  class="ct_input_g" style="width:100px">
				<option value="regDate" ${ search.orderStandard.equals("regDate") ? "selected" : "" }>��ǰ �����</option>
				<option value="lowPrice" ${ search.orderStandard.equals("lowPrice") ? "selected" : "" }>���� ������</option>
				<option value="highPrice" ${ search.orderStandard.equals("highPrice") ? "selected" : "" }>���� ������</option>
			</select>
			<select name="searchCondition" id="searchCondition" class="ct_input_g" style="width:80px" onchange="changeSearchkeyword2()">
				<c:if test="${(!empty user) && (user.role == 'manager' || user.role == 'admin') }">
				<option value="0" ${!empty search.searchCondition 
												&& search.searchCondition.equals("0")? "selected" : "" } >��ǰ��ȣ</option>
				</c:if>
				<option value="1" ${!empty search.searchCondition 
												&& search.searchCondition.equals("1")? "selected" : "" } >��ǰ��</option>
				<option value="2" ${!empty search.searchCondition 
												&& search.searchCondition.equals("2")? "selected" : "" } >��ǰ����</option>
			</select>
			<input 	type="text" id="textInput1"  name="searchKeyword"  value="${search.searchKeyword }" 
							class="ct_input_g" style="width:200px; height:19px" >
			<div id="searchKeyword2" style="display:${!empty search.searchCondition 
												&& search.searchCondition.equals('2')? 'inline' : 'none'}">
				~ <input 	type="text" id="textInput2"  name="priceRange"  value="${search.priceRange }" 
								class="ct_input_g" style="width:200px; height:19px" >
			</div>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
ī�װ�
<input type="hidden" name="categoryNo" id="categoryNo"/>
<c:forEach var="category" items="${categoryList }">
	 | <a href="javascript:fncGetProductListByCategory(${category.categoryNo })">${category.categoryName }</a>
</c:forEach>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ī�װ�</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>		
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
				<a href="/getProduct?prodNo=${product.prodNo }&menu=${menu}">${product.prodName }</a>
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
			<td align="left">${product.category.categoryName }</td>
			<td></td>
			<td align="left">
			<c:if test="${(!empty user) && (user.role == 'manager' || user.role == 'admin') }">
				<c:if test="${empty product.proTranCode }">
					�Ǹ���
				</c:if>
				<c:if test="${product.proTranCode == '1' }">
					���ſϷ� <a href="/updateTranCodeByProd?prodNo=${product.prodNo }&tranCode=2" >����ϱ�</a>
				</c:if>
				<c:if test="${product.proTranCode == '2' }">
					�����
				</c:if>
				<c:if test="${product.proTranCode == '3' }">
					��ۿϷ�
				</c:if>
			
			</c:if>
			<c:if test="${(empty user) || (user.role == 'user') }">
				<c:if test="${empty product.proTranCode }">
					�Ǹ���
				</c:if>
				<c:if test="${!empty product.proTranCode }">
					������
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
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>