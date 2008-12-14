<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jspf"%>
<%@ include file="/common/header.jspf"%>
<%@ include file="/common/menu.jspf"%>


<%@page import="com.minfo.cewolf.beans.UserStatsDatasetProducer"%>
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
			value="#{user.currentUser.username}" /> <Br>
		<Br>
		<h:outputText value="#{msg.givenAnswers}" /> <h:dataTable var="item"
			value="#{user.currentUser.userAnswers}">
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
				<h:commandLink action="#{pool.editPool}">
					<f:param name="poolId" value="#{item.pool.id}" />
					<h:outputText value="#{item.pool.questionShort}" />
				</h:commandLink>

			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.answer}" />
				</f:facet>
				<h:outputText value="#{item.answer}" />
			</h:column>
		</h:dataTable> <h:messages showDetail="true" showSummary="false" /></div>
		<div id="sidebar">


		<div class="sidebarheader"><%--h:dataTable var="item"
			value="#{user.userStats}">
			<h:column>
				<h:outputText value="#{item.tag}" />
			</h:column>
			<h:column>
				<h:outputText value="#{item.answer}" />
			</h:column>
			<h:column>
				<h:outputText value="#{item.percent}" />
			</h:column>
		</h:dataTable--%> <%--jsp:useBean id="pageViews" 
			class="com.minfo.cewolf.beans.UserStatsDatasetProducer" /--%> <cewolf:chart
			background="WEB-INF/img/white.png" id="line"
			title="Preferencje użytkownika" type="horizontalbar3d"
			xaxislabel="kategorie" yaxislabel="głosy">
			<cewolf:data>
				<cewolf:producer id="pageViews" />
			</cewolf:data>
			<cewolf:chartpostprocessor id="pageViews"/>
			
		</cewolf:chart> <cewolf:img chartid="line" renderer="/cewolf" width="270"
			height="400" style="background-color:white" /></div>
		</div>

		</div>
	</h:form>

</f:view>

<c:import url="/common/footer.jspf" />