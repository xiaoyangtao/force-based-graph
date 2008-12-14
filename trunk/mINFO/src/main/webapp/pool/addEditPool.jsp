<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>

<f:view locale="#{facesContext.externalContext.request.locale}">
	<f:loadBundle var="msg" basename="message" />
	<div id="mainarea">
	<h:form id="mainForm">
	<div id="contentarea">

		<h:outputLabel value="#{msg.poolId}" />: <h:outputText
			value="#{pool.currentPool.id}" />
		<Br>
		<h:outputLabel value="#{msg.poolQuestion}" />: <h:inputTextarea
			value="#{pool.currentPool.question}" style="width:500;height:300">
			<f:validateLength minimum="1" />
		</h:inputTextarea>
		<br>
		<Br>

		<h:messages showDetail="true" showSummary="false" />
		<h:outputText value="#{msg.availableAnswers}"></h:outputText>:<br>

		<h:dataTable var="item" value="#{pool.currentPool.answers}">
			<h:column>
				<h:outputText value="#{item.id}" />
			</h:column>
			<h:column>
				<h:inputText value="#{item.answer}" />
			</h:column>

			<h:column>
				<h:commandLink action="#{pool.deleteAnswer}">
					<f:param name="answerId" value="#{item.id}" />
					<h:outputText value="#{msg.delete}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<h:commandLink action="#{pool.showUsersForAnswer}">
					<f:param name="answerId" value="#{item.id}" />
					<h:outputText value="#{msg.showUsersForAnswer}" />
				</h:commandLink>
			</h:column>
		</h:dataTable>

		<c:choose>
			<c:when test="#{msg.poolId == null}">
				<h:commandButton value="#{msg.addNewPool}"
					action="#{pool.performPool}" />
			</c:when>
			<c:otherwise>
				<h:commandButton value="#{msg.save}" action="#{pool.performPool}" />
			</c:otherwise>
		</c:choose>

		<h:commandButton value="#{msg.addNewAnswer}"
			action="#{pool.addAnswer}" />

	
	</div>
	
	<div id="sidebar">
	<div class="sidebarheader"><h:outputLabel value="#{msg.tags}" />
	</div>
	<div class="sidebarheader">
		<h:dataTable var="item" value="#{pool.selectedTags}">
			<h:column>
				<h:selectBooleanCheckbox value="#{item.selected}"></h:selectBooleanCheckbox>
			</h:column>
			<h:column>
				<h:outputText value="#{item.tag.name}" />
			</h:column>
		</h:dataTable>
	</div>
	</h:form>
	</div>
	</div>

</f:view>
<c:import url="/common/footer.jspf" />