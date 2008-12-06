<?xml version="1.0" encoding='utf-8'?>
<xsl:stylesheet version = '2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
<xsl:output method="xml" encoding="UTF-8"/>

<xsl:template match="/">
<screen title='Screen loader'>
<container style='layout: borderlayout ; align: center'>
<textarea><xsl:value-of select='/pool/question'/></textarea>
<xsl:choose>
	<xsl:when test='/pool/question/@type="adsad"'>
		<radioGroup onChange="save, this">
		<xsl:attribute name="id"><xsl:value-of select='/pool/@id'/></xsl:attribute>
		<xsl:for-each select='//answer'>
		<radioButton>
		<xsl:attribute name="value"><xsl:value-of select='@value'/></xsl:attribute>
		<xsl:value-of select='.'/>
		</radioButton>
		</xsl:for-each>
		</radioGroup>
                      <button onAction="submit">Send answer</button>
	</xsl:when>
	<xsl:when test="/pool/question/@type='multi'">
		<xsl:for-each select='//answer'>
		<checkbox>
		<xsl:attribute name="id"><xsl:value-of select='/pool/@id'/>@<xsl:value-of select='@value'/></xsl:attribute>
		<xsl:value-of select='.'/>
		</checkbox>
		</xsl:for-each>
                      <button onAction="submit">Send answer</button>
	</xsl:when>
</xsl:choose>
</container>
<screenFirstMenu onAction="mainMenu">Exit</screenFirstMenu>
<xsl:choose>
	<xsl:when test='/pool/question/@type="single"'>
    <screenSecondMenu>
        mark
        <menuPopup>
           	<xsl:for-each select='//answer'>
                             <menuItem>
                                <xsl:attribute name="onAction">saveMenu,this,<xsl:value-of select='/pool/@id'/>,<xsl:value-of select='@value'/></xsl:attribute>
		<xsl:value-of select='.'/>
                             </menuItem>
               </xsl:for-each>
        </menuPopup>
    </screenSecondMenu>
		
	</xsl:when>
</xsl:choose>
</screen> 
</xsl:template>
	 

</xsl:stylesheet>