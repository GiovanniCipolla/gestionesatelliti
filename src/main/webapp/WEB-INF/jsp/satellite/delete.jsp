<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Elimina Elemento</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Elimina elemento</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Denominazione:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.denominazione}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Codice:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.codice}</dd>
					    	</dl>
					    	
					    	<dl class="row">
						<dt class="col-sm-3 text-right">Data di Lancio:</dt>
						<dd class="col-sm-9">
							<c:if test="${empty delete_satellite_attr.dataLancio}">
								<span class="text-danger">data di lancio non inserita</span>
							</c:if>
							<c:if test="${not empty show_satellite_attr.dataLancio}">
								<fmt:parseDate value="${delete_satellite_attr.dataLancio}"
									pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${localDateToBeParsed}" />
							</c:if>
						</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Data di Rientro:</dt>
						<dd class="col-sm-9">
							<c:if test="${empty delete_satellite_attr.dataRientro}">
								<span class="text-danger">data di rientro non inserita</span>
							</c:if>
							<c:if test="${not empty show_satellite_attr.dataRientro}">
								<fmt:parseDate value="${delete_satellite_attr.dataRientro}"
									pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${localDateToBeParsed}" />
							</c:if>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Stato :</dt>
						<c:if test="${empty delete_satellite_attr.stato}">
							<span class="text-danger">stato non inserito</span>
						</c:if>
						<c:if test="${not empty show_satellite_attr.stato}">
							<dd class="col-sm-9">${delete_satellite_attr.stato}</dd>
						</c:if>
					</dl>
					    	
					    	
					    </div>
					    <!-- end card body -->
					    
					    <div class='card-footer'>
					    
					        <a href="${pageContext.request.contextPath}/satellite" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					        
					         <form:form method="post" action="${pageContext.request.contextPath}/satellite/delete" >
								<input type="hidden" name="id" value="${delete_satellite_attr.id}" />
								<button type="submit" class="btn btn-danger">Cancella</button>
								</form:form>
						     
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>