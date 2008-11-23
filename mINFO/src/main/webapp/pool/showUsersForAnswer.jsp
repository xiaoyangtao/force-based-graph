<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf" %>
<%@ include file="/common/menu.jspf" %>
  
<div id="mainarea">
<div id="contentarea">

<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />

	<h:form id="mainForm">
		<h:commandLink action="back">
			<h:outputText value="#{msg.back}" />
		</h:commandLink>
		<br>

		<h:messages showDetail="true" showSummary="false" />
		<h:outputLabel value="#{msg.poolId}" />: <h:outputLabel
			value="#{pool.currentAnswer.pool.id}" />
		<br>

		<h:outputLabel value="#{msg.question}" />: <h:outputLabel
			value="#{pool.currentAnswer.pool.question}" />
		<hr>
		<h:outputLabel value="#{msg.answer}" />: <h:outputLabel
			value="#{pool.currentAnswer.answer}" />
		<br>
		<h:dataTable var="item" value="#{pool.currentAnswer.usersForAnswer}">
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.userId}" />
				</f:facet>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.username}" />
				</f:facet>
				<h:outputText value="#{item.username}" />
			</h:column>
		</h:dataTable>
	</h:form>

</f:view>
</div></div>
<c:import url="/common/footer.jspf" />