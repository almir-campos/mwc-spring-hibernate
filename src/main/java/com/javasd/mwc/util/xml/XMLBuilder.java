/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.xml;

/**
 *
 * @author almir
 */
public class XMLBuilder {

    public static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private final StringBuilder sb;
    private final String rootNode;

    public XMLBuilder(String rootNode, boolean showHeader) {
        this.sb = new StringBuilder();
        this.rootNode = rootNode;
        if ( showHeader )
        {
            sb.append(HEADER);
        }
        if (rootNode != null && rootNode.length() != 0) {
            sb.append("<").append(rootNode).append(">");
        }
    }

    public void addNode(String nName, String nValue) {
        sb.append("<").append(nName).append(">").append(nValue).append("</").append(nName).append(">");
    }

    public String getXML() {
        if (rootNode != null && rootNode.length() != 0) {
            sb.append("</").append(rootNode).append(">");
        }
        return sb.toString();
    }
    
    public String getXMLBlockFromIntArray( int[] arr, String root, String node )
    {
//        StringBuilder sbarr = new StringBuilder();
        sb.append( "<").append( root ).append( ">" );
        for ( int i : arr )
        {
            sb.append( "<").append( node ).append( ">" ).append( i ).append( "</").append( node ).append( ">" );
        }
        sb.append( "</").append( root ).append( ">" );
        return sb.toString();
    }
    
    public String getXMLBlockFromDoubleArray( double[] arr, String root, String node )
    {
        sb.append( "<").append( root ).append( ">" );
        for ( double d : arr )
        {
            sb.append( "<").append( node ).append( ">" ).append( d ).append( "</").append( node ).append( ">" );
        }
        sb.append( "</").append( root ).append( ">" );
        return sb.toString();
    }
    
    public static String getTag( String nodeName, boolean open )
    {
        if ( open )
        {
            return "<" + nodeName + ">";
        }
        else
        {
            return "</" + nodeName + ">";
        }
    }
}
