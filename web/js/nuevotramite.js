var z = new dhtmlXCombo("comboServicios", "comboServicios", 200);
z.attachEvent("onChange", onServicioChange);
z.enableOptionAutoWidth(1);

var c = new dhtmlXCombo("comboClientes", "comboClientes", 200);
c.attachEvent("onChange", onClienteChange);
c.enableOptionAutoWidth(1);

var numSerie = "null";

document.getElementById('precio').value = "0";
document.getElementById('cantidad').value = "1";
document.getElementById('total').value = "0";
document.getElementById('valorSubtotalTramite').value = "0";
document.getElementById('valorIvaTramite').value = "0";
document.getElementById('valorTotalTramite').value = "0";
document.getElementById('abono').value = "";
document.getElementById('credito').value = "";
document.getElementById('abonoTotal').value = "";
document.getElementById('nombreCliente').value = "";
document.getElementById('nuevoServicioDescripcion').value = "";
document.getElementById('nuevoServicioPrecio').value = "";

/*
 * Initialse DataTables, with no sorting on the 'details' column
 */
var myTable = $('#servicios_tramite').dataTable({
    "bPaginate": false,
    "bFilter": false,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'asc']],
    "aoColumns": [
        /*index*/ null,
        /* Subject Name */ null,
        /* Address */ null,
        /* LinkedWithCompany */ null,
        /* Work Tel */ null,
//                                                                    /* LinkedWithCompany */ {"bVisible": false}
        /* LinkedWithCompany */ null
    ]
});

var flagTablaInicial = 0;

var Script = function() {
    obtenerUsuario();
    obtenerServicios();
    obtenerClientes();
}();
function obtenerUsuario() {
    var url = "CuentaCtrl?op=usuario";
    var ai = new AJAXInteraction(url, cargarUsuario, "Text");
    ai.doGet();
}

function cargarUsuario(resultado) {
    document.getElementsByClassName("username")[0].innerHTML = resultado;
}

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
    z.addOption([[-1, "<a href='#myModal' class='btn btn-xs btn-success'>Nuevo servicio</a>"]]);
    z.enableFilteringMode(true);
    z.setComboText('');
    document.getElementById('precio').value = "0";
    document.getElementById('cantidad').value = "1";
    document.getElementById('total').value = "0";
}

function onServicioChange() {
    if (this.getSelectedValue() !== null) {
        if (this.getSelectedValue() !== -1) {
            url = "ServicioCtrl?op=obtenerServicio&id=" + this.getSelectedValue();
            ai = new AJAXInteraction(url, cargarServicio, "XML");
            ai.doGet();
        } else {
            $('#myModal').modal('toggle');
        }
    } else {
        var precio = document.getElementById('precio');
        var cantidad = document.getElementById('cantidad');
        var total = document.getElementById('total');
        total.value = 0;
        precio.value = 0;
        cantidad.value = 1;
    }
}

function cargarServicio(result) {
    var xmlresult = result.getElementsByTagName('servicio');
    var precio = document.getElementById('precio');
    var cantidad = document.getElementById('cantidad');
    var total = document.getElementById('total');
    for (i = 0; i < xmlresult.length; i++) {
        var serviceoffering = xmlresult[i];
        precio.value = serviceoffering.getElementsByTagName('precio')[0].firstChild.nodeValue;
        total.value = precio.value * cantidad.value;
        total.value = roundNumber(total.value, 2);
    }
}

function actualizarTotal() {
    var precio = document.getElementById('precio');
    var cantidad = document.getElementById('cantidad');
    var total = document.getElementById('total');
    total.value = precio.value * cantidad.value;
    total.value = roundNumber(total.value, 2);
}

function guardarNuevoServicio() {
    var descripcion = document.getElementById("nuevoServicioDescripcion").value;
    var tipo = document.getElementById("nuevoServicioTipo").value;
    var precio = document.getElementById("nuevoServicioPrecio").value;
    url = "ServicioCtrl?op=guardarNuevoServicio&descripcion=" + descripcion + "&tipo=" + tipo + "&precio=" + precio;
    ai = new AJAXInteraction(url, cargarServicios, "Text");
    ai.doGet();
    $('#myModal').modal('toggle');
    document.getElementById('nuevoServicioDescripcion').value = "";
    document.getElementById('nuevoServicioPrecio').value = "";
}

/*Seccion Tramite*/

function agregarTramite() {
    if (z.getSelectedValue() !== null) {
        if (z.getSelectedValue() !== -1) {
            var cantidad = document.getElementById('cantidad');
            url = "ServicioCtrl?op=obtenerNumSerie&idservicio=" + z.getSelectedValue() + "&cantidad=" + cantidad.value;
            ai = new AJAXInteraction(url, cargarTramite, "XML");
            ai.doGet();
        } else {
            alert("Debe seleccionar un servicio");
        }
    } else {
        alert("Debe seleccionar un servicio");
    }
}

function cargarTramite(result) {
    var xmlresult = result.getElementsByTagName('servicio');
    if (z.getSelectedValue() !== null) {
        if (z.getSelectedValue() !== -1) {
//            var serviciosAgregados = document.getElementById("servicios_tramite").tBodies[0].innerHTML;
            var precio = document.getElementById('precio');
            var valorSubtotalTramite = document.getElementById('valorSubtotalTramite');
            var valorIvaTramite = document.getElementById('valorIvaTramite');
            var valorTotalTramite = document.getElementById('valorTotalTramite');
            var usarClienteComoFavorecido = document.getElementById('usarCliente').checked;
            var nombreCliente = document.getElementById('nombreCliente').value;

            var html = "";
            for (i = 0; i < xmlresult.length; i++) {
                var serviceoffering = xmlresult[i];
                html += "<tr id='" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "-row'>";
                html += "<td>" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "</td>";
                html += "<td class='hidden-phone'>" + z.getSelectedText() + "</td>";
                html += "<td>";
                if (usarClienteComoFavorecido && nombreCliente!=='' && c.getSelectedValue() !== null) {                    
                    html += "<input id='benefinfo-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefinfo-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='text' disabled='' value='ok'></input>";
                    html += "<input id='benefnombre-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefnombre-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden' value='"+nombreCliente+"'></input>";
                    html += "<input id='benefid-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefid-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden' value='"+c.getSelectedValue()+"'></input>";
                } else {
                    html += "<input id='benefinfo-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefinfo-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='text' disabled='' value='pendiente'></input>";
                    html += "<input id='benefnombre-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefnombre-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden'></input>";
                    html += "<input id='benefid-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='benefid-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden'></input>";

                }

                html += "<input id='iddocumento-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='iddocumento-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden' value='" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "'></input>";
                html += "<input id='idservicio-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='idservicio-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden' value='" + z.getSelectedValue() + "'></input>";
                html += "<input id='valoradicional-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='valoradicional-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden' ></input>";
                html += "<input id='descripcionadicional-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' name='descripcionadicional-" + serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "' type='hidden'></input>";
                html += "</td>";
                html += "<td>$ " + precio.value + " </td>";
                html += "<td>";
//                html += "<button class='btn btn-danger btn-xs'><i class='fa fa-trash-o '></i></button>";
                html += "</td>";
                html += "</tr>";

                document.getElementById("servicios_tramite").tBodies[0].innerHTML = html;

                agregarDetalles(myTable, serviceoffering.getElementsByTagName('numSerie')[0].firstChild.nodeValue + "-row");
                valorSubtotalTramite.value = Number(valorSubtotalTramite.value) + Number(precio.value);
                valorIvaTramite.value = Number(valorSubtotalTramite.value) * 0.12;
                valorTotalTramite.value = Number(valorSubtotalTramite.value) + Number(valorIvaTramite.value);

                valorSubtotalTramite.value = roundNumber(valorSubtotalTramite.value, 2);
                valorIvaTramite.value = roundNumber(valorIvaTramite.value, 2);
                valorTotalTramite.value = roundNumber(valorTotalTramite.value, 2);

                html = "";
            }
            z.setComboText('');
            document.getElementById('precio').value = "0";
            document.getElementById('cantidad').value = "1";
            document.getElementById('total').value = "0";
        } else {
            alert("Debe seleccionar un servicio");
        }
    } else {
        alert("Debe seleccionar un servicio");
    }
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
    c.addOption([[-1, "<a href='#nuevoCliente' class='btn btn-xs btn-success'>Nuevo Cliente</a>"]]);
    c.enableFilteringMode(true);
}

function onClienteChange() {
    if (this.getSelectedValue() !== null) {
        if (this.getSelectedValue() !== -1) {
            url = "ClienteCtrl?op=obtenerCliente&identificacion=" + this.getSelectedValue();
            ai = new AJAXInteraction(url, cargarCliente, "XML");
            ai.doGet();
        } else {
            $('#nuevoCliente').modal('toggle');
        }
    } else {
        var nombreCliente = document.getElementById('nombreCliente');
        nombreCliente.value = "Nombre de cliente";
    }
}

function cargarCliente(result) {
    var xmlresult = result.getElementsByTagName('cliente');
    var nombreCliente = document.getElementById('nombreCliente');
    var creditoCliente = document.getElementById('credito');
    var abonoTotal = document.getElementById('abonoTotal');
    var usarClienteComoFavorecido = document.getElementById('usarCliente').checked;
    for (i = 0; i < xmlresult.length; i++) {
        var cliente = xmlresult[i];
        nombreCliente.value = cliente.getElementsByTagName('razonsocial')[0].firstChild.nodeValue;
        creditoCliente.value = cliente.getElementsByTagName('credito')[0].firstChild.nodeValue;
        abonoTotal.value = cliente.getElementsByTagName('credito')[0].firstChild.nodeValue;
    }
    if(usarClienteComoFavorecido){
        $( "input[id^='benefnombre-']" ).val( nombreCliente.value );
        $( "input[id^='benefid-']" ).val( c.getSelectedValue() );
        $( "input[id^='benefinfo-']").val("ok");    
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
function actualizarAbonoTotal() {
    var credito = document.getElementById('credito');
    var abono = document.getElementById('abono');
    var totalAbono = document.getElementById('abonoTotal');
    totalAbono.value = Number(credito.value) + Number(abono.value);
    totalAbono.value = roundNumber(totalAbono.value, 2);
}

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

function actualizarFavorecido(){
    var nombreCliente = document.getElementById('nombreCliente');
    var usarClienteComoFavorecido = document.getElementById('usarCliente').checked;

    if(usarClienteComoFavorecido && nombreCliente.value!=='' && c.getSelectedValue() !== null){
        $( "input[id^='benefnombre-']" ).val( nombreCliente.value );
        $( "input[id^='benefid-']" ).val( c.getSelectedValue() );
        $( "input[id^='benefinfo-']").val("ok");    
    }    
}