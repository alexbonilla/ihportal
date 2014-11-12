var c = new dhtmlXCombo("comboClientes", "comboClientes", 200);
c.attachEvent("onChange", onClienteChange);
c.enableOptionAutoWidth(1);
var n = new dhtmlXCombo("comboNombreClientes", "comboNombreClientes", 200);
n.attachEvent("onChange", onClienteChange);
n.enableOptionAutoWidth(1);
var u = new dhtmlXCombo("comboUsuarios", "comboUsuarios", 200);
u.attachEvent("onChange", onUsuarioChange);
u.enableOptionAutoWidth(1);
/*
 * Initialse DataTables, with no sorting on the 'details' column
 */
var myTable = $('#tramites-table').dataTable({
    "bDestroy": true,
    "bPaginate": false,
    "bFilter": false,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'desc']],
    "aoColumns": [
        /*info*/null,
        /*# Trámite*/null,
        /*Fecha*/null,
        /*Usuario*/null,
        /*Cliente*/null,
        /*Cant. Documentos*/null,
        /*SubTotal-Retencion*/null,
        /*IVA*/null,
        /*Total*/null,
        /*Adicionales*/null,
        /*Abonos*/null,
        /*Pendiente*/null,
        /*Cancelado*/null,
        /*Facturado*/null

    ]
});

//var flagTablaInicial = 0;

var Script = function() {
    obtenerUsuario();
    obtenerUsuarios();
    obtenerClientes();
    obtenerNombresClientes();
    document.getElementById('fechaInicio').value = '';
    document.getElementById('fechaFinal').value = '';
    buscarTramitesHoy();
    buscarGastos();
}();

/*Seccion Usuarios*/

function obtenerUsuarios() {
    url = "CuentaCtrl?op=consultaSimple";
    ai = new AJAXInteraction(url, cargarUsuarios, "Text");
    ai.doGet();
}

function cargarUsuarios(resultado) {
    var jsonObj = $.parseJSON(resultado);
    u.clearAll();
    u.addOption(jsonObj);
    u.enableFilteringMode(true);
    u.setComboText('');
}

function onUsuarioChange() {
    buscarTramites();
}

function obtenerUsuario() {
    var url = "CuentaCtrl?op=usuario";
    var ai = new AJAXInteraction(url, cargarUsuario, "Text");
    ai.doGet();
}

function cargarUsuario(resultado) {
    document.getElementsByClassName("username")[0].innerHTML = resultado;
}

/*Seccion Tramite*/

function buscarTramites() {
    var fechaInicio = document.getElementById('fechaInicio');
    var fechaFinal = document.getElementById('fechaFinal');
    var estado = document.getElementById("comboEstado");
    var cancelado = estado.options[estado.selectedIndex].value;
    var idcliente = c.getSelectedValue();
    if(idcliente===null || idcliente===""){
       idcliente = n.getSelectedValue();
    }
    url = "TramiteCtrl?op=buscarTramites&usuario=" + u.getSelectedValue() + "&idcliente=" + idcliente
            + "&fechaInicio=" + fechaInicio.value + "&fechaFinal=" + fechaFinal.value + "&cancelado=" + cancelado;
    ai = new AJAXInteraction(url, cargarTramites, "XML");
    ai.doGet();

}

function buscarTramitesHoy() {
    url = "TramiteCtrl?op=buscarTramitesHoy";
    ai = new AJAXInteraction(url, cargarTramites, "XML");
    ai.doGet();

}

function cargarTramites(result) {
    myTable.fnClearTable();

    var xmlresult = result.getElementsByTagName('tramite');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var tramite = xmlresult[i];
        html += "<tr id='" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "-row'>";

        html += "<td title='No. de Trámite'>";
        html += tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone' title='Fecha de Creación'>" + tramite.getElementsByTagName('fechacreacion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Usuario'>" + tramite.getElementsByTagName('usuario')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Cliente'>" + tramite.getElementsByTagName('nombrecliente')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Documentos'>";
        html += tramite.getElementsByTagName('docs')[0].firstChild.nodeValue;
        html += "<input id='idtramite-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' name='idtramite-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' type='hidden' value='" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='numfactura-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' name='numfactura-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' type='hidden' value='" + tramite.getElementsByTagName('numfactura')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='creditocliente-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' name='creditocliente-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' type='hidden' value='" + tramite.getElementsByTagName('credito')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='facturado-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' name='facturado-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' type='hidden' value='" + tramite.getElementsByTagName('facturado')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='pendiente-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' name='pendiente-" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "' type='hidden' value='" + tramite.getElementsByTagName('pendiente')[0].firstChild.nodeValue + "'></input>";
        html += "</td>";
        html += "<td class='hidden-phone' title='Subtotal'>" + tramite.getElementsByTagName('subtotal')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='IVA'>" + tramite.getElementsByTagName('iva')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Total'>" + tramite.getElementsByTagName('total')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Adicionales'>" + tramite.getElementsByTagName('totaladicional')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Abonos'>" + tramite.getElementsByTagName('abonos')[0].firstChild.nodeValue + "</td>";
        html += "<td title='Saldo Pendiente (Total+Adicionales-Abonos'>" + tramite.getElementsByTagName('pendiente')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Cancelado'>" + tramite.getElementsByTagName('cancelado')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone' title='Facturado'>" + tramite.getElementsByTagName('facturado')[0].firstChild.nodeValue + "</td>";

        html += "</tr>";

        document.getElementById("tramites-table").tBodies[0].innerHTML = html;

        agregarDetalles(myTable, tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "-row");

        html = "";
    }

    var tramites = result.getElementsByTagName('tramites')[0];
    var subtotalTramites = tramites.getElementsByTagName('subtotalTramites')[0].firstChild.nodeValue;
    var ivaTramites = tramites.getElementsByTagName('ivaTramites')[0].firstChild.nodeValue;
    var totalTramites = tramites.getElementsByTagName('totalTramites')[0].firstChild.nodeValue;
    var totaladicionalTramites = tramites.getElementsByTagName('totaladicionalTramites')[0].firstChild.nodeValue;
    var abonosTramites = tramites.getElementsByTagName('abonosTramites')[0].firstChild.nodeValue;

    document.getElementById("subtotalTramites").innerHTML = subtotalTramites;
    document.getElementById("ivaTramites").innerHTML = ivaTramites;
    document.getElementById("totalTramites").innerHTML = totalTramites;
    document.getElementById("totaladicionalTramites").innerHTML = totaladicionalTramites;
    document.getElementById("abonosTramites").innerHTML = abonosTramites;


    /*Esta sección actualiza los totales en la parte superior de la página */
    var tramites = result.getElementsByTagName('tramites')[0];
    var subtotalTramites = tramites.getElementsByTagName('subtotalTramites')[0].firstChild.nodeValue;
    var ivaTramites = tramites.getElementsByTagName('ivaTramites')[0].firstChild.nodeValue;
    var totalTramites = tramites.getElementsByTagName('totalTramites')[0].firstChild.nodeValue;
    var totaladicionalTramites = tramites.getElementsByTagName('totaladicionalTramites')[0].firstChild.nodeValue;
    var abonosTramites = tramites.getElementsByTagName('abonosTramites')[0].firstChild.nodeValue;
    $display = $('.count');
    $display.text(subtotalTramites);
    $display = $('.count3');
    $display.text(abonosTramites);
    $display = $('.count2');
    $display.text(totaladicionalTramites);

}

function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}


/*Seccion Clientes*/
function obtenerClientes() {
    url = "ClienteCtrl?op=obtenerClientes";
    ai = new AJAXInteraction(url, cargarClientes, "Text");
    ai.doGet();
}

function cargarClientes(resultado) {
    var jsonObj = $.parseJSON(resultado);
    c.clearAll();
    c.addOption(jsonObj);
    c.enableFilteringMode(true);
}

function obtenerNombresClientes() {
    url = "ClienteCtrl?op=obtenerNombresClientes";
    ai = new AJAXInteraction(url, cargarNombresClientes, "Text");
    ai.doGet();
}

function cargarNombresClientes(resultado) {
    var jsonObj = $.parseJSON(resultado);
    n.clearAll();
    n.addOption(jsonObj);
    n.enableFilteringMode(true);
}

function onClienteChange() {
    buscarTramites();
}

function cargarCliente(result) {
    var xmlresult = result.getElementsByTagName('cliente');
    var nombreCliente = document.getElementById('nombreCliente');
    for (i = 0; i < xmlresult.length; i++) {
        var cliente = xmlresult[i];
        nombreCliente.value = cliente.getElementsByTagName('razonsocial')[0].firstChild.nodeValue;
    }
}

function guardarNuevoCliente() {
    var identificacion = document.getElementById("nuevoClienteIdentificacion").value;
    var tipoidentificacion = document.getElementById("nuevoClienteTipoIdentificacion").value;
    var razonsocial = document.getElementById("nuevoClienteRazonSocial").value;
    var direccion = document.getElementById("nuevoClienteDireccion").value;
    var telefonofijo = document.getElementById("nuevoClienteTelefonoFijo").value;
    var telefonomovil = document.getElementById("nuevoClienteTelefonoMovil").value;
    var email = document.getElementById("nuevoClienteEmail").value;
    url = "ClienteCtrl?op=guardarNuevoCliente&identificacion=" + identificacion + "&tipoidentificacion=" + tipoidentificacion + "&razonsocial=" + razonsocial +
            "&direccion=" + direccion + "&telefonofijo=" + telefonofijo + "&telefonomovil=" + telefonomovil + "&email=" + email;
    ai = new AJAXInteraction(url, cargarClientes, "Text");
    ai.doGet();
    $('#nuevoCliente').modal('toggle');
}

/*Seccion Enviar*/
function crearTramite() {
    var clienteIdentificacion = c.getSelectedText();
    var abono = document.getElementById("abonoTotal").value;
    var data = myTable.$('input, select').serialize();
    url = "TramiteCtrl?op=guardarNuevoTramite";
    url = url + "&clienteIdentificacion=" + clienteIdentificacion + "&abono=" + abono + "&" + data;
    ai = new AJAXInteraction(url, respuestaTramite, "Text");
    ai.doGet();
}

function respuestaTramite(result) {
    if (result !== 0)
        alert("Se creó trámite no. " + result);
    else
        alert("Problemas en la creación de trámite");
    location.reload();
}

function abonarTramite(idtramite) {
    var abono = document.getElementById(idtramite + '-total-abonar').value;
    var pendiente = document.getElementById('pendiente-' + idtramite).value;
    if (abono < 0) {
        alert('El abono no puede ser negativo');
    } else if (abono <= pendiente) {
        url = "TramiteCtrl?op=abonarTramite";
        url = url + "&idtramite=" + idtramite + "&valor=" + abono;
        ai = new AJAXInteraction(url, buscarTramites, "Text");
        ai.doGet();
    } else {
        alert('El abono no puede ser mayor al saldo pendiente ' + pendiente);
    }

}

function guardarRetencionRecibida(idtramite) {
    var retencion = document.getElementById(idtramite + '-retencion').value;
    var pendiente = document.getElementById('pendiente-' + idtramite).value;
    if (retencion < 0) {
        alert('La retención no puede ser negativa');
    } else if (retencion < pendiente) {
        url = "TramiteCtrl?op=guardarRetencionRecibida";
        url = url + "&idtramite=" + idtramite + "&valor=" + retencion;
        ai = new AJAXInteraction(url, buscarTramites, "Text");
        ai.doGet();
    } else {
        alert('La retención $' + retencion + 'no puede ser mayor al saldo pendiente $' + pendiente);
    }
}

function consultarDocumentos(idtramite) {
    url = "TramiteCtrl?op=consultarDocumentos";
    url = url + "&idtramite=" + idtramite;
    ai = new AJAXInteraction(url, presentarDocumentos, "XML");
    ai.doGet();
}

function presentarDocumentos(result) {

    var xmlresult = result.getElementsByTagName('documento');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var documento = xmlresult[i];
        html += "<tr id='" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row'>";

        html += "<td>";
        html += documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>";
        html += documento.getElementsByTagName('idtramite')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + documento.getElementsByTagName('benefnombre')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + documento.getElementsByTagName('descripcion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + documento.getElementsByTagName('precio')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + documento.getElementsByTagName('adicional')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'><button type='button' class='btn btn-shadow btn-danger' onclick='borrarDocumento(\"" + documento.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "\",\"" + documento.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "\")'>X</button></td>";
        html += "</tr>";
    }
    document.getElementById("documentos-tramite-" + documento.getElementsByTagName('idtramite')[0].firstChild.nodeValue).tBodies[0].innerHTML = html;
}

function borrarDocumento(idtramite, iddocumento) {
    var facturado = document.getElementById('facturado-' + idtramite).value;
    if (facturado === 'Si') {
        alert('No se puede quitar documento de trámite facturado');
    } else {
        url = "TramiteCtrl?op=borrarDocumento";
        url = url + "&idtramite=" + idtramite + "&iddocumento=" + iddocumento;
        ai = new AJAXInteraction(url, presentarDocumentos, "XML");
        ai.doGet();
    }
}

function consultarAbonos(idtramite) {
    url = "TramiteCtrl?op=consultarAbonos";
    url = url + "&idtramite=" + idtramite;
    ai = new AJAXInteraction(url, presentarAbonos, "XML");
    ai.doGet();
}

function presentarAbonos(result) {

    var xmlresult = result.getElementsByTagName('abono');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var abono = xmlresult[i];
        html += "<tr id='" + abono.getElementsByTagName('idabono')[0].firstChild.nodeValue + "-row'>";

        html += "<td class='hidden-phone'>";
        html += abono.getElementsByTagName('idtramite')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td>";
        html += abono.getElementsByTagName('fechaabono')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td>" + abono.getElementsByTagName('valor')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
    }
    document.getElementById("abonos-tramite-" + abono.getElementsByTagName('idtramite')[0].firstChild.nodeValue).tBodies[0].innerHTML = html;
}

function facturarTramite(idtramite) {
    var numfactura = document.getElementById(idtramite + '-numfactura').value;
    if (numfactura !== null && numfactura !== '') {
        url = "TramiteCtrl?op=facturarTramite";
        url = url + "&idtramite=" + idtramite + "&numfactura=" + numfactura;
        window.open(url, '_blank', null);
        location.reload();
    } else {
        alert('Por favor ingrese el número de factura');
    }
}
function cambiarNumFacturaTramite(idtramite) {
    var numfactura = document.getElementById(idtramite + '-nuevo-numfactura').value;
    alert("Se cambió el no. de factura a " + numfactura);
    if (numfactura !== null && numfactura !== '') {
        url = "TramiteCtrl?op=cambiarNumFacturaTramite";
        url = url + "&idtramite=" + idtramite + "&numfactura=" + numfactura;
        ai = new AJAXInteraction(url, buscarTramites, "Text");
        ai.doGet();
    } else {
        alert('Por favor ingrese el número de factura');
    }
}

function anularTramite(idtramite) {
    url = "TramiteCtrl?op=anularTramite";
    url = url + "&idtramite=" + idtramite;
    ai = new AJAXInteraction(url, buscarTramitesHoy(), "XML");
    ai.doGet();
    alert("Se anuló el trámite no. " + idtramite);
    location.reload();
}

function presentarFactura(result) {

    var xmlresult = result.getElementsByTagName('factura');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var abono = xmlresult[i];
        html += "<tr id='" + abono.getElementsByTagName('idabono')[0].firstChild.nodeValue + "-row'>";

        html += "<td class='hidden-phone'>";
        html += abono.getElementsByTagName('idtramite')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td>";
        html += abono.getElementsByTagName('fechaabono')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td>" + abono.getElementsByTagName('valor')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
    }
    document.getElementById("abonos-tramite-" + abono.getElementsByTagName('idtramite')[0].firstChild.nodeValue).tBodies[0].innerHTML = html;
}

function buscarGastos() {
    url = "GastoCtrl?op=buscarGastosHoy";
    ai = new AJAXInteraction(url, cargarGastos, "XML");
    ai.doGet();

}

function cargarGastos(result) {
    var gastos = result.getElementsByTagName('gastos')[0];
    var totalGastos = gastos.getElementsByTagName('totalGastos')[0].firstChild.nodeValue;
    $display = $('.count4');
    $display.text(totalGastos);
}