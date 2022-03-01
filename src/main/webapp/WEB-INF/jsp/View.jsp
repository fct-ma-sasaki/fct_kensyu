<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/style.css" rel="stylesheet" >
<script type="text/javascript">
function deleteCheck( code ) {
    if( confirm("社員コード("+code+")削除しますか？") ) {

    	setCode(code);

    }else {
        return false ;
    }
}

function setCode(code){
	document.getElementById( "code" ).value = code ;
	return true ;
}
</script>
<html style="margin:50px 50px">
  <head>
    <title>社員情報管理</title>
  </head>
  <body>
    <h1 class="bg-info" style="height: 25px; font-size: 20px; margin-bottom: 10px;">社員情報管理(View.jsp)</h1>
    <form:form modelAttribute="viewForm" action="${pageContext.request.contextPath}/view">
    <td style="margin:10px 10px">
      <button type="submit" name="newRegister" value="newRegister" class="btn btn-primary" style="margin-bottom: 10px;">新規登録</button>
    </td>

    <p></p>

    <table class="table table-hover" border="2">
      <tr bgcolor="#eeeeee">
        <th>削除</th>
        <th>修正</th>
        <th>社員コード</th>
        <th>氏名</th>
        <th>所属</th>
        <th>内線</th>
        <th>入社年月日</th>
        <th>性別</th>
       </tr>


       <c:forEach var="record" items="${list}">
         <tr>
           <td>
             <button type="submit" name="delete" value="delete" class="btn btn-primary"
                onclick="return deleteCheck(${record.code})">削除</button>
           </td>

           <td>
             <button type="submit" name="update" value="update" class="btn btn-primary"
                onclick="return setCode(${record.code})">修正</button>
           </td>

           <td name="shainInfo" type="text"><c:out value="${record.code}"/></td>
           <td name="shainInfo" type="text"><c:out value="${record.meisyouKanji}"/></td>
           <td name="shainInfo" type="text"><c:out value="${record.shozokuMei}"/></td>
           <td name="shainInfo" type="text"><c:out value="${record.naisen}"/></td>
           <td name="shainInfo" type="text"><c:out value="${record.nyushaDate}"/></td>
           <td name="shainInfo" type="text"><c:out value="${record.seibetsuMei}"/></td>
         </tr>
       </c:forEach>

     </table>
    <td style="text-aline:right; margin:10px 10px">
      <button type="submit" name="back" value="back" class="btn btn-primary">戻る</button>
    </td>
    <form:input type="hidden" path="code"/>
    </form:form>
  </body>
</html>