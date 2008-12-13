<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>


<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />
	<h:form id="mainForm">
		<div id="mainarea">
		<div id="contentarea"> <h:messages
			showDetail="true" showSummary="false" /> <br>
		<br>
		 <h:dataTable var="item" value="#{user.userList}">
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
			<h:column>
				<h:commandLink action="#{user.editUser}">
					<f:param name="userId" value="#{item.id}" />
					<h:outputText value="#{msg.edit}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<h:commandLink action="#{user.deleteUser}">
					<f:param name="userId" value="#{item.id}" />
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<h:commandLink action="#{user.displayInfo}">
					<f:param name="userId" value="#{item.id}" />
					<h:outputText value="#{msg.displayInfo}" />
				</h:commandLink>
			</h:column>
		</h:dataTable></div>

		<div id="sidebar">
		<div class="sidebarheader"><h:outputLabel value="#{msg.userCount}" />:
		<h:outputLabel value="#{user.userCount}" /></div>

		<div class="sidebarheader"><h:commandLink action="#{user.newUser}">
			<h:outputText value="#{msg.addNewUser}" />
		</h:commandLink></div>

		</div>


		</div>
	</h:form>

</f:view>

<c:import url="/common/footer.jspf" />