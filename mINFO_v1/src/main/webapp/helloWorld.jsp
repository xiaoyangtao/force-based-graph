<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view locale="#{facesContext.externalContext.request.locale}" >
	<f:loadBundle var="msg" basename="message"  />
	<html>
	<head>
	<title>Hello World</title>
	</head>
	<body>
	<h:form id="mainForm" >
		<h:panelGrid columns="2">
			Locale:<h:outputFormat
				value="#{facesContext.externalContext.request.locale}" />
			Encoding:<h:outputFormat
				value="#{facesContext.externalContext.requestCharacterEncoding}"/>
			<h:outputLabel for="name" value="#{msg.inputName}" />
			<h:inputText id="name" value="#{helloWorld.name}" required="true" >
				<f:validateLength minimum="10"/>
			</h:inputText>
			<h:commandButton value="Press me" action="#{helloWorld.send}" />
			<h:commandButton value="Press me 2" action="#{helloWorld.send2}" />
			<h:messages showDetail="true" showSummary="false" />
		</h:panelGrid>
	</h:form>

	</body>
	</html>
</f:view>