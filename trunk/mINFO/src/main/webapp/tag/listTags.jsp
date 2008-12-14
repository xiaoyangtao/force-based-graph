<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>



<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />

	<div id="mainarea">
	<div id="contentarea"><h:messages showDetail="true"
		showSummary="false" /> <br>
	<br>
	<h:form id="mainForm">
		<h:dataTable var="item" value="#{tag.tagList}">
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.tagId}" />
				</f:facet>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.tagName}" />
				</f:facet>
				<h:inputText value="#{item.name}" />
			</h:column>
			<h:column>
				<h:commandLink action="#{tag.deleteTag}">
					<f:param name="tagId" value="#{item.id}" />
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
		</h:dataTable>
		<h:commandLink action="#{tag.saveTags}">
			<h:outputText value="#{msg.save}" />
		</h:commandLink>
	</h:form></div>

	<div id="sidebar">
	<div class="sidebarheader"><h:outputLabel value="#{msg.tagCount}" />:
	<h:outputLabel value="#{tag.tagCount}" /></div>

	<div class="sidebarheader"><h:form id="addForm">
		<h:inputText value="#{tag.newTagName}" />
		<br>
		<h:commandLink action="#{tag.newTag}">
			<h:outputText value="#{msg.addNewTag}" />
		</h:commandLink>
	</h:form></div>

	</div>

	</div>

</f:view>
<c:import url="/common/footer.jspf" />
