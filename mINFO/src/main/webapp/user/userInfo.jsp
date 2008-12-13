<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>


<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />

	<h:form id="mainForm">
		<div id="mainarea">
		<div id="contentarea"><h:commandLink action="userList">
			<h:outputText value="#{msg.userList}" />
		</h:commandLink><br>
		<br>

		<h:outputLabel value="#{msg.userId}" />: <h:outputText
			value="#{user.currentUser.id}" /> <Br>
		<h:outputLabel value="#{msg.username}" />: <h:outputText
			value="#{user.currentUser.username}"/>
			<Br>
		<Br>
		<h:outputText value="#{msg.givenAnswers}" />
		
		<h:dataTable var="item" value="#{user.currentUser.userAnswers}">
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.answerId}" />
				</f:facet>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.poolQuestion}" />
				</f:facet>
				<h:outputText value="#{item.pool.question}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.answer}" />
				</f:facet>
				<h:outputText value="#{item.answer}" />
			</h:column>
		</h:dataTable>
		
		<h:messages showDetail="true" showSummary="false" /> 
			</div>
		</div>
	</h:form>

</f:view>

<c:import url="/common/footer.jspf" />