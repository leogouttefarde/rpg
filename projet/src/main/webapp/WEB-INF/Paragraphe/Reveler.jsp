<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:wrapper>
    <jsp:attribute name="header">
        <h1>Etes Vous Sur De Vouloir Réveler Ce Paragraphe ?</h1>
    </jsp:attribute>

    <jsp:body>
        ${parag.getTexte()} <br/>
        <form action="paragraphe" method="post">
           oui <input type="radio" name="res" value='oui'/><br/>
           non <input type="radio" name="res" value="non" checked/><br/>
           <button type="submit"> Ok</button>
           <input type="hidden" name="pID" value="${parag.getID()}"/>
           <input type="hidden" name="action" value="reveler"/>
        </form>
    </jsp:body>
</t:wrapper>