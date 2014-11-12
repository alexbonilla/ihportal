var myTable = $('#documentos-anulados-table').dataTable({
    "bDestroy": true,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'asc']],
    "aoColumns": [
        /*+detalles*/null,
        /*documento*/null,
        /*fecha*/null,
        /*id benef*/null,
        /*beneficiario*/null
    ]
});


var Script = function() {
    obtenerUsuario();
    consultarDocumentosAnulados();  
}();

function obtenerUsuario() {
    var url = "CuentaCtrl?op=usuario";
    var ai = new AJAXInteraction(url, cargarUsuario, "Text");
    ai.doGet();
}

function cargarUsuario(resultado) {
    document.getElementsByClassName("username")[0].innerHTML = resultado;
}

function consultarDocumentosAnulados() {
    url = "TramiteCtrl?op=consultarDocumentosAnulados";
    ai = new AJAXInteraction(url, cargarDocumentosAnulados, "XML");
    ai.doGet();

}

function cargarDocumentosAnulados(result) {
    myTable.fnClearTable();

    var xmlresult = result.getElementsByTagName('documento');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var documento = xmlresult[i];
        html += "<tr id='" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row'>";

        html += "<td>";
        html += documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + documento.getElementsByTagName('fechacreacion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + documento.getElementsByTagName('benefid')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + documento.getElementsByTagName('benefnombre')[0].firstChild.nodeValue + "</td>";
        html += "<input id='iddocumento-" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' name='iddocumento-" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' type='hidden' value='" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='benefnombre-" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' name='benefnombre-" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' type='hidden' value='" + documento.getElementsByTagName('benefnombre')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='idservicio-" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' name='idservicio-" +documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "' type='hidden' value='" + documento.getElementsByTagName('idservicio')[0].firstChild.nodeValue + "'></input>";
        
        html += "</td>";        

        html += "</tr>";

        document.getElementById("documentos-anulados-table").tBodies[0].innerHTML = html;

        agregarDetalles(myTable, documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row");

        html = "";
    }
}

function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}