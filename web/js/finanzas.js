var z = new dhtmlXCombo("comboServicios", "comboServicios", 200);
z.attachEvent("onChange", onServicioChange);
z.enableOptionAutoWidth(1);
var c = new dhtmlXCombo("comboClientes", "comboClientes", 200);
c.attachEvent("onChange", onClienteChange);
c.enableOptionAutoWidth(1);
var u = new dhtmlXCombo("comboUsuarios", "comboUsuarios", 200);
u.attachEvent("onChange", onUsuarioChange);
u.enableOptionAutoWidth(1);

/*Seccion Servicios*/

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
        url = "ServicioCtrl?op=obtenerServicio&id=" + this.getSelectedValue();
        ai = new AJAXInteraction(url, cargarServicio, "XML");
        ai.doGet();
    } else {

    }
}

function cargarServicio(result) {

}

var Script = function() {
    obtenerUsuario();
    obtenerServicios();
    obtenerUsuarios();
    obtenerClientes();
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
    consultarIngresos();
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

function consultarIngresos() {
    var tipoServicio = document.getElementById("comboTipoServicio");
    var tipoSeleccionado = tipoServicio.options[tipoServicio.selectedIndex].value;
    var fechaInicio = document.getElementById('fechaInicio');
    var fechaFinal = document.getElementById('fechaFinal');
    var estado = document.getElementById("comboEstado");
    var cancelado = estado.options[estado.selectedIndex].value;
    url = "FinanzasCtrl?op=consultarIngresos&usuario=" + u.getSelectedValue() + "&idcliente=" + c.getSelectedValue()
            + "&fechaInicio=" + fechaInicio.value + "&fechaFinal=" + fechaFinal.value + "&cancelado=" + cancelado
            + "&tipo=" + tipoSeleccionado + "&descripcion=" + z.getSelectedText();
    ai = new AJAXInteraction(url, cargarIngresos, "XML");
    ai.doGet();
}

function cargarIngresos(result) {
    var cantRegistros = result.getElementsByTagName('cantRegistros')[0].firstChild.nodeValue;
    var subtotalRegistros = result.getElementsByTagName('subtotalRegistros')[0].firstChild.nodeValue;
    var ivaRegistros = result.getElementsByTagName('ivaRegistros')[0].firstChild.nodeValue;
    var totalRegistros = result.getElementsByTagName('totalRegistros')[0].firstChild.nodeValue;

    document.getElementById("cantRegistros").value = cantRegistros;
    document.getElementById("subtotalRegistros").value = subtotalRegistros;
    document.getElementById("ivaRegistros").value = ivaRegistros;
    document.getElementById("totalRegistros").value = totalRegistros;

    var xmlresult = result.getElementsByTagName('registro');
    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var tramite = xmlresult[i];
        html += "<tr id='" + tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row'>";
        html += "<td class='hidden-phone'>";
        html += tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('benefnombre')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + tramite.getElementsByTagName('descripcion')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + tramite.getElementsByTagName('tipo')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + tramite.getElementsByTagName('precio')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('iva')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('total')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('cancelado')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('fechacreacion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('usuario')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('identificacion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('razonsocial')[0].firstChild.nodeValue + "</td>";
    }
    document.getElementById("ingresos-documentos").tBodies[0].innerHTML = html;
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

function onClienteChange() {
    consultarIngresos();
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
    url = "TramiteCtrl?op=abonarTramite";
    url = url + "&idtramite=" + idtramite + "&valor=" + abono;
    ai = new AJAXInteraction(url, buscarTramites, "Text");
    ai.doGet();
}

function consultarDocumentos(idtramite) {
    url = "TramiteCtrl?op=consultarDocumentos";
    url = url + "&idtramite=" + idtramite;
    ai = new AJAXInteraction(url, presentarDocumentos, "XML");
    ai.doGet();
    $('#documentosTramite').modal('toggle');
    buscarTramites();
}

function presentarDocumentos(result) {
    myTable.fnClearTable();
    var xmlresult = result.getElementsByTagName('documento');
    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var tramite = xmlresult[i];
        html += "<tr id='" + tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue + "-row'>";
        html += "<td>";
        html += tramite.getElementsByTagName('iddocumento')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>";
        html += tramite.getElementsByTagName('idtramite')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('benefnombre')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + tramite.getElementsByTagName('descripcion')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + tramite.getElementsByTagName('precio')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
    }
    document.getElementById("documentos-tramite").tBodies[0].innerHTML = html;
}
