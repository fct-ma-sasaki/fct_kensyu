<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/style.css" rel="stylesheet" >
<html style="margin:50px 50px">
  <head>
    <title >社員登録 </title>
    <script src="/js/calendar.js" ></script>
  </head>
  <body>
    <h1 class="bg-info" style="height:30px;vertical-align:center;">社員登録 (Register.jsp)</h1>
    <form:form modelAttribute="registerForm" action="${pageContext.request.contextPath}/register" method="post">
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
          <th align="right">入社年月日：</th>
          <td>
            <form:input path="nyushaDate" size="12" maxlength="12" style="background:#EEeeEE" />
              <input name="Calendar" type="button" value="  "style="background-image: url(/images/calendar.gif);
                     width:20px;
                     height:20px;
                     border:0;
                     cursor:pointer;" onClick="wrtCalendar(event,this.form.nyushaDate,'yyyy/m/d');" />
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
            <button type="submit" class="btn btn-primary" name="register">登録</button>
          </td>
        </tr>
        <form:hidden path="registerFlg" />
    </form:form>
  </body>
</html>