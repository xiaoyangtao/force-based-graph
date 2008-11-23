<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>



<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />
	<h:form id="mainForm">
	<div id="mainarea">
	<div id="contentarea">


		<h:messages showDetail="true" showSummary="false" />
		<br>
		<br>
		
		<h:dataTable var="item" value="#{pool.poolList}">
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
					<f:param name="poolId" value="#{item.id}" />
					<h:outputText value="#{msg.edit}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<h:commandLink action="#{pool.deletePool}">
					<f:param name="poolId" value="#{item.id}" />
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
		</h:dataTable></div>

	<div id="sidebar">
	<div class="sidebarheader"><h:outputLabel
		value="#{msg.poolCount}" />: <h:outputLabel value="#{pool.poolCount}" /></div>

	<div class="sidebarheader"><h:commandLink
		action="#{pool.feedNews}">
		<h:outputText value="#{msg.feedNews}" />
	</h:commandLink></div>
	
	<div class="sidebarheader"><h:commandLink action="#{pool.newPool}">
			<h:outputText value="#{msg.addNewPool}" />
		</h:commandLink></div>
	
	
		
	</div>

	</div>
	</h:form>
</f:view>
<c:import url="/common/footer.jspf" />
