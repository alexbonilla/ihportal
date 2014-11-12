var z = new dhtmlXCombo("comboServicios", "comboServicios", 200);
z.attachEvent("onChange", onServicioChange);
z.enableOptionAutoWidth(1);

var myTable = $('#documentos-table').dataTable({
    "bDestroy": true,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'asc']],
    "aoColumns": [
        /*info*/null,
        /*cliente*/null,
        /*documento*/null,
        /*descripcion*/null,
        /*fecha*/null,
        /*usuario*/null,
        /*numfactura*/null
    ]
});

var Script = function() {
    obtenerUsuario();
    obtenerServicios();
}();

function obtenerServicios() {
    var tipoServicio = document.getElementById("comboTipoServicio");
    var tipoSeleccionado = tipoServicio.options[tipoServicio.selectedIndex].value;
    url = "ServicioCtrl?op=obtenerServicios&tipo=" + tipoSeleccionado;
    ai = new AJAXInteraction(url, cargarServicios, "Text");
    ai.doGet();
}

function cargarServicios(resultado) {
    var jsonObj = $.parseJSON(resultado);
    z.clearAll();
    z.addOption(jsonObj);
    z.enableFilteringMode(true);
    z.setComboText('');
}

function onServicioChange() {
    if (this.getSelectedValue() !== null) {
        //Buscar seleccionado
    } else {
        //Borrar busqueda
    }
}

function obtenerUsuario() {
    var url = "CuentaCtrl?op=usuario";
    var ai = new AJAXInteraction(url, cargarUsuario, "Text");
    ai.doGet();
}

function cargarUsuario(resultado) {
    document.getElementsByClassName("username")[0].innerHTML = resultado;
}

function buscarDocumentos() {
    var fechaInicio = document.getElementById('fechaInicio');
    var fechaFinal = document.getElementById('fechaFinal');

    url = "TramiteCtrl?op=buscarDocumentos&descripcion=" + z.getSelectedValue()
            + "&fechaInicio=" + fechaInicio.value + "&fechaFinal=" + fechaFinal.value;
    ai = new AJAXInteraction(url, cargarDocumentos, "XML");
    ai.doGet();
    //cliente facturado, num de documento, descripcion, fecha

}

function cargarDocumentos(result) {

    var xmlresult = result.getElementsByTagName('documento');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var tramite = xmlresult[i];
        html += "<tr id='" + tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row'>";
        html += "<td></td>";
        html += "<td title='Cliente' class='hidden-phone'>" + tramite.getElementsByTagName('clientenombre')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Documento' class='hidden-phone'>" + tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Descripcion'>" + tramite.getElementsByTagName('descripcion')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Fecha'>" + tramite.getElementsByTagName('fecha')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Usuario'>" + tramite.getElementsByTagName('usuario')[0].firstChild.nodeValue + "</td>";
        html += "<td title='NumFactura'>" + tramite.getElementsByTagName('numfactura')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
    }
    document.getElementById("documentos-table").tBodies[0].innerHTML = html;

    html = "";
}

function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}