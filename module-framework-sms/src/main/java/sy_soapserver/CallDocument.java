/*
 * An XML document type.
 * Localname: __call
 * Namespace: urn:SY_SoapServer
 * Java type: sy_soapserver.CallDocument
 *
 * Automatically generated - do not modify.
 */
package sy_soapserver;


/**
 * A document containing one __call(@urn:SY_SoapServer) element.
 *
 * This is a complex type.
 */
public interface CallDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CallDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4262BC6C064011807FED7385999847C2").resolveHandle("call17f5doctype");
    
    /**
     * Gets the "__call" element
     */
    sy_soapserver.CallDocument.Call getCall();
    
    /**
     * Sets the "__call" element
     */
    void setCall(sy_soapserver.CallDocument.Call call);
    
    /**
     * Appends and returns a new empty "__call" element
     */
    sy_soapserver.CallDocument.Call addNewCall();
    
    /**
     * An XML __call(@urn:SY_SoapServer).
     *
     * This is a complex type.
     */
    public interface Call extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Call.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4262BC6C064011807FED7385999847C2").resolveHandle("call12dbelemtype");
        
        /**
         * Gets the "method" element
         */
        java.lang.String getMethod();
        
        /**
         * Gets (as xml) the "method" element
         */
        org.apache.xmlbeans.XmlString xgetMethod();
        
        /**
         * Tests for nil "method" element
         */
        boolean isNilMethod();
        
        /**
         * Sets the "method" element
         */
        void setMethod(java.lang.String method);
        
        /**
         * Sets (as xml) the "method" element
         */
        void xsetMethod(org.apache.xmlbeans.XmlString method);
        
        /**
         * Nils the "method" element
         */
        void setNilMethod();
        
        /**
         * Gets the "args" element
         */
        java.lang.String getArgs();
        
        /**
         * Gets (as xml) the "args" element
         */
        org.apache.xmlbeans.XmlString xgetArgs();
        
        /**
         * Tests for nil "args" element
         */
        boolean isNilArgs();
        
        /**
         * Sets the "args" element
         */
        void setArgs(java.lang.String args);
        
        /**
         * Sets (as xml) the "args" element
         */
        void xsetArgs(org.apache.xmlbeans.XmlString args);
        
        /**
         * Nils the "args" element
         */
        void setNilArgs();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static sy_soapserver.CallDocument.Call newInstance() {
              return (sy_soapserver.CallDocument.Call) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static sy_soapserver.CallDocument.Call newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (sy_soapserver.CallDocument.Call) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static sy_soapserver.CallDocument newInstance() {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static sy_soapserver.CallDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static sy_soapserver.CallDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static sy_soapserver.CallDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static sy_soapserver.CallDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static sy_soapserver.CallDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static sy_soapserver.CallDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static sy_soapserver.CallDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static sy_soapserver.CallDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static sy_soapserver.CallDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static sy_soapserver.CallDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static sy_soapserver.CallDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static sy_soapserver.CallDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static sy_soapserver.CallDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static sy_soapserver.CallDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static sy_soapserver.CallDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static sy_soapserver.CallDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static sy_soapserver.CallDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (sy_soapserver.CallDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
