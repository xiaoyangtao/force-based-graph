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
		<h:outputLabel value="#{msg.poolCount}" />: <h:outputLabel
			value="#{pool.poolCount}" />
		<h:messages showDetail="true" showSummary="false" />
		<br>
		<br>
		<h:commandLink action="#{pool.newPool}">
			<h:outputText value="#{msg.addNewPool}" />
		</h:commandLink>
		<h:dataTable var="item" value="#{pool.poolList}" >
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.poolId}" />
				</f:facet>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.poolQuestion}" />
				</f:facet>
				<h:outputText value="#{item.question}" />
			</h:column>
			<h:column>
				<h:commandLink action="#{pool.editPool}">
					<f:param name="poolId" value="#{item.id}"/>
					<h:outputText value="#{msg.edit}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<h:commandLink action="#{pool.deletePool}">
					<f:param name="poolId" value="#{item.id}"/>
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
		</h:dataTable>
	</h:form>




	</body>
	</html>
</f:view>