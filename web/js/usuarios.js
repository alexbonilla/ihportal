/*
 * Initialse DataTables, with no sorting on the 'details' column
 */
var myTable = $('#usuarios-table').dataTable({
    "bDestroy": true,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'asc']],
    "aoColumns": [
        /*info*/null,
        /*usuario*/null,
        /*tipo*/null,
        /*correo*/null,
        /*activo*/null
    ]
});

//var flagTablaInicial = 0;

var Script = function() {
    obtenerUsuario();
    consultarUsuarios();
}();

/*Seccion Usuarios*/

function consultarUsuarios() {
    url = "CuentaCtrl?op=consultar";
    ai = new AJAXInteraction(url, cargarUsuarios, "XML");
    ai.doGet();

}

function cargarUsuarios(result) {
    myTable.fnClearTable();

    var xmlresult = result.getElementsByTagName('usuario');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var usuario = xmlresult[i];
        html += "<tr id='" + usuario.getElementsByTagName('id')[0].firstChild.nodeValue + "-row'>";

        html += "<td>";
        html += usuario.getElementsByTagName('username')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + usuario.getElementsByTagName('roles')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + usuario.getElementsByTagName('email')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + usuario.getElementsByTagName('activo')[0].firstChild.nodeValue + "</td>";
        html += usuario.getElementsByTagName('activo')[0].firstChild.nodeValue;
        html += "<input id='idusuario-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' name='idusuario-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' type='hidden' value='" + usuario.getElementsByTagName('id')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='usuario-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' name='usuario-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' type='hidden' value='" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='tipo-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' name='tipo-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' type='hidden' value='" + usuario.getElementsByTagName('roles')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='email-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' name='email-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' type='hidden' value='" + usuario.getElementsByTagName('email')[0].firstChild.nodeValue + "'></input>";
        html += "<input id='estado-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' name='estado-" + usuario.getElementsByTagName('username')[0].firstChild.nodeValue + "' type='hidden' value='" + usuario.getElementsByTagName('activo')[0].firstChild.nodeValue + "'></input>";

        html += "</td>";

        html += "</tr>";

        document.getElementById("usuarios-table").tBodies[0].innerHTML = html;

        agregarDetalles(myTable, usuario.getElementsByTagName('id')[0].firstChild.nodeValue + "-row");

        html = "";
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

function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}

function guardarNuevoUsuario() {
    var usuario = document.getElementById("usuario").value;
    var clave = document.getElementById("clave").value;
    var mail = document.getElementById("mail").value;
    var rol = document.getElementById("tipo");
    var rolSeleccionado = tipo.options[rol.selectedIndex].value;
    url = "CuentaCtrl?op=crear&username=" + usuario + "&password=" + clave + "&email=" + mail + "&rol=" + rolSeleccionado;
    ai = new AJAXInteraction(url, consultarUsuarios, "Text");
    ai.doGet();
}

function modificarUsuario(usuario) {
    var nuevocorreo = document.getElementById(usuario + '-nuevo-correo').value;
    var nuevotipo = document.getElementById(usuario + '-nuevo-tipo').value;
    var nuevoestado = document.getElementById(usuario + '-estado').value;
    var nuevaclave = document.getElementById(usuario + '-nueva-clave').value;
    url = "CuentaCtrl?op=modificar";
    url = url + "&username=" + usuario + "&email=" + nuevocorreo + "&rol=" + nuevotipo + "&estado=" + nuevoestado + "&clave=" + nuevaclave;
    ai = new AJAXInteraction(url, consultarUsuarios, "XML");
    ai.doGet();
}