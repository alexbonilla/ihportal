
var Script = function() {
    obtenerUsuario();
    consultarGastos();   
}();

/*Seccion Gastos*/

function consultarGastos() {
    url = "GastoCtrl?op=buscarGastosHoy";
    ai = new AJAXInteraction(url, cargarGastos, "XML");
    ai.doGet();

}

function cargarGastos(result) {
    var xmlresult = result.getElementsByTagName('gasto');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var usuario = xmlresult[i];
        html += "<tr id='" + usuario.getElementsByTagName('idgasto')[0].firstChild.nodeValue + "-row'>";
        html += "<td>";
        html += usuario.getElementsByTagName('descripcion')[0].firstChild.nodeValue;
        html += "</td>";
        html += "<td class='hidden-phone'>" + usuario.getElementsByTagName('fechacreacion')[0].firstChild.nodeValue + "</td>";        
        html += "<td>" + usuario.getElementsByTagName('valor')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";        
    }
    document.getElementById("gastos-table").tBodies[0].innerHTML = html;
    html = "";
}

function guardarNuevoGasto() {
    var valor = document.getElementById("valor_gasto").value;
    var descripcion = document.getElementById("descripcion_gasto").value;

    url = "GastoCtrl?op=guardarNuevoGasto&valor=" + valor + "&descripcion=" + descripcion;
    ai = new AJAXInteraction(url, consultarGastos, "Text");
    ai.doGet();
}

//Seccion Usuarios

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

function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}