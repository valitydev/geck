<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="root/payment/status/failed/failure">
        <err><xsl:apply-templates select="@*|node()"/></err>
    </xsl:template>
    <xsl:template match="root/payment/cost/currency">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
            <name type="string">kek</name>
            <numeric_code type="long">1</numeric_code>
            <exponent type="long">1</exponent>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="root/payment/domain_revision"/>
</xsl:stylesheet>
