<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/style.css" rel="stylesheet" >
<html style="margin:50px 50px">
  <head>
    <td>
      <b><font color="#0000ff"><c:out value="${searchForm.msg}"/></font></b>
    </td>
    <title >社員検索 </title>
  </head>
  <body>
    <h1 class="bg-info" style="height:30px;vertical-align:center;">社員検索 (Search.jsp)</h1>
    <form:form modelAttribute="searchForm" action="${pageContext.request.contextPath}/search" method="post">
    <table>
    <tbody>
        <tr>
          <td align="right">社員コード：</td>
            <td>
              <form:input path="code" size="9" maxlength="8" />
            </td>
        </tr>
        <tr>
          <td align="right">氏名：</td>
          <td>
            <form:input path="meisyouKanji" size="21" maxlength="20" />
          </td>
        </tr>
        <tr>
          <td  align="right">所属：</td>
          <td>
            <form:select path="shozokuMei">
              <form:option value="">未選択</form:option>
              <form:option value="開発">開発</form:option>
              <form:option value="営業">営業</form:option>
              <form:option value="総務">総務</form:option>
              <form:option value="経理">経理</form:option>
            </form:select>
          </td>
        </tr>
        <tr>
          <td  align="right">内線：</td>
          <td>
            <form:input path="naisen" size="5" maxlength="4" />
          </td>
        </tr>
        <tr>
          <td  align="right" >性別：</td>
          <td>
            <input type="radio" name="seibetsuMei" value="" checked>未選択
            <input type="radio" name="seibetsuMei" value="男性">男性
            <input type="radio" name="seibetsuMei" value="女性">女性
          </td>
        </tr>
    </tbody>
    </table>
        <tr>
          <td>
            <button type="submit" class="btn btn-primary" name="search">検索</button>
            <button type="submit" name="newRegister" value="newRegister" class="btn btn-primary">新規登録</button>
          </td>
        </tr>
    </form:form>
  </body>
</html>