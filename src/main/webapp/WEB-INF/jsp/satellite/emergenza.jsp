<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Emergenza</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>disabilita elementi</h5> 
				    </div>
				    <div class='card-body'>
		
							
							
								<div class="col-md-6">
									<label  class="form-label">Numero voci totali presenti nel sistema: ${quanti_satellite_attr}</label>
								</div>
								
								<div class="col-md-6">
									<label  class="form-label"> Numero Voci che verranno modificate in seguito alla procedura: ${rientrano_satellite_attr} </label>
								</div>
							
								<form:form method="post" action="${pageContext.request.contextPath}/satellite/avvioDisabilita" >
								<button type="submit" class="btn btn-danger btn-sm float-right" style="margin-left: 85%">Conferma disabilita tutti</button>
								</form:form>
								<a href="${pageContext.request.contextPath}/satellite"
									class='btn btn-outline-secondary' style='width: 80px'> <i
									class='fa fa-chevron-left'></i> Back
								</a> 
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>