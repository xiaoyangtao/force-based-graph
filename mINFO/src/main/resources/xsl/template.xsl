<?xml version="1.0" encoding='utf-8'?>
<xsl:stylesheet version = '2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>

<xsl:template match="/">
<screen title='Screen loader'>
<container style='layout:inlinelayout(false,fill); align: center'>
<text><xsl:value-of select='/pool/question'/></text>
<xsl:choose>
	<xsl:when test='/pool/question/@type="single"'>
		<radioGroup onChange="save, this">
		<xsl:attribute name="id"><xsl:value-of select='/pool/@id'/></xsl:attribute>
		<xsl:for-each select='//answer'>
		<radioButton>
		<xsl:attribute name="value"><xsl:value-of select='@value'/></xsl:attribute>
		<xsl:value-of select='.'/>
		</radioButton>
		</xsl:for-each>
		</radioGroup>
	</xsl:when>
	<xsl:when test="/pool/question/@type='multi'">
		<xsl:for-each select='//answer'>
		<checkbox>
		<xsl:attribute name="id"><xsl:value-of select='/pool/@id'/>@<xsl:value-of select='@value'/></xsl:attribute>
		<xsl:value-of select='.'/>
		</checkbox>
		</xsl:for-each>
	</xsl:when>
</xsl:choose>
<button onAction="submit">Send answer</button>
</container>
<screenFirstMenu onAction="closeScreen">Exit</screenFirstMenu>
</screen> 
</xsl:template>
	 

</xsl:stylesheet>