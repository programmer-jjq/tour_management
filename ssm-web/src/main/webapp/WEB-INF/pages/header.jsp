<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- 页面头部 -->
<header class="main-header">
	<!-- Logo -->
	<a href="${pageContext.request.contextPath}/index.jsp" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>数据</b></span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>数据</b>后台管理</span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
		   role="button"> <span class="sr-only">Toggle navigation</span>
		</a>

		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">

				<li class="dropdown user user-menu">
					<a href="" class="dropdown-toggle" data-toggle="dropdown"> <img
						src="${pageContext.request.contextPath}/img/user2-160x160.jpg"
						class="user-image" alt="User Image"> <span class="hidden-xs">
						<!-- authentication标签：获取当前登录用户的信息 -->
						<security:authentication property="principal.username"/>
					</span>

				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
								src="${pageContext.request.contextPath}/img/user2-160x160.jpg"
								class="img-circle" alt="User Image"></li>

						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-right">
								<a href="${pageContext.request.contextPath}/logout"
								   class="btn btn-default btn-flat">注销</a>
							</div>

							<%--
							<form action="${pageContext.request.contextPath}/logout" method="post">
							<!-- 如果开启CSRF防护机制，要在认证form表单携带 token，请求方式必须为 post-->
							<security:csrfInput/>
								<input type="submit" value="注销"/>
							</form>
							--%>

						</li>
					</ul></li>

			</ul>
		</div>
	</nav>
</header>
<!-- 页面头部 /-->