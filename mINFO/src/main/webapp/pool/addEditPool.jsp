<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>

<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />
	<html>
	<head>
	<title>mINFO</title>
	</head>
	<body>
	<h:form id="mainForm">
		<h:commandLink action="poolList">
			<h:outputText value="#{msg.poolList}" />
		</h:commandLink><br><br>
	
		<h:outputLabel value="#{msg.poolId}" />: <h:outputText
			value="#{pool.currentPool.id}" />
		<Br>
		<h:outputLabel value="#{msg.poolQuestion}" />: <h:inputTextarea
			value="#{pool.currentPool.question}">
			<f:validateLength minimum="1" />
		</h:inputTextarea>
		<br>
		<Br>

		<h:messages showDetail="true" showSummary="false" />
		<h:outputText value="#{msg.availableAnswers}"></h:outputText>:<br>
		
		<h:dataTable var="item" value="#{pool.currentPool.answers}"  >
			<h:column>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<h:inputText value="#{item.answer}" />
			</h:column>
			<h:column>
				<h:commandLink action="#{pool.deleteAnswer}">
					<f:param name="answerId" value="#{item.id}"/>
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
		</h:dataTable>
		
		<c:choose>
			<c:when test="#{msg.poolId == null}">
				<h:commandButton value="#{msg.addNewPool}"
					action="#{pool.performPool}" />
			</c:when>
			<c:otherwise>
				<h:commandButton value="#{msg.save}"
					action="#{pool.performPool}" />
			</c:otherwise>
		</c:choose>
		
		<h:commandButton value="#{msg.addNewAnswer}"
					action="#{pool.addAnswer}" />
	</h:form>

	</body>
	</html>
</f:view>