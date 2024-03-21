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
		var newCategoryName = prompt("���� �� ī�װ� �̸��� �Է��ϼ���.", "");
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
	    if (confirm("������ �����Ͻðڽ��ϱ�?")) {
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
						ī�װ� ����
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
		<td colspan="11" >��ü ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ī�װ� ��</td>
		<td class="ct_line02"></td>	
		<td class="ct_list_b" width="150">��ǰ ��</td>
		<td class="ct_line02"></td>	
		<td class="ct_list_b" width="150">����</td>
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
				<a href="javascript:fucUpdateCategory(${category.categoryNo })">����</a>
				<a href="#" onclick="confirmDelete(${category.categoryNo})">����</a>
			</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<br/>
<hr/>
<h2> ī�װ� �߰� </h2>

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
					<a href="javascript:fncAddCategory()">���</a>
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